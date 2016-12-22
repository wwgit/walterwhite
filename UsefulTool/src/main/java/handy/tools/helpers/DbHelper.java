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

import com.alibaba.fastjson.asm.Type;

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
			case DataTypes.JAVA_LANG_DOUBLE:
				double d_val = ((Double)value).doubleValue();
				statement.setDouble(index, d_val);
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
				break;
			case DataTypes.JAVA_BASIC_INT:
				int ii_val = ((Integer) value).intValue();
				statement.setInt(index, ii_val);
				break;
			case DataTypes.JAVA_BASIC_DOUBLE:
				double dd_val = ((Double)value).doubleValue();
				statement.setDouble(index, dd_val);
				break;
			case DataTypes.JAVA_BASIC_LONG:
				long ll_val = ((Long) value).longValue();
				statement.setLong(index, ll_val);
				break;
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
			//Statement state = conn.createStatement();
			
			//DriverManager.registerDriver(driver);
		}
		return conn;
		
	}
			
	public static void closeConnection(Connection conn) {
		try {
			//System.out.println("closing connection");
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*
	 * sql related
	 * */
	public static void setValuesForSql(PreparedStatement statement, 
									Map data, String[] dbColumnNames, int[] dataTypes) throws SQLException {
		
		if(null == data || data.size() < 1) {
			return;
		}
		
		for(int i = 0; i < dataTypes.length; i++) {
			setValue(statement,data.get(dbColumnNames[i]), i+1, dataTypes[i]);
		}
	}
	public static void setValuesForSql(PreparedStatement statement, Object[] data) throws SQLException {
		
		if(null == data || data.length < 1) {
			return;
		}
		
		int[] dataTypes = TypeHelper.getDataTypes(data);
		for(int i = 0; i < dataTypes.length; i++) {
			setValue(statement,data[i], i+1, dataTypes[i]);
		}
	}
	
	
	
	public static String prepareInsertSql(String[] dbColumns, String tableName) {
		
			StringBuilder sb = new StringBuilder();
			sb.append("INSERT INTO ");
			sb.append(tableName);
			sb.append(" (");
			
			for(int i = 0; i < dbColumns.length-1; i++) {
				sb.append(dbColumns[i] + ",");
			}
			sb.append(dbColumns[dbColumns.length-1] + ") VALUES (");
			
			for(int j = 0; j < dbColumns.length-1; j++) {
				sb.append("?,");
			}
			sb.append("?)");
			return sb.toString();
    }
	
	
	/*does not support embedded conditions combinations like where (a and b) or c
	 * does not support keywords like: 'like', 'is' etc 
	 * returns only conditions like: a=b and d=e or m<>n and k<=y
	 * 
	 * 
	 * 
	 * */
	public static String prepareSimpleSqlConditions(String[] colPlusOper, String[] andOr) {
		
		StringBuilder sb = null;
		if(null != colPlusOper) {
			sb = new StringBuilder();
		}
		if(null != andOr) {

			for(int i = 0; i < andOr.length; i++) {
				sb.append(colPlusOper + " ? ");
				sb.append(andOr + " ");
			}
		}
		
		if(null != colPlusOper && colPlusOper.length > 0) {
			sb.append(colPlusOper[colPlusOper.length-1]);
			return sb.toString();
		} else {
			return null;
		}
		
		
	}

	
	public static Map parseQueryResult(ResultSet sqlRet, String[] requiredColumns, int[] columnTypes) throws SQLException {
		
		List<?> rowData = null;
		Map result = null;
		
		int colCnt = sqlRet.getMetaData().getColumnCount();
		//sqlRet.getMetaData()
		
		
		return result;		
	}
	
}
