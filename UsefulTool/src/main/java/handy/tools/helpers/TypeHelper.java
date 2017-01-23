package handy.tools.helpers;

import handy.tools.annotations.MethodArgs;
import handy.tools.constants.ComplexValue;
import handy.tools.constants.DataTypes;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.util.Map;


import javafx.util.converter.BigDecimalStringConverter;

public abstract class TypeHelper implements DataTypes {
	
//	@MethodArgs
	public static int[] getDataTypes(Map<?,?> data) {
		
		int[] types = new int[data.size()];
		int i = 0;
		Iterator<?> it = null;
		for(it = data.values().iterator(); it.hasNext();) {
			types[i++] = parseType(it.next());
		}
		
		return types;
	}
	
//	@MethodArgs
	public static int[] getDataTypes(Object[] data) {
		
		int[] types = new int[data.length];

		for(int i = 0; i < data.length; i++) {
			types[i] = parseType(data[i]);
		}
		
		return types;
	}
	
	public static boolean isBasicOrBasicArray(String type) {
		
		if(type.equals("int")||type.equals("int[]")) {
			return true;
		} else if(type.equals("long")||type.equals("long[]")) {
			return true;
		} else if(type.equals("double")||type.equals("double[]")) {
			return true;
		} else if(type.equals("float")||type.equals("float[]")) {
			return true;
		} else if(type.equals("char")||type.equals("char[]")) {
			return true;
		} else if(type.equals("byte")||type.equals("byte[]")) {
			return true;
		} else {
			return false;
		}

	}
	
	public static boolean isBasicOrBasicArray(Class<?> type) {
		
		if(type.isPrimitive()) {
			return true;
		} else {
			return isJavaArray(type);
		}
	}
	
	public static boolean isJavaBasicType(Class<?> type) {
		
		if(type.isPrimitive()) return true;	
		if(type == String.class) return true;
		if(type == java.math.BigDecimal.class) return true;
		if(type == Long.class) return true;
		if(type == Double.class) return true;
		if(type == Float.class) return true;
		if(type == Integer.class) return true;
		if(type == Short.class) return true;
		if(type == Byte.class) return true;	
		
		return false;		
	}
	
	public static boolean isJavaDateType(Class<?> type) {
		
		if(type == java.sql.Date.class) return true;
		if(type == java.util.Date.class) return true;
		if(type == java.sql.Time.class) return true;
		if(type == java.sql.Timestamp.class) return true;
		
		return false;
	}
	
	public static boolean isJavaArray(Class<?> type) {
		
		String simpleName = type.getSimpleName();
		if(simpleName.endsWith("[]")) {
			return true;
		}		
		return false;
	}
	
	public static boolean isListOrMap(Class<?> type) {
		
		if(type == java.util.List.class) return true;
		if(type == java.util.Map.class) return true;		
		return false;
	}
	
//	@MethodArgs
	public static int parseComplex(int flag) {
		
		if(flag == 0) {
			return JAVA_LANG_BINARY_STREAM;
		} else if(flag == 1) {
			return JAVA_LANG_ASCII_STREAM;
		} else {
			return 0;
		}
	}
	
	public static int parseType(Object value) {
		
		String type = value.getClass().getName();
		int a = 1;
				
		int ret = parseType(type);
		
		if(ret == JAVA_HANDY_COMPLEX_VALUE) {
			ComplexValue cv = (ComplexValue) value;
			return parseComplex(cv.flag);
		}
		
		return ret;
	}
	
	
	public static int parseType(String type) {
		
		if(type.equals("java.lang.String")) {
			
			return JAVA_LANG_STRING;
			
		} else if(type.equals("java.lang.Object")) {
			
			return JAVA_LANG_OBJECT;
			
		} else if(type.equals("java.lang.Integer")) {
			
			return JAVA_LANG_INTEGER;
			
		} else if(type.equals("java.lang.Long")) {
			
			return JAVA_LANG_LONG;
			
		} else if(type.equals("java.math.BigDecimal")) {
			
			return JAVA_MATH_BIGDECIMAL;
			
		} else if(type.equals("JAVA_LANG_ARRAY")) {
			
			return JAVA_LANG_ARRAY;
			
		} else if(type.equals("int")) {
			
			return JAVA_BASIC_INT;
			
		} else if(type.equals("long")) {
			
			return JAVA_BASIC_LONG;
			
		} else if(type.equals("double")) {
			
			return JAVA_BASIC_DOUBLE;
			
		} else if(type.equals("handy.tools.db.ComplexValue")) {
			
			return JAVA_HANDY_COMPLEX_VALUE;
			
		}else {
			
			return 0;
		}
	}
	
