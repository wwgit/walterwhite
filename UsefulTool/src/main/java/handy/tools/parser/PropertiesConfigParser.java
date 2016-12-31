package handy.tools.parser;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import handy.tools.helpers.PathHelper;
import handy.tools.helpers.TypeHelper;
import handy.tools.interfaces.ConfigureParser;


/*e.g
 * beanId_realBeanId=beanClazzName
 * realBeanId.beanClazzName.propertyName=value
 * realBeanId must be unique
 * 
 * beanId_user1=Test.User
 * user1.Test.User.name=example
 * */
public class PropertiesConfigParser extends ConfigureParser {

	public static final String beanHeader = "beanId_";
	
	private Properties prop;
	
	public PropertiesConfigParser(String propPath) {
		loadConfig(propPath);
	}

	@Override
	public void setBeansClazz(String configHashCode) {
	
		Map<String, Class<?>> beanClazzes = null;
		
		beanClazzes = new HashMap<String, Class<?>>();
		String beanId = null;
		for(Entry<Object, Object> property : this.getProp().entrySet()) {
			String beanIdStr = (String) property.getKey();
			beanId = beanIdStr.contains(beanHeader) 
					? beanIdStr.replaceFirst(beanHeader,"") + configHashCode
					: null;
			if(null != beanId) { 
				try {
					beanClazzes.put(beanId, TypeHelper.getRequireClass((String) property.getValue()));
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}			
		}		
		this.setBeansClazz(beanClazzes);
	}

	@Override
	public void BeansPropertiesValues(String configHashCode) {
		
		String beanId = null; Map<String,Object> propertyValues = null;
		Map<String,Map> beanPropertiesValues = new HashMap<String,Map>();
		for(Entry<String, Class<?>> beanClazzes : this.getBeansClazz().entrySet()) {
			beanId = beanClazzes.getKey().replaceAll(configHashCode, "");
			propertyValues = getPropertyValues(beanId, beanClazzes.getValue(),
							 this.getBeanPropertyClazz().get(beanClazzes.getKey()));
			beanPropertiesValues.put(beanClazzes.getKey(), propertyValues);
		}
		this.setBeanPropertyValues(beanPropertiesValues);

	}
	
	public Map<String, Object> getPropertyValues(String beanId, Class<?> beanClazz, 
												Map<String,Class<?>> propertyClazzes) {
		
		Map<String,Object> propertyValues = new HashMap<String,Object>();
		
		for(Entry<String, Class<?>> property : propertyClazzes.entrySet()) {
			String propName = beanId +"." + beanClazz.getName() + "." + property.getKey().toLowerCase();
			Object value = this.getProp().get(propName);
			propertyValues.put(property.getKey(), value);
		}
		
		return propertyValues;
	}

	@Override
	public void BeansPropertiesRefBeanIds(String configHashCode) {

		return;
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
		Properties theProp = new Properties();
		try {
			theProp.load(PathHelper.resolveAbsoluteStream(propPath));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this.prop = theProp;
		}
	}

	@Override
	public void loadConfig(String propPath) {
		setProp(propPath);
		loadBeansInfo(propPath);
	}
	

}
