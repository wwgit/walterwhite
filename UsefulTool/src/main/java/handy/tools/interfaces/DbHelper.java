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
import java.util.Vector;

import javax.lang.model.element.Element;

public abstract class DbHelper {
	
	
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
	
	
	public static List doQueryAll(String url, String clazzName, String userName, String password, String sql) throws ClassNotFoundException, SQLException {
			
		if(null == url) {
			throw new SQLException("url is NUll !");
		}
		Class.forName(clazzName);
		Connection conn = null;
		if(null != userName && null != password) {
			conn = DriverManager.getConnection(url, userName, password);
			
		} else {
			conn = DriverManager.getConnection(url);
		}
		
		PreparedStatement statement = conn.prepareStatement(sql);
		List<String> result = new ArrayList<String>();
		
		ResultSet sqlRet = statement.executeQuery(sql);
		
		for(int i = 0; i < sqlRet.getFetchSize(); i++) {
			result.set(i, sqlRet.getString(i));
		}
		
		return result;
		
	}
	

}