	public static Object getRequiredValue(String origin_value, String requiredType) {
		int require_flag = parseType(requiredType);
		Object value = origin_value;
		
		switch(require_flag) {
		case JAVA_BASIC_INT:
			value = Integer.parseInt(origin_value);
			break;
		case JAVA_LANG_OBJECT:
			value = origin_value;
			break;
		case JAVA_BASIC_DOUBLE:
			value = Double.parseDouble(origin_value);
			break;
		case JAVA_BASIC_LONG:
			value = Long.parseLong(origin_value);
			break;
		case JAVA_LANG_INTEGER:
			value = Integer.valueOf(origin_value);
			break;
		case JAVA_LANG_LONG:
			value = Long.valueOf(origin_value);
			break;
		case JAVA_LANG_DOUBLE:
			value = Double.valueOf(origin_value);
			break;
		case JAVA_LANG_STRING:
			value = origin_value;
			break;
		case JAVA_MATH_BIGDECIMAL:
			value = new BigDecimalStringConverter().fromString(origin_value);
			break;
		default:
			value = origin_value;
			break;	
		}
		
		return value;
	}
	
//	@MethodArgs
	public static Object getRequiredValue(int origin_value, String requiredType) {
		int require_flag = parseType(requiredType);
		Object value = null;
		
		switch(require_flag) {
		case JAVA_BASIC_INT:
			value = origin_value;
			break;
		case JAVA_BASIC_DOUBLE:
			value = (double)origin_value;
			break;
		case JAVA_BASIC_LONG:
			value = (long)origin_value;;
			break;
		case JAVA_LANG_INTEGER:
			value = Integer.valueOf(origin_value);
			break;
		case JAVA_LANG_LONG:
			value = Long.valueOf((long)origin_value);
			break;
		case JAVA_LANG_DOUBLE:
			value = Double.valueOf((double)origin_value);
			break;
		case JAVA_LANG_STRING:
			value = String.valueOf(origin_value);
			break;
		case JAVA_LANG_OBJECT:
			value = origin_value;
			break;
		case JAVA_MATH_BIGDECIMAL:
			value = new BigDecimal(origin_value);
			break;
		default:
			value = String.valueOf(origin_value);
			break;	
		}
		
		return value;
	}
	
	public static Object getRequiredValue(Object origin_value, String requiredType) {
		
		String str = String.valueOf(origin_value);
		
		Object value = getRequiredValue(str, requiredType);		
		return value;
	}
	
	
	
//	@MethodArgs
	public static Class<?> getRequireClass(String type) throws ClassNotFoundException {
		
		Class<?> requiredClz = null;
		int dataType = parseType(type);
		
		switch(dataType) {
		case DataTypes.JAVA_BASIC_INT:
			requiredClz = int.class;
			break;
		case DataTypes.JAVA_BASIC_DOUBLE:
			requiredClz = double.class;
			break;
		case DataTypes.JAVA_BASIC_LONG:
			requiredClz = long.class;
			break;
		case DataTypes.JAVA_LANG_INTEGER:
			requiredClz = Integer.class;
			break;
		case DataTypes.JAVA_LANG_DOUBLE:
			requiredClz = Double.class;
			break;
		case DataTypes.JAVA_LANG_LONG:
			requiredClz = Long.class;
			break;	
		case DataTypes.JAVA_LANG_STRING:
			requiredClz = String.class;
			break;	
		default:
			requiredClz = Class.forName(type);
			break;
		}
//		System.out.println("checkpoint before return !");
		return requiredClz;
	}
	
