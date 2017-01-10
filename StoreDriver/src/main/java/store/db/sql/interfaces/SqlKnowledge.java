package store.db.sql.interfaces;

import handy.tools.helpers.TypeHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import store.db.sql.commons.SqlCommons;

/** 
* @ClassName: SqlKnowledge 
* @Description: TODO(what to do) 
* @author walterwhite
* @date 2017年1月9日 下午2:47:32 
*  
*/

public abstract class SqlKnowledge extends SqlCommons {

	public abstract void reportFailure(Exception e);
	public abstract void reportExecuteProcess(String info);
	public abstract void reportResults(ResultSet result);
	public abstract void reportResults(int doneCnt);
	public abstract void returnResources(Connection conn, PreparedStatement statement);
	public abstract void returnResources(Connection conn, Statement statement);
	
	/** 
	* @Title: doInsert 
	* @Description: TODO(for batch insert)  debug is done
	* @param @param conn
	* @param @param tableName
	* @param @param rowsData - column,value
	* @param @param DataTypes  
	* @return void   
	* @throws 
	*/
	public void doInsert(Connection conn, String tableName, List<Map<String,Object>> rowsData) {
		
		Iterator<?> it = rowsData.get(0).keySet().iterator();
		String[] columns = new String[rowsData.get(0).size()];
		for(int i = 0; it.hasNext(); i++) {
			columns[i] = (String) it.next();
		}
		String sql =  prepareInsertSql(columns,tableName);
		int cnt = rowsData.size();
		int[] dataTypes = TypeHelper.getDataTypes(rowsData.get(0));
			
		reportExecuteProcess("ready to execute sql:" + sql);
		PreparedStatement statement = null;
		try {
			conn.setAutoCommit(false);		
			statement = conn.prepareStatement(sql);
			for(int i = 0; i < cnt; i++) {		
				setValuesForSql(statement, rowsData.get(i), columns, dataTypes);
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

	/** 
	* @Title: doPrepareSql 
	* @Description: TODO(insert,update,delete) 
	* @param @param conn
	* @param @param completeSql
	* @param @param values  
	* @return void   
	* @throws 
	*/
	public void doPrepareSql(Connection conn, String completeSql, Object[] values) {
			
		reportExecuteProcess("ready to execute sql:" + completeSql);
		PreparedStatement statement = null;
		int result = 0;
		try {
			if(!conn.getAutoCommit()) conn.setAutoCommit(true);			
			statement = conn.prepareStatement(completeSql);
			setValuesForSql(statement,values);
			result = statement.executeUpdate();
			
		} catch (SQLException e) {
			reportFailure(e);
			try {
				conn.rollback();
			} catch (SQLException e1) {
				reportFailure(e1);
			}
		} finally {
			reportResults(result);
			returnResources(conn,statement);
		}
						
	}
	
	/** 
	* @Title: doSimpleSql 
	* @Description: TODO(what to do) 
	* @param @param conn
	* @param @param complete sql  
	* @return void   
	* @throws 
	*/
	public void doSimpleSql(Connection conn, String sql) {
		
		Statement sqlStatement = null;
		
		int doneCnt = 0;
		reportExecuteProcess("ready to execute simple sql:" + sql);
		try {
			sqlStatement = conn.createStatement();
			doneCnt = sqlStatement.executeUpdate(sql);
			
		} catch (SQLException e) {
			reportFailure(e);
		} finally {
			reportResults(doneCnt);
			returnResources(conn, sqlStatement);
		}
		
	}


	/** 
	* @Title: doPrepareQuery 
	* @Description: TODO(query using prepareStatement) 
	* @param @param conn
	* @param @param completeSql
	* @param @param condValues - where condition values 
	* @return void   
	* @throws 
	*/
	public void doPrepareQuery(Connection conn, String completeSql, Object[] condValues) {
	
		PreparedStatement statement = null;		

		reportExecuteProcess("ready to execute sql:" + completeSql);
		//Map<String,List<List<Object>>> Result = null;
		ResultSet sqlResult = null;
		try {
			statement = conn.prepareStatement(completeSql);			
			setValuesForSql(statement, condValues);
			sqlResult = statement.executeQuery();		
			 
		} catch (SQLException e) {
			reportFailure(e);
		} finally {
			reportResults(sqlResult);
			returnResources(conn,statement);
		}

	}
	
	
	/** 
	* @Title: doMySqlPrepareQuery 
	* @Description: TODO(query using prepareStatement using mysql stream mode) 
	* @param @param conn
	* @param @param completeSql
	* @param @param condValues  
	* @return void   
	* @throws 
	*/
	public void doMySqlPrepareQuery(Connection conn, String completeSql, Object[] condValues) {
		
		PreparedStatement statement = null;		

		reportExecuteProcess("ready to execute sql:" + completeSql);
		//Map<String,List<List<Object>>> Result = null;
		ResultSet sqlResult = null;
		try {
			//parepare statement for tons of data coming ~
			statement = conn.prepareStatement(completeSql,ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
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
	
	
	/** 
	* @Title: doSimpleQuery 
	* @Description: TODO(what to do) 
	* @param @param conn
	* @param @param sql  
	* @return void   
	* @throws 
	*/
	public void doSimpleQuery(Connection conn, String sql) {
		
		ResultSet sqlResult = null;
		Statement sqlStatement = null;
		reportExecuteProcess("ready to execute simple sql:" + sql);
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
