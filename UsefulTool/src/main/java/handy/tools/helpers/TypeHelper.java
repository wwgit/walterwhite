package handy.tools.helpers;

import handy.tools.annotations.MethodArgs;
import handy.tools.constants.DataTypes;
import handy.tools.db.ComplexValue;

import java.math.BigDecimal;
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
			if(type.equals(int[].class)) {
				return true;
			} else if(type.equals(long[].class)) {
				return true;
			} else if(type.equals(double[].class)) {
				return true;
			} else if(type.equals(float[].class)) {
				return true;
			} else if(type.equals(byte[].class)) {
				return true;
			} else if(type.equals(char[].class)) {
				return true;
			} else {
				return false;
			}
		}

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
		//System.out.println("get required value for " + str);
		
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
		System.out.println("checkpoint before return !");
		return requiredClz;
	}

}
