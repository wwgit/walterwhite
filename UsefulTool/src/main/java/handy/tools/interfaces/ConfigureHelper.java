package handy.tools.interfaces;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Properties;

public abstract class ConfigureHelper {
	
	public static final int CONFIG_SUFFIX_PROPERTY = 1;
	public static final int CONFIG_SUFFIX_XML = 2;
	public static final int CONFIG_SUFFIX_JSON = 3;
		
	
	public int parseFileSuffix(String configPath) {
		
		if(configPath.endsWith("properties")) {
			return CONFIG_SUFFIX_PROPERTY;
		} else if(configPath.endsWith("xml")) {
			return CONFIG_SUFFIX_XML;
		} else if(configPath.endsWith("json")) {
			return CONFIG_SUFFIX_JSON;
		} else {
			return 0;
		}
		
	}
	
	public abstract void parseConfigure(String configPath);
	
	public void parsePropertyConf(String configPath, String clazzName) throws IOException, ClassNotFoundException,
																		InstantiationException, IllegalAccessException, 
																		NoSuchMethodException, SecurityException, 
																		IllegalArgumentException, InvocationTargetException {
		
		Properties prop = new Properties();
		prop.load(PathHelper.resolveAbsoluteStream(configPath));
		System.out.println(prop);
		
		Class<?> clazz = Class.forName(clazzName);
		Field[] fields = clazz.getDeclaredFields();
		Class<?>[] types = new Class[fields.length];
		
		Object obj = clazz.newInstance();
		StringBuilder sb = new StringBuilder();
		String propertyName = null;
		String fullName = null;
		String propertyValue = null;
		Method method = null;
		
		for(int i = 0; i < fields.length; i++) {
			
			propertyName = fields[i].getName();
			//System.out.println("propertyName: " + propertyName);
			//handy.tools.db.dbconfig.url
			//System.out.println("property full name: " + clazzName + "." + propertyName.toLowerCase());
			//propertyValue = prop.getProperty(clazzName + "." + propertyName.toLowerCase());
			fullName = clazzName + "." + propertyName.toLowerCase();
			//System.out.println("full Name: " + propertyName);
			propertyValue = prop.getProperty(propertyName.toLowerCase());
			System.out.println("propertyValue: " + propertyValue + "  " + i);
			types[i] = fields[i].getType();
			System.out.println("property types: " + types[i].getName() + "  " + i);
			propertyName = BasicHelper.UpperCaseFirstChar(propertyName, sb);
			sb.delete(0, propertyName.length());

			method = clazz.getDeclaredMethod("set" + propertyName, types[i]);
			method.invoke(obj, propertyValue);
			
		}
		
	}

}
