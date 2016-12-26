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
	
	
	public static void doOneDeclareMethodCall(Object obj, String methodName, String ... paramPairs) {
		
		Class<?>[] typeClazzes = null;
		Object[] values = null;
		
		if(paramPairs.length > 0 && paramPairs.length%2==0 ) {
			typeClazzes = new Class<?>[paramPairs.length/2];
			values = new Object[paramPairs.length/2];
		}
			
		try {
			
			int flag = 0;
			for(int i = 0,j = 0; i < paramPairs.length; i+=2,j++) {
				
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
	
	public static Object retrieveInstance(String beanClazName) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		
		Class<?> beanClaz = TypeHelper.getRequireClass(beanClazName);
				return beanClaz.newInstance();
		
	}
	
	public static List<String> retrieveBeanPropertyTypes(String beanClazName) throws ClassNotFoundException {
		
		Class<?> beanClaz = TypeHelper.getRequireClass(beanClazName);
		Field[] fields = beanClaz.getDeclaredFields();
		List<String> propertyTypes = new LinkedList<String>();
		
		for(int i = 0; i < fields.length; i++) {
			propertyTypes.add(fields[i].getType().getName());
		}
		return propertyTypes;
	}
	
	public static List<String> retrieveBeanPropertyNames(String beanClazName) throws ClassNotFoundException {
		
		Class<?> beanClaz = TypeHelper.getRequireClass(beanClazName);
		Field[] fields = beanClaz.getDeclaredFields();
		List<String> propertyNames = new LinkedList<String>();
		
		for(int i = 0; i < fields.length; i++) {
			propertyNames.add(fields[i].getName());
		}
		return propertyNames;
	}
	
	
	/*map structure of one bean property: key-value = property name(key) - property type(value)
	 * 
	 * */
	public static Map<String, String> retrieveBeanProperties(String beanClazName) throws ClassNotFoundException {
		
		Class<?> beanClaz = TypeHelper.getRequireClass(beanClazName);
		Field[] fields = beanClaz.getDeclaredFields();
		Map<String, String> properties = new HashMap<String,String>();
		
		for(int i = 0; i < fields.length; i++) {
			properties.put(fields[i].getName(), fields[i].getType().getName());
		}
		
		return properties;
	}
	
}
