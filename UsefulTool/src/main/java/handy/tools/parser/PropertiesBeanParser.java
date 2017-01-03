package handy.tools.parser;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import handy.tools.helpers.PathHelper;
import handy.tools.helpers.TypeHelper;
import handy.tools.interfaces.bean.IBeanInfoMapParser;


/*e.g
 * beanId_realBeanId=beanClazzName
 * realBeanId.beanClazzName.propertyName=value
 * realBeanId must be unique
 * 
 * beanId_user1=Test.User
 * user1.Test.User.name=example
 * */
public class PropertiesBeanParser implements IBeanInfoMapParser {

	public static final String beanHeader = "beanId_";
	
	private Properties prop;
	
	public PropertiesBeanParser(String propPath) {
		this.setProp(propPath);
	}

	public Map<String, Class<?>> setBeansClazz(String configHashCode) {
	
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
		return beanClazzes;
	}

	//need to re-write the logic
	public Map<String, Map<String,Object>> BeansPropertiesValues(String configHashCode) {
		
		String beanId = null; Map<String,Object> propertyValues = null;
		Map<String, Map<String, Object>> beanPropertiesValues = new HashMap<String, Map<String, Object>>();
		/*for(Entry<String, Class<?>> beanClazzes : this.getBeansClazz().entrySet()) {
			beanId = beanClazzes.getKey().replaceAll(configHashCode, "");
			propertyValues = getPropertyValues(beanId, beanClazzes.getValue(),
							 this.getBeanPropertyClazz().get(beanClazzes.getKey()));
			beanPropertiesValues.put(beanClazzes.getKey(), propertyValues);
		}*/
		return beanPropertiesValues;

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

	public Map<String,Map<String, String>> BeansPropertiesRefBeanIds(String uniqCode) {
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


	public List<String> setCurrFileBeanIds(String uniqCode) {
		
		List<String> beanIds = null;
		
		beanIds = new LinkedList<String>();
		String beanId = null;
		for(Entry<Object, Object> property : this.getProp().entrySet()) {
			String beanIdStr = (String) property.getKey();
			beanId = beanIdStr.contains(beanHeader) 
					? beanIdStr.replaceFirst(beanHeader,"") + uniqCode
					: null;
			if(null != beanId) { 
				beanIds.add(beanId);
			}			
		}		
		return beanIds;
	}

	public void initParser() {

		
	}
	

}
