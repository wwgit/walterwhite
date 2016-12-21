package handy.tools.helpers;

import handy.tools.constants.DataTypes;
import handy.tools.db.ComplexValue;
import handy.tools.db.DbConfig;
import handy.tools.db.DbPool;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Array;
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

public abstract class DbHelper extends BasicHelper {
		
	
	private static void setValue(PreparedStatement statement, Object value, int index, int dataType) throws SQLException {
		
		switch(dataType) {
			case DataTypes.JAVA_LANG_STRING:
				statement.setString(index, (String) value);
				break;
			case DataTypes.JAVA_LANG_INTEGER:
				int i_val = ((Integer) value).intValue();
				statement.setInt(index, i_val);
				break;
			case DataTypes.JAVA_LANG_LONG:
				long l_val = ((Long) value).longValue();
				statement.setLong(index, l_val);
				break;
			case DataTypes.JAVA_MATH_BIGDECIMAL:
				statement.setBigDecimal(index, (BigDecimal) value);
				break;
			case DataTypes.JAVA_LANG_ASCII_STREAM:
				ComplexValue cv_ascii = (ComplexValue)value;
				statement.setAsciiStream(index, cv_ascii.data);
				break;
			case DataTypes.JAVA_LANG_BINARY_STREAM:
				ComplexValue cv_bin = (ComplexValue)value;
				statement.setBinaryStream(index, cv_bin.data);
				break;
			case DataTypes.JAVA_LANG_ARRAY:
				statement.setArray(index, (Array) value);
			default:
				break;
		}
	}
	
	
	
	public  static Connection createConnection(String url, String userName, String password) throws SQLException {
		
		Connection conn = null;
		if(null != userName && null != password) {
			conn = DriverManager.getConnection(url, userName, password);
			
		} else {
			conn = DriverManager.getConnection(url);
			//DriverManager.registerDriver(driver);
		}
		return conn;
		
	}
			
	public static void closeConnection(Connection conn) {
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
	public static void doOneInsert(Connection conn, PreparedStatement statement, 
									Map data, String[] keys, int[] dataTypes) throws SQLException {
					
		for(int i = 0; i < dataTypes.length; i++) {
			setValue(statement,data.get(keys[i]),i,dataTypes[i]);
		}
		statement.addBatch();
	}
	
	public static String prepareInsertSql(String[] keys, String tableName) {
		
			StringBuilder sb = new StringBuilder();
			sb.append("INSERT INTO ");
			sb.append(tableName);
			sb.append(" (");
			
			for(int i = 0; i < keys.length-1; i++) {
				sb.append(keys[i] + ",");
			}
			sb.append(keys[keys.length-1] + ") VALUES (");
			
			for(int j = 0; j < keys.length-1; j++) {
				sb.append("?,");
			}
			sb.append("?)");
			return sb.toString();
    }
	
	
}
