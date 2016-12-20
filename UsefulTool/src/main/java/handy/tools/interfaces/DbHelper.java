package handy.tools.interfaces;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Vector;

import javax.lang.model.element.Element;

public abstract class DbHelper extends SqlHelper {
	
	
	public static Vector<Connection> initConnections(String url, String clazzName, String userName, String password, int size) throws ClassNotFoundException, SQLException {
		
		Vector<Connection> conns = null;
		
		Class.forName(clazzName);
		Connection conn = null;
		
		for(int i = 0; i < size; i++) {
			createConnection(url, clazzName, userName, password);
			conns.add(i, conn);
		}
		return conns;		
	}
	
	public static Connection createConnection(String url, String clazzName, String userName, String password) throws SQLException {
		
		Connection conn = null;
		if(null != userName && null != password) {
			conn = DriverManager.getConnection(url, userName, password);
			
		} else {
			conn = DriverManager.getConnection(url);
			//DriverManager.registerDriver(driver);
		}
		return conn;
		
	}
	
	public static Connection retrieveConnection(Vector<Connection> conns) {
		Connection conn = null;
		if(!conns.isEmpty()) {
			conn = conns.firstElement();
			conns.remove(0);
		}
		return conn;
	}
	
	public static void returnConnection(Vector<Connection> conns, Connection conn) {
		conns.add(conn);
	}
	
	public static void closeConnections(Vector<Connection> conns) {
		
		
		for(Iterator it = conns.iterator(); it.hasNext();) {
			Connection conn = (Connection) it.next();
			closeConnection(conn);
		}
	}
	
	public static void closeConnection(Connection conn) {
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static Map doQuery(Vector<Connection> conns, String sql) throws ClassNotFoundException, SQLException {
			
		Connection conn = retrieveConnection(conns);
		
		PreparedStatement statement = conn.prepareStatement(sql);
		Map<Long,String> result = new TreeMap<Long,String>();
		
		ResultSet sqlRet = statement.executeQuery(sql);
		
		for(int i = 0; i < sqlRet.getFetchSize(); i++) {
			//result.set(i, sqlRet.getString(i));
			result.put(Long.valueOf(i), sqlRet.getString(i));
		}
		
		returnConnection(conns,conn);
		return result;
		
	}
	
	private static void doOneInsert(Connection conn, PreparedStatement statement, 
									Map data, String[] keys, int[] dataTypes) throws SQLException {
					
		for(int i = 0; i < dataTypes.length; i++) {
			setValue(statement,data.get(keys[i]),i,dataTypes[i]);
		}
		statement.addBatch();
	}
	
	public static void doInserts(Vector<Connection> conns, String tableName, List<Map> batchData) {
		
		Connection conn = retrieveConnection(conns);
		String[] keys = (String[]) batchData.get(0).keySet().toArray();
		String sql =  prepareInsertSql(keys,tableName);
		int cnt = batchData.size();
		int[] dataTypes = getDataTypes(batchData.get(0));
			
		System.out.println(sql);
		try {
			conn.setAutoCommit(false);
			PreparedStatement statement = conn.prepareStatement(sql);
			for(int i = 0; i < cnt; i++) {
				doOneInsert(conn, statement, batchData.get(i), keys, dataTypes);
				if(i%2000 == 0) {
					statement.executeBatch();
				}
		    }
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			returnConnection(conns, conn);
		}
						
	}

}
