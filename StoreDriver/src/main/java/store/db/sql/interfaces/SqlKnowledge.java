package store.db.sql.interfaces;

import handy.tools.helpers.TypeHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import store.db.sql.commons.SqlCommons;

public abstract class SqlKnowledge extends SqlCommons {

	
	public abstract void returnConnection(Connection conn);
	public abstract void reportSQLError(Connection conn, SQLException e);
	
	/*debug is done
	 * 
	 * 
	 * */
	public void doInsert(Connection conn, String tableName, List<Map<String,Object>> rowsData, int[] DataTypes) {
		
		//Connection conn = retrieveConnection();
		Iterator it = rowsData.get(0).keySet().iterator();
		String[] keys = new String[rowsData.get(0).size()];
		for(int i = 0; it.hasNext(); i++) {
			keys[i] = (String) it.next();
		}
		String sql =  prepareInsertSql(keys,tableName);
		int cnt = rowsData.size();
		int[] dataTypes = TypeHelper.getDataTypes(rowsData.get(0));
			
		System.out.println(sql);
		try {
			conn.setAutoCommit(false);		
			PreparedStatement statement = conn.prepareStatement(sql);
			for(int i = 0; i < cnt; i++) {
				
				setValuesForSql(statement, rowsData.get(i), keys, dataTypes);
				statement.addBatch();
				//System.out.println(batchData.get(i));
				if(i%2000 == 0) {
					System.out.println("commiting data: " + rowsData.get(i));
					statement.executeBatch();
					conn.commit();
				}
		    }
			System.out.println("commiting rest of data");
			statement.executeBatch();
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			reportSQLError(conn,e);
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
				reportSQLError(conn,e);
			}
			returnConnection(conn);
		}
						
	}
	
public void doUpdate(Connection conn, String tableName, List<Map<String,Object>> rowsData, String[] whereColPlusOper, String[] whereAndOr) {
		

		Iterator it = rowsData.get(0).keySet().iterator();
		String[] keys = new String[rowsData.get(0).size()];
		for(int i = 0; it.hasNext(); i++) {
			keys[i] = (String) it.next();
		}
		String sql =  prepareUpdateSql(keys,tableName, whereColPlusOper, whereAndOr);
		int cnt = rowsData.size();
		int[] dataTypes = TypeHelper.getDataTypes(rowsData.get(0));
			
		System.out.println(sql);
		try {
			conn.setAutoCommit(true);
			
			PreparedStatement statement = conn.prepareStatement(sql);
			for(int i = 0; i < cnt; i++) {
				
				setValuesForSql(statement, rowsData.get(i), keys, dataTypes);				
				System.out.println("commiting data: " + rowsData.get(i));
				statement.executeUpdate();
		    }
			
		} catch (SQLException e) {
			e.printStackTrace();
			reportSQLError(conn,e);
		} finally {
			returnConnection(conn);
		}
						
	}

/*Debug has been done
 * 
 * 
 * */
	public ResultSet doQuery(Connection conn, String baseSql, String[] conditions, String[] andOr, Object[] condValues) {
	
		System.out.println("do query start !");
		//Connection conn = retrieveConnection();
	
		PreparedStatement statement = null;		
		String sql = baseSql;
		String where_sql = prepareSimpleWhereConds(conditions, andOr);
		
		if(null != where_sql) {
			sql = sql + where_sql;
		}	
		
		System.out.println("sql string: " + sql);

		//Map<String,List<List<Object>>> Result = null;
		ResultSet sqlResult = null;
		try {
			//System.out.println("do prepare statement start !");
			statement = conn.prepareStatement(sql);
			setValuesForSql(statement, condValues);
			sqlResult = statement.executeQuery();		
			//Result = parseQueryResult(sqlRet);
			 
		} catch (SQLException e) {
			e.printStackTrace();
			reportSQLError(conn,e);
		} finally {
			returnConnection(conn);
		}
					
		return sqlResult;

	}
	
}
