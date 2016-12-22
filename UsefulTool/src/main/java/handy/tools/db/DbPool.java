package handy.tools.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

import handy.tools.helpers.DbHelper;
import handy.tools.helpers.TypeHelper;

public class DbPool {
	
	private Vector<Connection> connetions;
	
	public DbPool(DbConfig config) {
		setConnetions(initConnections(config));		
	}
	
	public DbPool() {
		
	}
	

	public Vector<Connection> getConnetions() {
		return connetions;
	}

	public void setConnetions(Vector<Connection> connetions) {
		this.connetions = connetions;
	}
	
	public Vector<Connection> initConnections(DbConfig config) {
		
		Vector<Connection> conns = new Vector<Connection>();
		Connection conn = null;
		try {
			Class.forName(config.getDbDriver());
			for(int i = 0; i < config.getDbSize(); i++) {
				conn = DbHelper.createConnection(config.getUrl(), config.getUserName(), config.getPassword());
				conns.add(i, conn);
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	

		return conns;		
	}
	
	public void closeConnections() {
		
		@SuppressWarnings("rawtypes")
		Iterator it = null;
		for(it = getConnetions().iterator(); it.hasNext();) {
			Connection conn = (Connection) it.next();
			DbHelper.closeConnection(conn);
		}
	}
	
	public Connection retrieveConnection() {
		Connection conn = null;
		if(!getConnetions().isEmpty()) {
			conn = getConnetions().firstElement();
			getConnetions().remove(0);
			System.out.println("pool size: " + this.getConnetions().size());
		}
		return conn;
	}
	
	public void returnConnection(Connection conn) {
		getConnetions().add(conn);
	}
	
	public void doInserts(String tableName, List<Map> batchData) {
		
		Connection conn = retrieveConnection();
		String[] keys = (String[]) batchData.get(0).keySet().toArray();
		String sql =  DbHelper.prepareInsertSql(keys,tableName);
		int cnt = batchData.size();
		int[] dataTypes = TypeHelper.getDataTypes(batchData.get(0));
			
		System.out.println(sql);
		try {
			conn.setAutoCommit(false);
			PreparedStatement statement = conn.prepareStatement(sql);
			for(int i = 0; i < cnt; i++) {
				DbHelper.setValuesForSql(statement, batchData.get(i), keys, dataTypes);
				statement.addBatch();
				if(i%2000 == 0) {
					statement.executeBatch();
				}
		    }
			statement.executeBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			returnConnection(conn);
		}
						
	}

	@SuppressWarnings("unchecked")
	public Map doQuery(String baseSql, String[] conditions, String[] andOr, Object[] values, String[] queryColumns) {
		System.out.println("do query start !");
		Connection conn = retrieveConnection();
		//Connection conn = null;
		
		//Statement statement = null;
		PreparedStatement statement = null;
		//Map<String, String> result = new HashMap<String,String>();
		
		String sql = baseSql;
		String where_sql = DbHelper.prepareSimpleSqlConditions(conditions, andOr);
		
		if(null != where_sql) {
			sql = sql + where_sql;
		}	
		
		ResultSet sqlRet = null;
		Map<String,List<List<Object>>> Result = null;
		try {
			//System.out.println("do prepare statement start !");
			statement = conn.prepareStatement(sql);
			DbHelper.setValuesForSql(statement, values);
			sqlRet = statement.executeQuery();
			
			int colCnt = sqlRet.getMetaData().getColumnCount();
			//System.out.println("do reading data start !   table column = " + colCnt);
			
			 Result = DbHelper.parseQueryResult(sqlRet, null);
			 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			returnConnection(conn);
		}
					
		return Result;
	
	}
	

}
