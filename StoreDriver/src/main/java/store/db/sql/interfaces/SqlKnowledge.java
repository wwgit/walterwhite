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
	public void doInsert(Connection conn, String tableName, List<Map<String,Object>> rowsData, int[] DataTypes) {
		
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
	* @Title: doUpdate 
	* @Description: TODO(for batch update) 
	* @param @param conn
	* @param @param tableName
	* @param @param setRowData
	* 				-  List<Map<String,Object>> set column1=value,column2=value2
	* @param @param whereRowData 
	* 				- List<Map<String,Object>> where column=value, and column3=value4
	* @param @param whereAndOrs 
	* 				- List<Map<String,String>> and/or in where column=value and/or column3=value4
	* @param @param operators 
	* 				- String[] and/or in where column=value and/or column3=value4
	* 
	* @return void   
	* @throws 
	*/
	public void doUpdate(Connection conn, String tableName, List<Map<String,Object>> setRowData, 
						List<Map<String,Object>> whereRowData, 
						List<Map<String,String>> whereAndOrs, String[] operators) {
		if(null == setRowData) {
			reportFailure(new NullPointerException("param setRowData is Null"));
		}
		if(null == whereRowData) {
			reportFailure(new NullPointerException("param whereRowData is Null"));
		}
		if(null == whereAndOrs) {
			if(whereRowData.size() > 1)
			reportFailure(new NullPointerException("param whereAndOrs should Not be Null"));
		}
		if(null == operators) {
			reportFailure(new NullPointerException("param operators is Null"));
		}
		
		if(1 != whereRowData.size() - whereAndOrs.size() ) {
			reportFailure(new Exception("the number of where conditions and operators does Not match !"));
		}
		if(1 > setRowData.size()) {
			reportFailure(new Exception("at least one set statement is needed ! param setRowData issue !"));
		}	
		if(whereRowData.size() != setRowData.size()) {
			reportFailure(new Exception("row number does Not match for set statment and where conditions !"));
		}
		if(whereRowData.size() != operators.length) {
			reportFailure(new Exception("row number does Not match for where operators and where conditions !"));
		}
		
		int rowCnt = setRowData.size();
		Iterator<?> it = setRowData.get(0).keySet().iterator();
		String[] setColumns = new String[setRowData.get(0).size()];
		String[] whereColumns = new String[whereRowData.get(0).size()];
		for(int i = 0; it.hasNext(); i++) {
			setColumns[i] = (String) it.next();
		}
		String sql =  prepareUpdateSql(setColumns,tableName, whereColPlusOper, whereAndOr);
		
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

	/** 
	* @Title: doDelete 
	* @Description: TODO(what to do) 
	* @param @param conn
	* @param @param delSql
	* @param @param conditions
	* @param @param andOr
	* @param @param condValues  
	* @return void   
	* @throws 
	*/
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
	
	/** 
	* @Title: doSimpleSql 
	* @Description: TODO(what to do) 
	* @param @param conn
	* @param @param sql  
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
	* @Title: doQuery 
	* @Description: TODO(for tons of data coming) Debug has been done 
	* @param @param conn
	* @param @param baseSql e.g select * from tableName
	* @param @param conditions
	* @param @param andOr
	* @param @param condValues  
	* @return void   
	* @throws 
	*/
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