	public static Object getRequiredValue(Object orgValue, Class<?> requiredType) {
		
		Object value = null;
		
		if(requiredType == orgValue.getClass())
			return orgValue;
		
		if(requiredType == int[].class) {
//			if()
		}
		
		
		return value;
	}
	
	
	private static Object getRequiredValueForJavaArray(String[] orgValue, Class<?> requiredType) 
			throws ParseException, Exception {
		
		Object[] value = null;
		value = new Object[orgValue.length];
		
//		string to java basic
		if(isJavaBasicType(requiredType)) {
			for(int i = 0; i < value.length; i++) {
				value[i] = convertToRequiredJavaBasic((String)orgValue[i], requiredType);
			}
			return value;
		}
//		string to java date time
		if(isJavaDateType(requiredType)) {
			for(int i = 0; i < value.length; i++) {
				value[i] = convertToRequiredJavaDateTime((String)orgValue[i], requiredType);
			}
			return value;
		}
//		java array to list
		if(requiredType == java.util.List.class) {
			return convertJavaArrToList(orgValue);
		}
		
		throw new IllegalArgumentException("cannot convert java array " + requiredType.getSimpleName());
	}
	
	
	
	private static Object getRequiredValueForJavaArray(long[] orgValue, Class<?> requiredType) 
			throws ParseException, Exception {
		
		Object[] value = null;
		value = new Object[orgValue.length];

//		long to java date time
		if(isJavaDateType(requiredType)) {
			for(int i = 0; i < value.length; i++) {
				value[i] = convertToRequiredJavaDateTime(orgValue[i], requiredType);
			}
			return value;
		}
//		java basic type to java basic type
		if(isJavaBasicType(requiredType)) {
			for(int i = 0; i < value.length; i++) {
				String tmp = String.valueOf(orgValue[0]);
				value[i] = convertToRequiredJavaBasic(tmp, requiredType);
			}
			return value;
		}
		
		throw new IllegalArgumentException("cannot convert java array " + requiredType.getSimpleName());
	}
	
	private static Object[] getRequiredValueForJavaArray(int[] orgValue, Class<?> requiredType) 
			throws ParseException, Exception {
		
		Object[] value = null;
		value = new Object[orgValue.length];

//		long to java date time
		if(isJavaDateType(requiredType)) {
			for(int i = 0; i < value.length; i++) {
				value[i] = convertToRequiredJavaDateTime(orgValue[i], requiredType);
			}
			return value;
		}
//		java basic type to java basic type
		if(isJavaBasicType(requiredType)) {
			for(int i = 0; i < value.length; i++) {
				String tmp = String.valueOf(orgValue[0]);
				value[i] = convertToRequiredJavaBasic(tmp, requiredType);
			}
			return value;
		}
		
		throw new IllegalArgumentException("cannot convert java array " + requiredType.getSimpleName());
	}
	
	
	private static Object getRequiredValueForJavaArray(Object[] orgValue, Class<?> requiredType) 
														throws ParseException, Exception {
		Object[] value = null;
		value = new Object[orgValue.length];
		
//		java basic type to java basic type
		if(isJavaBasicType(orgValue[0].getClass()) && isJavaBasicType(requiredType)) {
			for(int i = 0; i < value.length; i++) {
				String tmp = String.valueOf(orgValue[0]);
				value[i] = convertToRequiredJavaBasic(tmp, requiredType);
			}
			return value;
		}
		
//		java array to list
		if(requiredType == java.util.List.class) {
			return convertJavaArrToList(orgValue);
		}		
		throw new IllegalArgumentException("cannot convert java array " + requiredType.getSimpleName());
	}
	
	private static List<Object> convertJavaArrToList(Object[] orgValue) {
		
		java.util.List<Object> list = new ArrayList<Object>();
		for(int i = 0; i < orgValue.length; i++) {
			list.add(orgValue[i]);
		}
		return list;
	}
	
