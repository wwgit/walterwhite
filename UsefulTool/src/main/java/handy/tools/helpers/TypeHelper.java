package handy.tools.helpers;

import handy.tools.annotations.MethodArgs;
import handy.tools.constants.ComplexValue;
import handy.tools.constants.DataTypes;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
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
	
	public static boolean isJavaBasicType(String type) throws ClassNotFoundException {
		
		Class<?> theType = getRequiredClass(type);
		return isJavaBasicType(theType);
	}
	
	public static boolean isJavaBasicType(Class<?> type) {
		
		if(type == byte.class) return true;	
		if(type == char.class) return true;
		if(type == String.class) return true;		
		if(type == Byte.class) return true;	
		
		return isJavaNumeric(type);		
	}
	
	public static boolean isJavaNumeric(Class<?> type) {
		
		if(type == int.class) return true;
		if(type == long.class) return true;
		if(type == float.class) return true;
		if(type == double.class) return true;
		if(type == short.class) return true;
		if(type == Long.class) return true;
		if(type == Double.class) return true;
		if(type == Float.class) return true;
		if(type == Integer.class) return true;
		if(type == Short.class) return true;
		if(type == java.math.BigDecimal.class) return true;
		
		return false;
	}
	
	public static boolean isJavaDateType(Class<?> type) {
		
		if(type == java.sql.Date.class) return true;
		if(type == java.util.Date.class) return true;
		if(type == java.sql.Time.class) return true;
		if(type == java.sql.Timestamp.class) return true;
		
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
	
	public static Object getRequiredValue(Object orgValue, Class<?> requiredType) {
		
		Object value = null;
		try {
			if(false == orgValue.getClass().isArray()) {
//				supports java String to any java basic type
				if(orgValue.getClass() == String.class 
						&& isJavaBasicType(requiredType)) {
					return convertToRequiredJavaBasic((String)orgValue, requiredType);
				}
//				supports java String to any java date type
				if(orgValue.getClass() == String.class 
						&& isJavaDateType(requiredType)) {
					return convertToRequiredJavaDateTime((String)orgValue, requiredType);
				}
//				supports java long to any java date type
				if(orgValue.getClass() == long.class 
						&& isJavaDateType(requiredType)) {
					return convertToRequiredJavaDateTime((long)orgValue, requiredType);
				}
//				supports java Long to any java date type
				if(orgValue.getClass() == Long.class 
						&& isJavaDateType(requiredType)) {
					return convertToRequiredJavaDateTime(((Long)orgValue).longValue(), requiredType);
				}
//				supports any java basic type to any java basic type
				if(isJavaBasicType(orgValue.getClass()) 
						&& isJavaBasicType(requiredType)) {
					String str = String.valueOf(orgValue);
					return convertToRequiredJavaBasic(str, requiredType);
				}
				
			} else {
				
			}
			
		} catch (IllegalArgumentException | ParseException e) {
			e.printStackTrace();
		}	
		
		return value;
	}
	
//	@MethodArgs
	public static Class<?> getRequiredClass(String type) throws ClassNotFoundException {
		
		if(byte.class.getSimpleName().equalsIgnoreCase(type)) {
			return byte.class;
		}
		if(byte[].class.getSimpleName().equalsIgnoreCase(type)) {
			return byte[].class;
		}
		if(char.class.getSimpleName().equalsIgnoreCase(type)) {
			return char.class;
		}
		if(char[].class.getSimpleName().equalsIgnoreCase(type)) {
			return char[].class;
		}
		if(int.class.getSimpleName().equalsIgnoreCase(type)) {
			return int.class;
		}
		if(int[].class.getSimpleName().equalsIgnoreCase(type)) {
			return int[].class;
		}
		if(long.class.getSimpleName().equalsIgnoreCase(type)) {
			return long.class;
		}
		if(long[].class.getSimpleName().equalsIgnoreCase(type)) {
			return long[].class;
		}
		if(double.class.getSimpleName().equalsIgnoreCase(type)) {
			return double.class;
		}
		if(double[].class.getSimpleName().equalsIgnoreCase(type)) {
			return double[].class;
		}
		if(float.class.getSimpleName().equalsIgnoreCase(type)) {
			return float.class;
		}
		if(float[].class.getSimpleName().equalsIgnoreCase(type)) {
			return float[].class;
		}
		if(short.class.getSimpleName().equalsIgnoreCase(type)) {
			return short.class;
		}
		if(short[].class.getSimpleName().equalsIgnoreCase(type)) {
			return short[].class;
		}
		return Class.forName(type);

	}
	
	public static Object convertStrArrToBasicArr(String[] orgStrArr, Class<?> requiredType) throws IllegalArgumentException, ParseException {
		
		if(requiredType == int.class) {
			int[] i_tmpArr = new int[orgStrArr.length];
			for(int i = 0; i < orgStrArr.length; i++) 
				i_tmpArr[i] = Integer.parseInt(orgStrArr[i]);
			return i_tmpArr;
		}
		if(requiredType == long.class) {
			long[] l_tmpArr = new long[orgStrArr.length];
			for(int i = 0; i < orgStrArr.length; i++) 
				l_tmpArr[i] = Long.parseLong(orgStrArr[i]);
			return l_tmpArr;
		}
		if(requiredType == double.class) {
			double[] d_tmpArr = new double[orgStrArr.length];
			for(int i = 0; i < orgStrArr.length; i++) 
				d_tmpArr[i] = Double.parseDouble(orgStrArr[i]);
			return d_tmpArr;
		}
		if(requiredType == float.class) {
			float[] f_tmpArr = new float[orgStrArr.length];
			for(int i = 0; i < orgStrArr.length; i++) 
				f_tmpArr[i] = Float.parseFloat(orgStrArr[i]);
			return f_tmpArr;
		}
		if(requiredType == short.class) {
			short[] sh_tmpArr = new short[orgStrArr.length];
			for(int i = 0; i < orgStrArr.length; i++) 
				sh_tmpArr[i] = Short.parseShort(orgStrArr[i]);
			return sh_tmpArr;
		}
		if(requiredType == byte.class) {
			byte[] b_tmpArr = new byte[orgStrArr.length];
			for(int i = 0; i < orgStrArr.length; i++) 
				b_tmpArr[i] = Byte.parseByte(orgStrArr[i]);
			return b_tmpArr;
		}
		if(requiredType == char.class) {
			char[] c_tmpArr = new char[orgStrArr.length];
			for(int i = 0; i < orgStrArr.length; i++) 
				c_tmpArr[i] = orgStrArr[i].charAt(0);
			return c_tmpArr;
		}
		if(requiredType == Integer.class) {
			Integer[] i_tmpArr = new Integer[orgStrArr.length];
			for(int i = 0; i < orgStrArr.length; i++) 
				i_tmpArr[i] = Integer.valueOf(orgStrArr[i]);
			return i_tmpArr;
		}
		if(requiredType == Long.class) {
			Long[] l_tmpArr = new Long[orgStrArr.length];
			for(int i = 0; i < orgStrArr.length; i++) 
				l_tmpArr[i] = Long.valueOf(orgStrArr[i]);
			return l_tmpArr;
		}
		if(requiredType == Double.class) {
			Double[] d_tmpArr = new Double[orgStrArr.length];
			for(int i = 0; i < orgStrArr.length; i++) 
				d_tmpArr[i] = Double.valueOf(orgStrArr[i]);
			return d_tmpArr;
		}
		if(requiredType == Float.class) {
			Float[] f_tmpArr = new Float[orgStrArr.length];
			for(int i = 0; i < orgStrArr.length; i++) 
				f_tmpArr[i] = Float.valueOf(orgStrArr[i]);
			return f_tmpArr;
		}
		if(requiredType == Short.class) {
			Short[] sh_tmpArr = new Short[orgStrArr.length];
			for(int i = 0; i < orgStrArr.length; i++) 
				sh_tmpArr[i] = Short.valueOf(orgStrArr[i]);
			return sh_tmpArr;
		}
		if(requiredType == Byte.class) {
			Byte[] b_tmpArr = new Byte[orgStrArr.length];
			for(int i = 0; i < orgStrArr.length; i++) 
				b_tmpArr[i] = Byte.valueOf(orgStrArr[i]);
			return b_tmpArr;
		}
		if(requiredType == java.math.BigDecimal.class) {
			java.math.BigDecimal[] b_tmpArr = new java.math.BigDecimal[orgStrArr.length];
			for(int i = 0; i < orgStrArr.length; i++) 
				b_tmpArr[i] = new BigDecimalStringConverter().fromString(orgStrArr[i]);
			return b_tmpArr;
		}
		throw new IllegalArgumentException("cannot convert " + requiredType.getSimpleName());
	}
	
	
	public static Object convertToRequiredJavaBasic(String orgValue, Class<?> requiredType) 
													throws ParseException, IllegalArgumentException {
		
		if(false == isJavaBasicType(requiredType))
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
