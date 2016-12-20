package handy.tools.interfaces;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

public abstract class ReflectHelper {
	
	public static Class getClassByName(String clazzName) throws ClassNotFoundException {
		return Class.forName(clazzName);
	}
	
	public static String[] getPropertyNames(Class clazz) {
		Field[] fields = clazz.getDeclaredFields();
		String[] names = new String[fields.length];
		
		for(int i = 0; i < fields.length; i++) {
			names[i] = fields[i].getName();
		}
		return names;
	}
	
	public static Class[] getPropertyTypes(Class clazz) {
		Field[] fields = clazz.getDeclaredFields();
		Class[] types = new Class[fields.length];
		
		for(int i = 0; i < fields.length; i++) {
			types[i] = fields[i].getType();
		}
		return types;
	}
	
	public static String[] getDeclaredMethodNames(Class clazz) {
		Method[] methods = clazz.getDeclaredMethods();
		String[] names = new String[methods.length];
		for(int i = 0; i < methods.length; i++) {
			names[i] = methods[i].getName();
			//methods[i].invoke(obj, args);
		}
		return names;
	}
	
	public static Method getOneDeclaredMethodName(Class clazz, String name, Class type) throws NoSuchMethodException, SecurityException {
		return clazz.getDeclaredMethod(name, type);
	}
	
	public static Constructor getConstructTypes(Class clazz, Class[] inputTypes) throws NoSuchMethodException, SecurityException {
		return clazz.getConstructor(inputTypes);
	}
	
	public static Object createInstance(Constructor construct, Map inputParams) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Object obj = construct.newInstance(inputParams);
		
		return obj;
	}

}
