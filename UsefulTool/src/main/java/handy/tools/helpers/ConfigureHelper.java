package handy.tools.helpers;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Properties;

public abstract class ConfigureHelper extends BasicHelper {
	
	public static final int CONFIG_SUFFIX_PROPERTY = 1;
	public static final int CONFIG_SUFFIX_XML = 2;
	public static final int CONFIG_SUFFIX_JSON = 3;
		
	
	public static int parseFileSuffix(String configPath) {
		
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
	
	
	
	public static void parsePropertyConf(String configPath, Object obj) throws IOException {
		
		Properties prop = new Properties();
		prop.load(PathHelper.resolveAbsoluteStream(configPath));
		
		Class<?> clazz = obj.getClass();
		Field[] fields = clazz.getDeclaredFields();

		String propertyName = null;
		String fullName = null;
		String propertyValue = null;
		
		for(int i = 0; i < fields.length; i++) {
			
			propertyName = fields[i].getName();
			fullName = obj.getClass().getName() + "." + propertyName.toLowerCase();
			//System.out.println("full Name: " + fullName + "  " + i);
			propertyValue = prop.getProperty(fullName);
			//System.out.println("propertyValue: " + propertyValue + "  " + i);
			ReflectHelper.callSetter(obj, fields[i].getName(), fields[i].getType().getName(), propertyValue);
			
		}
		
	}

}
