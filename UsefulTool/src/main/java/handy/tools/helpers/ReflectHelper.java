package handy.tools.helpers;

import handy.tools.constants.DataTypes;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public abstract class ReflectHelper extends BasicHelper {
	
	
	public static void callSetter(Object beanObj, String propertyName, String requiredType, String propertyValue) {
		
		String setterName = "set" + UpperCaseFirstChar(propertyName);
		doOneDeclareMethodCall(beanObj, setterName, requiredType, propertyValue);
		
	}
	
	public static void callSetter(Object beanObj, String propertyName, Map<Object, Class<?>> values) {
		
		String setterName = "set" + UpperCaseFirstChar(propertyName);
		//Map<Object, String> values = new HashMap<Object, String>();
		//values.put(propertyValue, requiredType);
		doOneDeclareMethodCall(beanObj, setterName, values);
		
	}
	
	/*type,value,type,value,type.value....
	 * 
	 * */
	public static void doOneDeclareMethodCall(Object obj, String methodName, String ... paramPairs) {
		
		Class<?>[] typeClazzes = null;
		Object[] values = null;
		
		if(paramPairs.length > 0 && paramPairs.length%2==0 ) {
			typeClazzes = new Class<?>[paramPairs.length/2];
			values = new Object[paramPairs.length/2];
		}
			
		try {
			
			for(int i = 0,j = 0; i < paramPairs.length; i+=2,j++) {
				
				typeClazzes[i] = getRequireClass(paramPairs[i]);				
				//flag = TypeHelper.parseType(paramPairs[i]);
				values[j] = TypeHelper.getRequiredValue(paramPairs[i+1], paramPairs[i]);
			}
			Method method = obj.getClass().getDeclaredMethod(methodName, typeClazzes);
			method.invoke(obj, values);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void doOneDeclareMethodCall(Object obj, String methodName, Map<Object, Class<?>> values_clazz) {
		
		Class<?>[] paramClazzes = null;
		Object[] paramvalues = null;
		
		paramClazzes = (Class<?>[]) values_clazz.values().toArray();
		paramvalues = values_clazz.keySet().toArray();
		
		try {
						
			Method method = obj.getClass().getDeclaredMethod(methodName, paramClazzes);
			method.invoke(obj, paramvalues);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	/*map structure of one bean property: key-value = property name(key) - property type(value)
	 * 
	 * */
	public static Map<String, Class<?>> retrieveBeanPropertyTypes(Class<?> beanClaz) {
		
		Field[] fields = beanClaz.getDeclaredFields();
		Map<String, Class<?>> properties = new HashMap<String, Class<?>>();
		
		for(int i = 0; i < fields.length; i++) {
			properties.put(fields[i].getName(), fields[i].getType());
		}
		
		return properties;
	}
	
}
