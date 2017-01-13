package handy.tools.parser;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import handy.tools.helpers.FileHelper;
import handy.tools.helpers.ReflectHelper;
import handy.tools.helpers.TypeHelper;
import handy.tools.interfaces.bean.IBeanInfoMapParser;
import handy.tools.interfaces.templates.IPropBeanTempSetter;
import handy.tools.interfaces.templates.IPropBeanTemplate;


/** 
* @ClassName: PropertiesBeanParser 
* @Description: TODO(what to do) 
* e.g
* beanId_realBeanId=beanClazzName
* realBeanId.beanClazzName.propertyName=value
* realBeanId must be unique
* 
* e.g
* beanId_user1=Test.User
* user1.Test.User.name=example
* 
* @author walterwhite
* @date 2017年1月13日 下午2:10:23 
*  
*/


public class PropertiesBeanParser extends FileHelper implements IBeanInfoMapParser, IPropBeanTemplate, IPropBeanTempSetter {

	private String beanIdTab;
	
	private String clazHeaderSeperator;
	
	private String clazPropSeperator;
	
	private Map<String, Class<?>> beanClazInfo;
	
	
	private Properties prop;
	
	public PropertiesBeanParser(String propPath) {	
		loadBeanTemplate();
		setProp(propPath);
		this.beanClazInfo = new HashMap<String, Class<?>>();
	}
	
	public PropertiesBeanParser() {
	//	loadBeanTemplate();
		this.beanClazInfo = new HashMap<String, Class<?>>();
	}

	public Map<String, Class<?>> setBeansClazz(String uniqCode) {
	
		Map<String, Class<?>> beanClazzes = null;
		
		beanClazzes = new HashMap<String, Class<?>>();
		String beanId = null;
		for(Entry<Object, Object> property : this.getProp().entrySet()) {
			String beanIdStr = (String) property.getKey();
			beanId = beanIdStr.contains(BEAN_ID_TAB) 
					? beanIdStr.replaceFirst(BEAN_ID_TAB,"") + uniqCode
					: null;
			if(null != beanId) { 
				try {
					beanClazzes.put(beanId, TypeHelper.getRequireClass((String) property.getValue()));
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}			
		}
		setBeanClazInfo(beanClazzes);
		return beanClazzes;
	}

	//rewrite done
	public Map<String, Map<String,Object>> BeansPropertiesValues(String uniqCode) {
		
		String beanId = null; Map<String,Object> propertyValues = null;
		Map<String, Map<String, Object>> beanPropertiesValues = new HashMap<String, Map<String, Object>>();
		
		for(Entry<String, Class<?>> beanClazzes : this.beanClazInfo.entrySet()) {
			beanId = beanClazzes.getKey().replaceAll(uniqCode, "");
			propertyValues = getPropertyValues(beanId, beanClazzes.getValue());
			beanPropertiesValues.put(beanClazzes.getKey(), propertyValues);
		}
		return beanPropertiesValues;

	}
	
	public Map<String, Object> getPropertyValues(String beanId, Class<?> beanClazz) {
		
		Map<String,Object> propertyValues = new HashMap<String,Object>();
		Map<String, Class<?>> propertyTypes = ReflectHelper.retrieveBeanPropertyTypes(beanClazz);
		
		for(Entry<String, Class<?>> property : propertyTypes.entrySet()) {
			String propName = beanId + CLAZ_HEADER_SEPERATOR + beanClazz.getName() + CLAZ_PROP_SEPERATOR + property.getKey();
			Object value = this.getProp().get(propName);
			propertyValues.put(property.getKey(), value);
		}
		
		return propertyValues;
	}

	public Map<String,Map<String, String>> BeansPropertiesRefBeanIds(String uniqCode) {
		return new HashMap<String,Map<String, String>>();
	}

	public Properties getProp() {
		return prop;
	}

	public void setProp(Properties prop) {
		this.prop = prop;
	}
	
	public void setProp(String propPath) {
		System.out.println("who is calling me: " + this.getClass());
		try {
			Properties theProp = null;
			if(null == this.getProp()) {
				theProp = new Properties();
				theProp.load(getFileInputStream(propPath));
				this.setProp(theProp);
			} else {
				this.getProp().load(getFileInputStream(propPath));
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}


	public List<String> setCurrFileBeanIds(String uniqCode) {
		
		List<String> beanIds = null;
		
		beanIds = new LinkedList<String>();
		String beanId = null;
		for(Entry<Object, Object> property : this.getProp().entrySet()) {
			String beanIdStr = (String) property.getKey();
			beanId = beanIdStr.contains(BEAN_ID_TAB) 
					? beanIdStr.replaceFirst(BEAN_ID_TAB,"") + uniqCode
					: null;
			if(null != beanId) { 
				beanIds.add(beanId);
			}			
		}		
		return beanIds;
	}


	public void reloadParser(String filePath) {
		this.setProp(filePath);
	}

	public String getClazHeaderSeperator() {
		return clazHeaderSeperator;
	}

	public void setClazHeaderSeperator(String clazHeaderSeperator) {
		this.clazHeaderSeperator = clazHeaderSeperator;
	}

	public String getClazPropSeperator() {
		return clazPropSeperator;
	}

	public void setClazPropSeperator(String clazPropSeperator) {
		this.clazPropSeperator = clazPropSeperator;
	}

	public String getBeanIdTab() {
		return beanIdTab;
	}

	public void setBeanIdTab(String beanIdTab) {
		this.beanIdTab = beanIdTab;
	}

	public Map<String, Class<?>> getBeanClazInfo() {
		return beanClazInfo;
	}

	public void setBeanClazInfo(Map<String, Class<?>> beanClazInfo) {
		this.beanClazInfo = beanClazInfo;
	}
	
	private void loadBeanTemplate() {
		
		//load bean related template
		setBeanIdTab(BEAN_ID_TAB);
		setClazHeaderSeperator(CLAZ_HEADER_SEPERATOR);		
		setClazPropSeperator(CLAZ_PROP_SEPERATOR);		
		
	}
	

}
