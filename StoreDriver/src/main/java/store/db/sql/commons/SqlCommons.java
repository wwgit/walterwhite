package store.db.sql.commons;

import handy.tools.constants.ComplexValue;
import handy.tools.constants.DataTypes;
import handy.tools.helpers.TypeHelper;

import java.math.BigDecimal;
import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import store.db.sql.interfaces.ISQLReporter;


/** 
* @ClassName: SqlCommons 
* @Description: TODO(sql basics) 
* @author walterwhite
* @date 2017��1��9�� ����5:54:47 
*  
*/
public abstract class SqlCommons {
	
	protected Connection createConnection(String url, String userName, String password) throws SQLException {
		
		Connection conn = null;
		if(null != userName && null != password) {
			  conn = DriverManager.getConnection(url, userName, password);
			  
		} else {
				conn = DriverManager.getConnection(url);
		}		
		return conn;		
	}
	
	protected void closeConnection(Connection conn, ISQLReporter reporter) {
		try {
			conn.close();
		} catch (SQLException e) {
			reporter.reportFailure(e);
		}
	}

	/** 
	* @Title: setSqlValue 
	* @Description: TODO(what to do) 
	* Debug has been done
	* 
	* @param @param statement
	* @param @param value
	* @param @param index
	* @param @param dataType
	* @param @throws SQLException  
	* @return void   
	* @throws 
	*/
	private void setSqlValue(PreparedStatement statement, Object value, int index, int dataType) throws SQLException {
//		System.out.println("setting index: " + index);
//		System.out.println("setting Obj value: " + value);
//		System.out.println("obj value type: " + value.getClass().getName());
		switch(dataType) {
			case DataTypes.JAVA_LANG_STRING:
				statement.setString(index, String.valueOf(value));//System.out.println("setting Str value: " + value);
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
				throw new SQLException("Type not support yet " + value.getClass().getName());
				//break;
		}
	}
	
	/*Debug has been done
	 * 
	 * 
	 * */
	protected void setValuesForSql(PreparedStatement statement, 
									Map<String,Object> colName_value, String[] dbColumnNames, int[] dataTypes) throws SQLException {
		
		if(null == colName_value || colName_value.size() < 1) {
			return;
		}
		
		for(int i = 0; i < dataTypes.length; i++) {
			setSqlValue(statement,colName_value.get(dbColumnNames[i]), i+1, dataTypes[i]);
		}
	}
	
	protected void setValuesForSql(PreparedStatement statement, Object[] data, int[] dataTypes) throws SQLException {
		
		if(null == data || data.length < 1) {
			return;
		}
		
		//System.out.println("data type length: " + dataTypes.length);
		for(int i = 0; i < data.length; i++) {
			setSqlValue(statement,data[i], i+1, dataTypes[i]);
		}
	}
	
	
}