	private static Object[] convertJavaListToJavaArr(List<Object> list) {
		
		Object[] objs = new Object[list.size()];
		for(int i = 0; i < list.size(); i++) {
			objs[i] = list.get(i);
		}
		return objs;
	}
	
	
	private static Object convertToRequiredJavaBasic(String orgValue, Class<?> requiredType) 
													throws ParseException, IllegalArgumentException {
		
		if(false == isJavaBasicType(requiredType) || false == isJavaDateType(requiredType))
			throw new IllegalArgumentException("cannot convert " + requiredType.getSimpleName());
		
		if(requiredType == int.class) {
			return Integer.parseInt(orgValue);
		}
		if(requiredType == double.class) {
			return Double.parseDouble(orgValue);
		}
		if(requiredType == float.class) {
			return Float.parseFloat(orgValue);
		}
		if(requiredType == long.class) {
			return Long.parseLong(orgValue);
		}
		if(requiredType == short.class) {
			return Short.parseShort(orgValue);
		}
		if(requiredType == Short.class) {
			return Short.valueOf(orgValue);
		}
		if(requiredType == Integer.class) {
			return Integer.valueOf(orgValue);
		}
		if(requiredType == Long.class) {
			return Long.valueOf(orgValue);
		}
		if(requiredType == Float.class) {
			return Float.valueOf(orgValue);
		}
		if(requiredType == Double.class) {
			return Double.valueOf(orgValue);
		}
		if(requiredType == Byte.class) {
			return Byte.valueOf(orgValue);
		}
		if(requiredType == java.math.BigDecimal.class) {
			return new BigDecimalStringConverter().fromString(orgValue);
		}
		if(requiredType == byte.class) {
			return Byte.parseByte(orgValue);
		}
		if(requiredType == char.class) {
			return orgValue.charAt(0);
		}
		
		throw new IllegalArgumentException("cannot convert java basic type " + requiredType.getSimpleName());
	}
	
	private static Object convertToRequiredJavaDateTime(String orgValue, Class<?> requiredType) 
														throws ParseException, IllegalArgumentException {
		if(requiredType == java.sql.Date.class) {
			return java.sql.Date.valueOf(orgValue);
		}
		if(requiredType == java.util.Date.class) {
			return DateFormat.getDateInstance().parse(orgValue);
		}
		if(requiredType == java.sql.Time.class) {
			return java.sql.Time.valueOf(orgValue);
		}
		if(requiredType == java.sql.Timestamp.class) {
			return java.sql.Timestamp.valueOf(orgValue);
		} 
		throw new IllegalArgumentException("cannot convert to java date time " + requiredType.getSimpleName());
	}
	
	private static Object convertToRequiredJavaDateTime(long orgValue, Class<?> requiredType) 
			throws ParseException, IllegalArgumentException {
		
		if(requiredType == java.sql.Date.class) {
			return new java.sql.Date(orgValue);
		}
		if(requiredType == java.util.Date.class) {
			return new java.util.Date(orgValue);
		}
		if(requiredType == java.sql.Time.class) {
			return new java.sql.Time(orgValue);
		}
		if(requiredType == java.sql.Timestamp.class) {
			return new java.sql.Timestamp(orgValue);
		} 
		throw new IllegalArgumentException("cannot convert java date time " + requiredType.getSimpleName());
	}

	public static String getMysqlTypeDesc(Class<?> javaTypeClaz) throws Exception {
		
		if(javaTypeClaz == String.class) {
			return "VARCHAR";
		}
		if(javaTypeClaz == int.class) {
			return "INT";
		}
		if(javaTypeClaz == long.class) {
			return "INTEGER";
		}
		if(javaTypeClaz == float.class) {
			return "FLOAT";
		}
		if(javaTypeClaz == double.class) {
			return "DOUBLE";
		}
		if(javaTypeClaz == byte[].class) {
			return "BLOB";
		}
		if(javaTypeClaz == Long.class) {
			return "INTEGER";
		}
		if(javaTypeClaz == java.math.BigDecimal.class) {
			return "DECIMAL";
		}
		if(javaTypeClaz == Float.class) {
			return "FLOAT";
		}
		if(javaTypeClaz == Double.class) {
			return "DOUBLE";
		}
		if(javaTypeClaz == java.sql.Date.class) {
			return "DATE";
		}
		if(javaTypeClaz == java.sql.Time.class) {
			return "TIME";
		}
		if(javaTypeClaz == java.sql.Timestamp.class) {
			return "TIMESTAMP";
		} else {
			throw new Exception(javaTypeClaz.getName() + " Not supported !");
		}

	}

}
