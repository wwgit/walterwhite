package handy.tools.parser;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import handy.tools.helpers.PathHelper;
import handy.tools.interfaces.ConfigureParser;


/*e.g
 * beanId=beanClazzName
 * beanId.beanClazzName.propertyName=value
 * beanId must be unique
 * 
 * user1=Test.User
 * user1.Test.User.name=example
 * */
public class PropertiesConfigParser extends ConfigureParser {

	private Properties prop;
	
	public PropertiesConfigParser(String propPath) {
		this.setProp(propPath);
	}
	

	@Override
	public Map<String, Class<?>> getBeanClazzes(String configHashCode) {
	
		Map<String, Class<?>> beanClazzes = null;
		
		beanClazzes = new HashMap<String, Class<?>>();
		String beanId = null;
		for(Entry<Object, Object> property : this.getProp().entrySet()) {
			beanId = (String) property.getKey();
			
		}
		
		return beanClazzes;
	}

	@Override
	public Map<String, Map> BeansPropertiesValues(String configHashCode) {

		return null;
	}

	@Override
	public Map<String, Map> BeansPropertiesRefBeanIds(String configHashCode) {

		return null;
	}

	public Properties getProp() {
		return prop;
	}

	public void setProp(Properties prop) {
		this.prop = prop;
	}
	
	public void setProp(String propPath) {
		
		if(null != this.getProp()) {
			return;
		}
		Properties prop = new Properties();
		try {
			prop.load(PathHelper.resolveAbsoluteStream(propPath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	@Override
	public void loadConfig(String configPath) {
		
	}
	
	
	

}
