package handy.tools.interfaces;

import handy.tools.constants.DataTypes;
import handy.tools.db.ComplexValue;

import java.math.BigDecimal;
import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map;

public abstract class SqlHelper {
	
	protected static int[] getDataTypes(Map data) {
		
		int[] types = new int[data.size()];
		int i = 0;
		Iterator it = null;
		for(it = data.values().iterator(); it.hasNext();) {
			types[i++] = parseType(it.next());
		}
		
		return types;
	}
	
	public static int parseComplex(int flag) {
		
		if(flag == 0) {
			return DataTypes.JAVA_LANG_BINARY_STREAM;
		} else if(flag == 1) {
			return DataTypes.JAVA_LANG_ASCII_STREAM;
		} else {
			return 0;
		}
	}
	
	public static int parseType(Object value) {
		
		String type = value.getClass().toString();
		
		if(type.contains("java.lang.String")) {
			return DataTypes.JAVA_LANG_STRING;
		} else if(type.contains("java.lang.Integer")) {
			return DataTypes.JAVA_LANG_INTEGER;
		} else if(type.contains("java.lang.Long")) {
			return DataTypes.JAVA_LANG_LONG;
		} else if(type.contains("java.math.BigDecimal")) {
			return DataTypes.JAVA_MATH_BIGDECIMAL;
		} else if(type.contains("JAVA_LANG_ARRAY")) {
			return DataTypes.JAVA_LANG_ARRAY;
		} else if(type.contains("handy.tools.db.ComplexValue")) {
			ComplexValue cv = (ComplexValue)value;
			return parseComplex(cv.flag);
		}else {
			return 0;
		}
	}
	
	
	public static void setValue(PreparedStatement statement, Object value, int index, int dataType) throws SQLException {
				
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
