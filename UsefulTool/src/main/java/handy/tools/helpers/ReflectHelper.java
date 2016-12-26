package handy.tools.helpers;

import handy.tools.constants.DataTypes;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
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
	
	
}
