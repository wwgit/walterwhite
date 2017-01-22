package store.db.sql.interfaces;

import handy.tools.helpers.TypeHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import store.db.sql.commons.SqlCommons;


/** 
* @ClassName: SqlKnowledge 
* @Description: TODO(what to do) 
* @author walterwhite
* @date 2017年1月20日 下午2:23:42 
*  
*/
public abstract class SqlKnowledge extends SqlCommons {

	protected abstract void reportFailure(Exception e);
	protected abstract void reportExecuteProcess(String info);
	protected abstract void reportResults(ResultSet result);
	protected abstract void reportResults(int doneCnt);
	protected abstract void returnResources(Connection conn, PreparedStatement statement);
	protected abstract void returnResources(Connection conn, Statement statement);
	

	/** 
	* @Title: doInsert 
	* @Description: TODO(what to do) 
	* @param @param conn
	* @param @param sql
	* @param @param rowsData  
	* @return void   
	* @throws 
	*/
	protected void doInsert(Connection conn, String sql, List<Object[]> rowsData) {

		int cnt = rowsData.size();
		int[] dataTypes = TypeHelper.getDataTypes(rowsData.get(0));
			
		reportExecuteProcess("ready to execute sql:" + sql);
		PreparedStatement statement = null;
		try {
			conn.setAutoCommit(false);		
			statement = conn.prepareStatement(sql);
			
			for(int i = 0; i < cnt; i++) {		
				setValuesForSql(statement,rowsData.get(i), dataTypes);
				statement.addBatch();
				if(i%2000 == 0) {			
					statement.executeBatch();
					conn.commit();
					reportExecuteProcess("committed data: " + i);
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
	protected void doPrepareSql(Connection conn, String completeSql, Object[] values) {
			
		reportExecuteProcess("ready to execute sql:" + completeSql);
		PreparedStatement statement = null;
		
		int result = 0;
		try {
			
			if(!conn.getAutoCommit()) conn.setAutoCommit(true);			
			statement = conn.prepareStatement(completeSql);
			if(null != values) {
				int[] dataTypes = TypeHelper.getDataTypes(values);
				setValuesForSql(statement,values,dataTypes);
			}
			result = statement.executeUpdate();
			reportResults(result);
			
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
	* @Title: doSimpleSql 
	* @Description: TODO(what to do) 
	* @param @param conn
	* @param @param complete sql  
	* @return void   
	* @throws 
	*/
	protected void doSimpleSql(Connection conn, String sql) {
		
		Statement sqlStatement = null;
		
		int doneCnt = 0;
		reportExecuteProcess("ready to execute simple sql:" + sql);
		try {
			
			sqlStatement = conn.createStatement();
			doneCnt = sqlStatement.executeUpdate(sql);
			reportResults(doneCnt);
			
		} catch (SQLException e) {
			reportFailure(e);
		} finally {
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
	protected void doPrepareQuery(Connection conn, String completeSql, Object[] condValues) {
	
		PreparedStatement statement = null;		

		reportExecuteProcess("ready to execute sql:" + completeSql);
		
		ResultSet sqlResult = null;
		try {
			
			statement = conn.prepareStatement(completeSql);
			if(null != condValues) {
				int[] dataTypes = TypeHelper.getDataTypes(condValues);
				setValuesForSql(statement, condValues,dataTypes);
			}
			sqlResult = statement.executeQuery();
			reportResults(sqlResult);
			 
		} catch (SQLException e) {
			reportFailure(e);
		} finally {
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
	protected void doSimpleQuery(Connection conn, String sql) {
		
		ResultSet sqlResult = null;
		Statement sqlStatement = null;
		reportExecuteProcess("ready to execute simple sql:" + sql);
		try {
			
			sqlStatement = conn.createStatement();
			sqlResult = sqlStatement.executeQuery(sql);
			reportResults(sqlResult);
			
		} catch (SQLException e) {
			reportFailure(e);
		} finally {
			returnResources(conn, sqlStatement);
		}
		
	}
	
	
	
}
