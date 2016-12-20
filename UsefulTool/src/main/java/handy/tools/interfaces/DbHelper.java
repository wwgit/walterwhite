package handy.tools.interfaces;

import handy.tools.db.DbConfig;
import handy.tools.db.DbPool;

import java.io.IOException;
import java.lang.reflect.Field;
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
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;
import java.util.Vector;

import javax.lang.model.element.Element;

public abstract class DbHelper extends SqlHelper {
		
	
	protected Connection createConnection(String url, String userName, String password) throws SQLException {
		
		Connection conn = null;
		if(null != userName && null != password) {
			conn = DriverManager.getConnection(url, userName, password);
			
		} else {
			conn = DriverManager.getConnection(url);
			//DriverManager.registerDriver(driver);
		}
		return conn;
		
	}
			
	protected void closeConnection(Connection conn) {
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*
	 * sql related
	 * */
	protected static void doOneInsert(Connection conn, PreparedStatement statement, 
									Map data, String[] keys, int[] dataTypes) throws SQLException {
					
		for(int i = 0; i < dataTypes.length; i++) {
			setValue(statement,data.get(keys[i]),i,dataTypes[i]);
		}
		statement.addBatch();
	}
	
	
	
	
	/*
	 * db config related
	 * 
	 * */
	public static DbConfig parseConfigFrmProperties(String configPath) {
		
		String[] propertyNames = getDbPropertyNames();
		Properties prop = new Properties();
		try {
			prop.load(PathHelper.resolveAbsoluteStream(configPath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//return new DbConfig(prop.getProperty("db.url"),prop.getProperty("db.driver"),
		//		prop.getProperty("db.username"),prop.getProperty("db.password"));
		return new DbConfig(prop.getProperty(propertyNames[0]),
							prop.getProperty(propertyNames[1]),
							prop.getProperty(propertyNames[2]),
							prop.getProperty(propertyNames[3]),
							Integer.parseInt(prop.getProperty(propertyNames[4])));
		
		
	}
		
	public static String[] getDbPropertyNames() {
		
		Field[] fields = DbConfig.class.getDeclaredFields();
		String[] names = new String[fields.length];
		String clazz = DbConfig.class.toString().toLowerCase().replaceAll("class", "");
		
		for(int i = 0; i < fields.length; i++) {
			names[i] = clazz + "." + fields[i].getName().toLowerCase();
			System.out.println(names[i]);
		}
		
		return names;
	}
	
	
}
