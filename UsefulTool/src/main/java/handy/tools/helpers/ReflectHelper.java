package handy.tools.helpers;

import handy.tools.annotations.MethodArgs;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public abstract class ReflectHelper extends FundationHelper {
		
	
	@MethodArgs
	public static void callSetter(Object beanObj, String propertyName, String requiredType, String propertyValue) {
		
		String setterName = "set" + UpperCaseFirstChar(propertyName);
		//System.out.println("calling setter: " + setterName);
		doOneDeclareMethodCall(beanObj, setterName, requiredType, propertyValue);
		
	}
	@MethodArgs
	public static void callSetter(Object beanObj, String propertyName, Map<Object, Class<?>> values) {
		
		String setterName = "set" + UpperCaseFirstChar(propertyName);
		//System.out.println("calling setter: " + setterName + " for " + beanObj.getClass().getName());
		//System.out.println("value: " + values.toString());
		doOneDeclareMethodCall(beanObj, setterName, values);
		
	}
	
	public static Object callGetter(Object beanObj, String propertyName, Map<Object, Class<?>> values) {
		
		String getterName = "get" + UpperCaseFirstChar(propertyName);
		//System.out.println("calling setter: " + setterName + " for " + beanObj.getClass().getName());
		//System.out.println("value: " + values.toString());
		return doOneDeclareMethodCall(beanObj, getterName, values);
		
	}
	
	/*type,value,type,value,type.value....
	 * 
	 * */
//	@MethodArgs
	public static Object doOneDeclareMethodCall(Object obj, String methodName, String ... paramPairs) {
		
		Class<?>[] typeClazzes = null;
		Object[] values = null;
		Object returnObj = null;
		
		if(paramPairs.length > 0 && paramPairs.length%2==0 ) {
			typeClazzes = new Class<?>[paramPairs.length/2];
			values = new Object[paramPairs.length/2];
		}
			
		try {
			
			for(int i = 0,j = 0; i < paramPairs.length; i+=2,j++) {
				
				typeClazzes[i] = TypeHelper.getRequireClass(paramPairs[i]);				
				//flag = TypeHelper.parseType(paramPairs[i]);
				values[j] = TypeHelper.getRequiredValue(paramPairs[i+1], paramPairs[i]);
			}
			Method method = obj.getClass().getDeclaredMethod(methodName, typeClazzes);
			returnObj = method.invoke(obj, values);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return returnObj;
	}
	
	
	public static Object doOneDeclareMethodCall(Object obj, String methodName, Map<Object, Class<?>> values_clazz) {
		
		Class<?>[] paramClazzes = null;
		Object[] paramValues = null;
		Object returnObj = null;
		
		if(values_clazz.keySet().isEmpty()) {
			return null;
		}
		
		paramClazzes = new Class<?>[values_clazz.size()];
		paramValues = new Object[values_clazz.size()];
		
		int i = 0;
		for(Map.Entry<Object, Class<?>> entry : values_clazz.entrySet()) {
			paramClazzes[i] = entry.getValue();
			paramValues[i] = entry.getKey();
		}
		
		try {
						
			Method method = obj.getClass().getDeclaredMethod(methodName, paramClazzes);
			returnObj = method.invoke(obj, paramValues);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return returnObj;
		
	}
	
	/*map structure of one bean property: key-value = property name(key) - property type(value)
	 * 
	 * */
	@MethodArgs
	public static Map<String, Class<?>> retrieveBeanPropertyTypes(Class<?> beanClaz) {

		Field[] fields = beanClaz.getDeclaredFields();
		Map<String, Class<?>> properties = new HashMap<String, Class<?>>();
		
		for(int i = 0; i < fields.length; i++) {
			properties.put(fields[i].getName(), fields[i].getType());
		}
		
		return properties;
	}
	
	
	
}
