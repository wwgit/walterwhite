package store.db.sql.interfaces;

import handy.tools.helpers.DbHelper;
import handy.tools.helpers.TypeHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import store.db.sql.commons.SqlCommons;

public abstract class SqlKnowledge extends SqlCommons {

	public abstract void reportFailure(Exception e);
	public abstract void reportExecuteProcess(String info);
	public abstract void reportResults(ResultSet result);
	public abstract void reportResults(int doneCnt);
	public abstract void returnResources(Connection conn, PreparedStatement statement);
	public abstract void returnResources(Connection conn, Statement statement);
	
	/*debug is done
	 * 
	 * 
	 * */
	public void doInsert(Connection conn, String tableName, List<Map<String,Object>> rowsData, int[] DataTypes) {
		
		//Connection conn = retrieveConnection();
		Iterator<?> it = rowsData.get(0).keySet().iterator();
		String[] keys = new String[rowsData.get(0).size()];
		for(int i = 0; it.hasNext(); i++) {
			keys[i] = (String) it.next();
		}
		String sql =  prepareInsertSql(keys,tableName);
		int cnt = rowsData.size();
		int[] dataTypes = TypeHelper.getDataTypes(rowsData.get(0));
			
		reportExecuteProcess("ready to execute sql:" + sql);
		PreparedStatement statement = null;
		try {
			conn.setAutoCommit(false);		
			statement = conn.prepareStatement(sql);
			for(int i = 0; i < cnt; i++) {		
				setValuesForSql(statement, rowsData.get(i), keys, dataTypes);
				statement.addBatch();
				if(i%2000 == 0) {			
					statement.executeBatch();
					conn.commit();
					reportExecuteProcess("committed data: " + rowsData.get(i));
				}
		    }			
			statement.executeBatch();
			conn.commit();
			reportExecuteProcess("commiting rest of data");
			conn.setAutoCommit(true);
		} catch (SQLException e) {
			reportFailure(e);
			try {
				conn.rollback();
			} catch (SQLException e1) {
				reportFailure(e1);
			}
		} finally {
			returnResources(conn,statement);
		}
						
	}
	
	public void doUpdate(Connection conn, String tableName, List<Map<String,Object>> rowsData, String[] whereColPlusOper, String[] whereAndOr) {
		

		Iterator<?> it = rowsData.get(0).keySet().iterator();
		String[] keys = new String[rowsData.get(0).size()];
		for(int i = 0; it.hasNext(); i++) {
			keys[i] = (String) it.next();
		}
		String sql =  prepareUpdateSql(keys,tableName, whereColPlusOper, whereAndOr);
		int cnt = rowsData.size();
		int[] dataTypes = TypeHelper.getDataTypes(rowsData.get(0));
			
		reportExecuteProcess("ready to execute sql:" + sql);
		PreparedStatement statement = null;
		try {
			conn.setAutoCommit(false);
			statement = conn.prepareStatement(sql);
			for(int i = 0; i < cnt; i++) {
				
				setValuesForSql(statement, rowsData.get(i), keys, dataTypes);				
				statement.addBatch();
				if(i%2000 == 0) {			
					statement.executeBatch();
					conn.commit();
					reportExecuteProcess("committed data: " + rowsData.get(i));
				}
		    }
			statement.executeBatch();
			conn.commit();
			reportExecuteProcess("commiting rest of data");
			conn.setAutoCommit(true);
			
		} catch (SQLException e) {
			reportFailure(e);
			try {
				conn.rollback();
			} catch (SQLException e1) {
				reportFailure(e1);
			}
		} finally {
			returnResources(conn,statement);
		}
						
	}

	public void doDelete(Connection conn, String delSql, String[] conditions, String[] andOr, Object[] condValues) {
	
		PreparedStatement statement = null;		
		String sql = delSql + prepareSimpleWhereConds(conditions, andOr);	
		
		reportExecuteProcess("ready to execute sql:" + sql);
		try {
			//System.out.println("do prepare statement start !");
			statement = conn.prepareStatement(sql);
			DbHelper.setValuesForSql(statement, condValues);
			statement.executeUpdate();		
			 
		} catch (SQLException e) {
			reportFailure(e);
		} finally {
			returnResources(conn,statement);
		}

	}
	
	public void doSimpleDelete(Connection conn, String delSql) {
		
		Statement sqlStatement = null;
		
		int doneCnt = 0;
		
		try {
			sqlStatement = conn.createStatement();
			doneCnt = sqlStatement.executeUpdate(delSql);
			
		} catch (SQLException e) {
			reportFailure(e);
		} finally {
			reportResults(doneCnt);
			returnResources(conn, sqlStatement);
		}
		
	}

	/*Debug has been done
	 * 
	 * 
	 * */
	public void doQuery(Connection conn, String baseSql, String[] conditions, 
												String[] andOr, Object[] condValues) {
	
		PreparedStatement statement = null;		
		
		String sql = baseSql + prepareSimpleWhereConds(conditions, andOr);
		reportExecuteProcess("ready to execute sql:" + sql);
		//Map<String,List<List<Object>>> Result = null;
		ResultSet sqlResult = null;
		try {
			//statement = conn.prepareStatement(sql);
			//parepare statement for tons of data coming ~
			statement = conn.prepareStatement(sql,ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			statement.setFetchSize(Integer.MIN_VALUE);
			statement.setFetchDirection(ResultSet.FETCH_REVERSE);
			
			setValuesForSql(statement, condValues);
			sqlResult = statement.executeQuery();		
			 
		} catch (SQLException e) {
			reportFailure(e);
		} finally {
			reportResults(sqlResult);
			returnResources(conn,statement);
		}

	}
	
	public void doSimpleQuery(Connection conn, String sql) {
		
		ResultSet sqlResult = null;
		Statement sqlStatement = null;
		
		try {
			sqlStatement = conn.createStatement();
			sqlResult = sqlStatement.executeQuery(sql);
			
		} catch (SQLException e) {
			reportFailure(e);
		} finally {
			reportResults(sqlResult);
			returnResources(conn, sqlStatement);
		}
		
	}
	
	
	
}
