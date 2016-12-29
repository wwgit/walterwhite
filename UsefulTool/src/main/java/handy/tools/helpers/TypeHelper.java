package handy.tools.helpers;

import handy.tools.constants.DataTypes;
import handy.tools.db.ComplexValue;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Map;

import javafx.util.converter.BigDecimalStringConverter;

public abstract class TypeHelper extends BasicHelper {
	
	
	public static int[] getDataTypes(Map data) {
		
		int[] types = new int[data.size()];
		int i = 0;
		Iterator it = null;
		for(it = data.values().iterator(); it.hasNext();) {
			types[i++] = parseType(it.next());
		}
		
		return types;
	}
	public static int[] getDataTypes(Object[] data) {
		
		int[] types = new int[data.length];

		for(int i = 0; i < data.length; i++) {
			types[i] = parseType(data[i]);
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
		
		String type = value.getClass().getName();
		int a = 1;
				
		int ret = parseType(type);
		
		if(ret == DataTypes.JAVA_HANDY_COMPLEX_VALUE) {
			ComplexValue cv = (ComplexValue) value;
			return parseComplex(cv.flag);
		}
		
		return ret;
	}
	
	public static int parseType(String type) {
		
		if(type.equals("java.lang.String")) {
			
			return DataTypes.JAVA_LANG_STRING;
			
		} else if(type.equals("java.lang.Object")) {
			
			return DataTypes.JAVA_LANG_OBJECT;
			
		} else if(type.equals("java.lang.Integer")) {
			
			return DataTypes.JAVA_LANG_INTEGER;
			
		} else if(type.equals("java.lang.Long")) {
			
			return DataTypes.JAVA_LANG_LONG;
			
		} else if(type.equals("java.math.BigDecimal")) {
			
			return DataTypes.JAVA_MATH_BIGDECIMAL;
			
		} else if(type.equals("JAVA_LANG_ARRAY")) {
			
			return DataTypes.JAVA_LANG_ARRAY;
			
		} else if(type.equals("int")) {
			
			return DataTypes.JAVA_BASIC_INT;
			
		} else if(type.equals("long")) {
			
			return DataTypes.JAVA_BASIC_LONG;
			
		} else if(type.equals("double")) {
			
			return DataTypes.JAVA_BASIC_DOUBLE;
			
		} else if(type.equals("handy.tools.db.ComplexValue")) {
			
			return DataTypes.JAVA_HANDY_COMPLEX_VALUE;
			
		}else {
			
			return 0;
		}
	}
	
	public static Object getRequiredValue(String origin_value, String requiredType) {
		int require_flag = parseType(requiredType);
		Object value = null;
		
		switch(require_flag) {
		case DataTypes.JAVA_BASIC_INT:
			value = Integer.parseInt(origin_value);
			break;
		case DataTypes.JAVA_LANG_OBJECT:
			value = origin_value;
			break;
		case DataTypes.JAVA_BASIC_DOUBLE:
			value = Double.parseDouble(origin_value);
			break;
		case DataTypes.JAVA_BASIC_LONG:
			value = Long.parseLong(origin_value);
			break;
		case DataTypes.JAVA_LANG_INTEGER:
			value = Integer.valueOf(origin_value);
			break;
		case DataTypes.JAVA_LANG_LONG:
			value = Long.valueOf(origin_value);
			break;
		case DataTypes.JAVA_LANG_DOUBLE:
			value = Double.valueOf(origin_value);
			break;
		case DataTypes.JAVA_LANG_STRING:
			value = origin_value;
			break;
		case DataTypes.JAVA_MATH_BIGDECIMAL:
			value = new BigDecimalStringConverter().fromString(origin_value);
			break;
		default:
			break;	
		}
		
		return value;
	}
	
	public static Object getRequiredValue(int origin_value, String requiredType) {
		int require_flag = parseType(requiredType);
		Object value = null;
		
		switch(require_flag) {
		case DataTypes.JAVA_BASIC_INT:
			value = origin_value;
			break;
		case DataTypes.JAVA_BASIC_DOUBLE:
			value = (double)origin_value;
			break;
		case DataTypes.JAVA_BASIC_LONG:
			value = (long)origin_value;;
			break;
		case DataTypes.JAVA_LANG_INTEGER:
			value = Integer.valueOf(origin_value);
			break;
		case DataTypes.JAVA_LANG_LONG:
			value = Long.valueOf((long)origin_value);
			break;
		case DataTypes.JAVA_LANG_DOUBLE:
			value = Double.valueOf((double)origin_value);
			break;
		case DataTypes.JAVA_LANG_STRING:
			value = String.valueOf(origin_value);
			break;
		case DataTypes.JAVA_LANG_OBJECT:
			value = origin_value;
			break;
		case DataTypes.JAVA_MATH_BIGDECIMAL:
			value = new BigDecimal(origin_value);
			break;
		default:
			break;	
		}
		
		return value;
	}
	
	public static Object getRequiredValue(Object origin_value, String requiredType) {
		
		String str = String.valueOf(origin_value);
		
		Object value = getRequiredValue(str, requiredType);		
		return value;
	}

}
