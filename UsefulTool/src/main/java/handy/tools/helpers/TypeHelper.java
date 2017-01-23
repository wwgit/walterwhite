package handy.tools.helpers;

import handy.tools.annotations.MethodArgs;
import handy.tools.constants.ComplexValue;
import handy.tools.constants.DataTypes;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
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
	
	private static Object getRequiredArrValue(String orgValue, Class<?> requiredType) {
		
		Object arrValue = null;
		String[] tmpArr = orgValue.split(",");
		if(requiredType == String[].class) {
			arrValue = tmpArr;
		}
		if(requiredType == int[].class) {
			int[] tmp_i = new int[tmpArr.length];
			for(int i = 0; i < tmpArr.length; i++) {
				tmp_i[i] = Integer.parseInt(tmpArr[i]);
			}
			arrValue = tmp_i;
		}
		if(requiredType == double[].class) {
			double[] tmp_i = new double[tmpArr.length];
			for(int i = 0; i < tmpArr.length; i++) {
				tmp_i[i] = Double.parseDouble(tmpArr[i]);
			}
			arrValue = tmp_i;
		}
		if(requiredType == float[].class) {
			float[] tmp_i = new float[tmpArr.length];
			for(int i = 0; i < tmpArr.length; i++) {
				tmp_i[i] = Float.parseFloat(tmpArr[i]);
			}
			arrValue = tmp_i;
		}
		if(requiredType == long[].class) {
			long[] tmp_i = new long[tmpArr.length];
			for(int i = 0; i < tmpArr.length; i++) {
				tmp_i[i] = Long.parseLong(tmpArr[i]);
			}
			arrValue = tmp_i;
		}
		if(requiredType == byte[].class) {
			byte[] tmp_i = new byte[tmpArr.length];
			for(int i = 0; i < tmpArr.length; i++) {
				tmp_i[i] = Byte.parseByte(tmpArr[i]);
			}
			arrValue = tmp_i;
		}
		if(requiredType == char[].class) {
			char[] tmp_i = new char[tmpArr.length];
			for(int i = 0; i < tmpArr.length; i++) {
				tmp_i[i] = tmpArr[i].charAt(0);
			}
			arrValue = tmp_i;
		}	
		
		return arrValue;
	}
	
	public static Object getRequiredValueForJavaBasic(Object orgValue, Class<?> requiredType) throws ParseException {
		
		if(orgValue.getClass() == String.class) {
			String tmp = (String) orgValue;
			return convertStrToRequiredJavaBasic(tmp,requiredType);			
		}
		
		if(orgValue.getClass() == long.class) {
			long tmp_l = (long)orgValue;			
			return convertLongToRequiredJavaBasic(tmp_l,requiredType);
		}
		
		return null;
		
	}
	
	private static Object convertStrToRequiredJavaBasic(String orgValue, Class<?> requiredType) throws ParseException {
		
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
		return null;
	}

	
	private static Object convertLongToRequiredJavaBasic(long orgValue, Class<?> requiredType) {
		
		if(false == isJavaBasicType(requiredType) || false == isJavaDateType(requiredType))
			throw new IllegalArgumentException("cannot convert " + requiredType.getSimpleName());
		
		if(requiredType == byte.class) {
			return Byte.parseByte(String.valueOf(orgValue));
		}
		if(requiredType == char.class) {
			return String.valueOf(orgValue).charAt(0);
		}
		if(requiredType == short.class) {
			return (short) orgValue;
		}
		if(requiredType == int.class) {
			return (int) orgValue;
		}
//		double,float,long
		if(requiredType.isPrimitive()) {
			return orgValue;
		}

		if(requiredType == Long.class) {
			return Long.valueOf(orgValue);
		}
		if(requiredType == String.class) {
			return String.valueOf(orgValue);
		}

		if(requiredType == Integer.class) {
			return Integer.valueOf((int)orgValue);
		}

		if(requiredType == Double.class) {
			return Double.valueOf(orgValue);
		}

		if(requiredType == Float.class) {
			return Float.valueOf(orgValue);
		}

		if(requiredType == Byte.class) {
			return Byte.valueOf(String.valueOf(orgValue));
		}

		if(requiredType == java.math.BigDecimal.class) {
			return new BigDecimal(orgValue);
		}
		if(requiredType == java.sql.Date.class) {
			return new java.sql.Date(orgValue);
		}
		if(requiredType == java.util.Date.class) {
			return new java.util.Date(orgValue);
		}
		if(requiredType == java.sql.Time.class) {
			return new java.sql.Time(orgValue);
		}
		
		return null;
	}
	
	private static Object convertIntToRequiredJavaBasic(int orgValue, Class<?> requiredType) {
		
		if(false == isJavaBasicType(requiredType))
			throw new IllegalArgumentException("cannot convert " + requiredType.getSimpleName());
		
		if(requiredType == byte.class) {
			return Byte.parseByte(String.valueOf(orgValue));
		}
		if(requiredType == char.class) {
			return String.valueOf(orgValue).charAt(0);
		}
		if(requiredType == short.class) {
			return (short) orgValue;
		}
		
//		double,long,float,int
		if(requiredType.isPrimitive()) {
			return orgValue;
		}
		if(requiredType == Long.class) {
			return Long.valueOf(orgValue);
		}
		if(requiredType == String.class) {
			return String.valueOf(orgValue);
		}

		if(requiredType == Integer.class) {
			return Integer.valueOf((int)orgValue);
		}

		if(requiredType == Double.class) {
			return Double.valueOf(orgValue);
		}

		if(requiredType == Float.class) {
			return Float.valueOf(orgValue);
		}

		if(requiredType == Byte.class) {
			return Byte.valueOf(String.valueOf(orgValue));
		}

		if(requiredType == java.math.BigDecimal.class) {
			return new BigDecimal(orgValue);
		}
		
		return null;
		
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
