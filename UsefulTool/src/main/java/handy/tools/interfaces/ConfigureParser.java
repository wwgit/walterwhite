package handy.tools.interfaces;

import java.util.Map;

import org.dom4j.Element;

public abstract class ConfigureParser {
	
	public static final int REF_BEAN_NOT_INIT = "REFERENCE_BEAN_NOT_INIT".hashCode();
	public static final int REF_LOCAL_NOT_INIT = "REFERENCE_LOCAL_NOT_INIT".hashCode();
	
	public static final int CONFIG_SUFFIX_PROPERTY = 1;
	public static final int CONFIG_SUFFIX_XML = 2;
	public static final int CONFIG_SUFFIX_JSON = 3;
	
	
	
	public abstract Map<String, Class<?>> getBeanClazzes();
	public abstract Map<String,Map> BeansPropertiesValues();
	public abstract Map<String,Map> BeansPropertiesRefBeanIds();
	
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

}
