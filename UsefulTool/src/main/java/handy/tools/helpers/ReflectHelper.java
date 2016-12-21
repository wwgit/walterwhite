package handy.tools.helpers;

import handy.tools.constants.DataTypes;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public abstract class ReflectHelper {
	
	
	public static void callSetter(Object beanObj, String propertyName, String requiredType, String propertyValue) {
		
		String setterName = "set" + BasicHelper.UpperCaseFirstChar(propertyName);
		doOneMethodCall(beanObj, setterName, new String[]{requiredType, propertyValue});
		
	}
	
	
	public static void doOneMethodCall(Object obj, String methodName, String ... paramPairs) {
		
		Class<?>[] typeClazzes = null;
		Object[] values = null;
		
		if(paramPairs.length > 0 && paramPairs.length%2==0 ) {
			typeClazzes = new Class<?>[paramPairs.length/2];
			values = new Object[paramPairs.length/2];
		}
			
		try {
			
			int flag = 0;
			for(int i = 0,j = 0; i < paramPairs.length; i+=2,j++) {
				
				//System.out.println("required type: " + paramPairs[i]);
				typeClazzes[i] = getRequireClass(paramPairs[i]);				
				flag = TypeHelper.parseType(paramPairs[i]);
				values[j] = TypeHelper.getRequiredValue(paramPairs[i+1], paramPairs[i]);
			}
			Method method = obj.getClass().getDeclaredMethod(methodName, typeClazzes);
			method.invoke(obj, values);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	

	public static Class<?> getRequireClass(String type) throws ClassNotFoundException {
		
		Class<?> requiredClz = null;
		int dataType = TypeHelper.parseType(type);
		
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
		
		return requiredClz;
	}
	
}
