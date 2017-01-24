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
	
	public static boolean isJavaBasicArray(Class<?> type) {
		
		if(type == int[].class) return true;
		if(type == long[].class) return true;
		if(type == float[].class) return true;
		if(type == double[].class) return true;
		if(type == short[].class) return true;
		if(type == Long[].class) return true;
		if(type == Double[].class) return true;
		if(type == Float[].class) return true;
		if(type == Integer[].class) return true;
		if(type == Short[].class) return true;
		if(type == java.math.BigDecimal[].class) return true;
		if(type == byte[].class) return true;	
		if(type == char[].class) return true;
		if(type == String[].class) return true;		
		if(type == Byte[].class) return true;
		if(type == Object[].class) return true;	
		
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
		
		try {
			return tryGetRequiredValue(origin_value, getRequiredClass(requiredType));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@MethodArgs
	public static Object tryGetRequiredValue(Object orgValue, Class<?> requiredType) {
		
		if(orgValue.getClass() == requiredType) {
			return orgValue;
		}
		
		try {
//			not a Java Array
			if(false == orgValue.getClass().isArray()) {
//				supports java String to any java basic type
				if(orgValue.getClass() == String.class 
						&& isJavaBasicType(requiredType)) {
					return convertToRequiredJavaBasic((String)orgValue, requiredType);
				}
//				supports java String with comma seperator to any java basic array
				if(orgValue.getClass() == String.class
						&& isJavaBasicArray(requiredType)) {
					String[] strArr = ((String)orgValue).split(",");
					return convertStrArrToBasicArr(strArr,requiredType);
				}
//				supports java.util.List to Object[] array
				if(orgValue.getClass() == java.util.List.class
						&& requiredType == Object[].class) {
					return ((List<?>)orgValue).toArray();
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
//				supports any java date type to any String, long or Long
				if(isJavaDateType(orgValue.getClass()) ) {
					if(requiredType == String.class) return convertJavaDateTimeToStr(orgValue);
					if(requiredType == long.class) return convertJavaDateTimeToStr(orgValue);
					if(requiredType == Long.class) 
						return Long.valueOf(convertJavaDateTimeToStr(orgValue));			
				}
				throw new IllegalArgumentException("cannot parse type " + requiredType.getSimpleName());
				
			} else {
//				Is a Java Array situation
//				supports java String[] array to any java basic array
				if(orgValue.getClass() == String[].class
						&& isJavaBasicArray(requiredType)) {
					return convertStrArrToBasicArr((String[])orgValue,requiredType);
				}
//				supports java Object[] array to any java.util.List
				if(orgValue.getClass() == Object[].class
						&& requiredType == java.util.List.class) {
					return Arrays.asList((Object[])orgValue);
				}
				throw new IllegalArgumentException("cannot parse type " + requiredType.getSimpleName());
			}
			
		} catch (IllegalArgumentException | ParseException e) {
			e.printStackTrace();
		}	
		return null;
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
	
		if(requiredType == String[].class) {
			return orgStrArr;
		}
		if(requiredType == Object[].class) {
			Object[] o_tmpArr = new Object[orgStrArr.length];
			for(int i = 0; i < orgStrArr.length; i++) 
				o_tmpArr[i] = orgStrArr[i];
			return o_tmpArr;
		}
		if(requiredType == int[].class) {
			int[] i_tmpArr = new int[orgStrArr.length];
			for(int i = 0; i < orgStrArr.length; i++) 
				i_tmpArr[i] = Integer.parseInt(orgStrArr[i].trim());
			return i_tmpArr;
		}
		if(requiredType == long[].class) {
			long[] l_tmpArr = new long[orgStrArr.length];
			for(int i = 0; i < orgStrArr.length; i++) 
				l_tmpArr[i] = Long.parseLong(orgStrArr[i].trim());
			return l_tmpArr;
		}
		if(requiredType == double[].class) {
			double[] d_tmpArr = new double[orgStrArr.length];
			for(int i = 0; i < orgStrArr.length; i++) 
				d_tmpArr[i] = Double.parseDouble(orgStrArr[i].trim());
			return d_tmpArr;
		}
		if(requiredType == float[].class) {
			float[] f_tmpArr = new float[orgStrArr.length];
			for(int i = 0; i < orgStrArr.length; i++) 
				f_tmpArr[i] = Float.parseFloat(orgStrArr[i].trim());
			return f_tmpArr;
		}
		if(requiredType == short[].class) {
			short[] sh_tmpArr = new short[orgStrArr.length];
			for(int i = 0; i < orgStrArr.length; i++) 
				sh_tmpArr[i] = Short.parseShort(orgStrArr[i].trim());
			return sh_tmpArr;
		}
		if(requiredType == byte[].class) {
			byte[] b_tmpArr = new byte[orgStrArr.length];
			for(int i = 0; i < orgStrArr.length; i++) 
				b_tmpArr[i] = Byte.parseByte(orgStrArr[i].trim());
			return b_tmpArr;
		}
		if(requiredType == char[].class) {
			char[] c_tmpArr = new char[orgStrArr.length];
			for(int i = 0; i < orgStrArr.length; i++) 
				c_tmpArr[i] = orgStrArr[i].trim().charAt(0);
			return c_tmpArr;
		}
		if(requiredType == Integer[].class) {
			Integer[] i_tmpArr = new Integer[orgStrArr.length];
			for(int i = 0; i < orgStrArr.length; i++) 
				i_tmpArr[i] = Integer.valueOf(orgStrArr[i].trim());
			return i_tmpArr;
		}
		if(requiredType == Long[].class) {
			Long[] l_tmpArr = new Long[orgStrArr.length];
			for(int i = 0; i < orgStrArr.length; i++) 
				l_tmpArr[i] = Long.valueOf(orgStrArr[i].trim());
			return l_tmpArr;
		}
		if(requiredType == Double[].class) {
			Double[] d_tmpArr = new Double[orgStrArr.length];
			for(int i = 0; i < orgStrArr.length; i++) 
				d_tmpArr[i] = Double.valueOf(orgStrArr[i].trim());
			return d_tmpArr;
		}
		if(requiredType == Float[].class) {
			Float[] f_tmpArr = new Float[orgStrArr.length];
			for(int i = 0; i < orgStrArr.length; i++) 
				f_tmpArr[i] = Float.valueOf(orgStrArr[i].trim());
			return f_tmpArr;
		}
		if(requiredType == Short[].class) {
			Short[] sh_tmpArr = new Short[orgStrArr.length];
			for(int i = 0; i < orgStrArr.length; i++) 
				sh_tmpArr[i] = Short.valueOf(orgStrArr[i].trim());
			return sh_tmpArr;
		}
		if(requiredType == Byte[].class) {
			Byte[] b_tmpArr = new Byte[orgStrArr.length];
			for(int i = 0; i < orgStrArr.length; i++) 
				b_tmpArr[i] = Byte.valueOf(orgStrArr[i].trim());
			return b_tmpArr;
		}
		if(requiredType == java.math.BigDecimal[].class) {
			java.math.BigDecimal[] b_tmpArr = new java.math.BigDecimal[orgStrArr.length];
			for(int i = 0; i < orgStrArr.length; i++) 
				b_tmpArr[i] = new BigDecimalStringConverter().fromString(orgStrArr[i].trim());
			return b_tmpArr;
		}
		throw new IllegalArgumentException("cannot convert " + requiredType.getSimpleName());
	}
	
	
	public static Object convertToRequiredJavaBasic(String orgValue, Class<?> requiredType) 
													throws ParseException, IllegalArgumentException {
		
		if(false == isJavaBasicType(requiredType))
			throw new IllegalArgumentException("cannot convert " + requiredType.getSimpleName());
		
		if(requiredType == int.class) {
			return Integer.parseInt(orgValue.trim());
		}
		if(requiredType == double.class) {
			return Double.parseDouble(orgValue.trim());
		}
		if(requiredType == float.class) {
			return Float.parseFloat(orgValue.trim());
		}
		if(requiredType == long.class) {
			return Long.parseLong(orgValue.trim());
		}
		if(requiredType == short.class) {
			return Short.parseShort(orgValue.trim());
		}
		if(requiredType == Short.class) {
			return Short.valueOf(orgValue.trim());
		}
		if(requiredType == Integer.class) {
			return Integer.valueOf(orgValue.trim());
		}
		if(requiredType == Long.class) {
			return Long.valueOf(orgValue.trim());
		}
		if(requiredType == Float.class) {
			return Float.valueOf(orgValue.trim());
		}
		if(requiredType == Double.class) {
			return Double.valueOf(orgValue.trim());
		}
		if(requiredType == Byte.class) {
			return Byte.valueOf(orgValue.trim());
		}
		if(requiredType == java.math.BigDecimal.class) {
			return new BigDecimalStringConverter().fromString(orgValue.trim());
		}
		if(requiredType == byte.class) {
			return Byte.parseByte(orgValue.trim());
		}
		if(requiredType == char.class) {
			return orgValue.trim().charAt(0);
		}
		
		throw new IllegalArgumentException("cannot convert java basic type " + requiredType.getSimpleName());
	}
	
	private static Object convertToRequiredJavaDateTime(String orgValue, Class<?> requiredType) 
														throws ParseException, IllegalArgumentException {
		if(requiredType == java.sql.Date.class) {
			return java.sql.Date.valueOf(orgValue.trim());
		}
		if(requiredType == java.util.Date.class) {
			return DateFormat.getDateInstance().parse(orgValue.trim());
		}
		if(requiredType == java.sql.Time.class) {
			return java.sql.Time.valueOf(orgValue.trim());
		}
		if(requiredType == java.sql.Timestamp.class) {
			return java.sql.Timestamp.valueOf(orgValue.trim());
		} 
		throw new IllegalArgumentException("cannot convert to java date time " + requiredType.getSimpleName());
	}
	
	public static String convertJavaDateTimeToStr(Object dateValue) {
		
		if(dateValue.getClass() == java.sql.Date.class) {
			java.sql.Date date = (java.sql.Date)dateValue;
			return date.toString();
		}
		if(dateValue.getClass() == java.sql.Time.class) {
			java.sql.Time time = (java.sql.Time)dateValue;
			return time.toString();
		}
		if(dateValue.getClass() == java.sql.Timestamp.class) {
			java.sql.Timestamp timestamp = (java.sql.Timestamp)dateValue;
			return timestamp.toString();
		}
		if(dateValue.getClass() == java.util.Date.class) {
			java.util.Date date = (java.util.Date)dateValue;
			return date.toString();
		}
		
		throw new IllegalArgumentException("cannot convert date time to string " 
						+ dateValue.getClass().getSimpleName());
	}
	
	public static long convertJavaDateTimeToLong(Object dateValue) {
		
		if(dateValue.getClass() == java.sql.Date.class) {
			java.sql.Date date = (java.sql.Date)dateValue;
			return date.getTime();
		}
		if(dateValue.getClass() == java.sql.Time.class) {
			java.sql.Time time = (java.sql.Time)dateValue;
			return time.getTime();
		}
		if(dateValue.getClass() == java.sql.Timestamp.class) {
			java.sql.Timestamp timestamp = (java.sql.Timestamp)dateValue;
			return timestamp.getTime();
		}		
		if(dateValue.getClass() == java.util.Date.class) {
			java.util.Date date = (java.util.Date)dateValue;
			return date.getTime();
		}
		throw new IllegalArgumentException("cannot convert date time to long " 
				+ dateValue.getClass().getSimpleName());
	}
	
	public static Object convertToRequiredJavaDateTime(long orgValue, Class<?> requiredType) 
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
