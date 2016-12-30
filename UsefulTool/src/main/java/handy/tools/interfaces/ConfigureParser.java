package handy.tools.interfaces;

import handy.tools.constants.DataTypes;
import handy.tools.helpers.TypeHelper;

import java.util.Map;

import org.dom4j.Element;

public abstract class ConfigureParser {
	
	public static final int REF_BEAN_NOT_INIT = "REFERENCE_BEAN_NOT_INIT".hashCode();
	public static final int REF_LOCAL_NOT_INIT = "REFERENCE_LOCAL_NOT_INIT".hashCode();
	
	public static final int CONFIG_SUFFIX_PROPERTY = 1;
	public static final int CONFIG_SUFFIX_XML = 2;
	public static final int CONFIG_SUFFIX_JSON = 3;
	
	
	
	public abstract Map<String, Class<?>> getBeanClazzes(String configHashCode);
	public abstract Map<String,Map> BeansPropertiesValues(String configHashCode);
	public abstract Map<String,Map> BeansPropertiesRefBeanIds(String configHashCode);
	public abstract void loadConfig(String configPath);
	
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
	
	public boolean isRefBean(Object propertyValue) {
		
		int type = TypeHelper.parseType(propertyValue);
		
		if(type == DataTypes.JAVA_LANG_INTEGER || type == DataTypes.JAVA_BASIC_INT ) {
			
			int chk = ((Integer)propertyValue).intValue();				
			
			if(ConfigureParser.REF_LOCAL_NOT_INIT == chk || 
			    ConfigureParser.REF_BEAN_NOT_INIT == chk) {
				return true;			
			}					
		}
		
		return false;
	}

}
