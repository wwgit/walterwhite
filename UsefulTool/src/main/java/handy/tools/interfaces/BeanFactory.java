package handy.tools.interfaces;

import handy.tools.helpers.XmlHelper;

import java.util.HashMap;
import java.util.Map;

public abstract class BeanFactory {
	
	//beansClazz: Map<beanId, bean Class> - beanId should be unique
	private Map<String, Class<?>> beansClazz;
	
	//beanObjects: Map<beanId, bean Object> - beanId should be unique
	private Map<String, Object> beanObjects;
	
	//beanPropertyValues: Map<beanId, propertyInfo> -  beanId should be unique
	//propertyInfo: Map<attributeName, attributeValue> - attribute name should be unique
	//For the same object, property name(alias as attributeName) must be unique 
	private Map<String, Map> beanPropertyValues;
	
	//beanPropertyClazz: Map<beanId, propertyClazz> -  beanId should be unique
	//propertyClazz: Map<attributeName, attributeTypeClazz> - attribute name should be unique
	//For the same object, property name(alias as attributeName) must be unique 
	private Map<String, Map> beanPropertyClazz;
	
	
	
	
	public abstract void loadBeans(String xmlPath);
	
	public abstract void lazyLoadBeans(String xmlPath);
	
	public abstract Map<String,Map> BeansPropertiesTypes();
	
	

	public Map<String, Class<?>> getBeansClazz() {
		return beansClazz;
	}

	public void setBeansClazz(Map<String, Class<?>> beansClazz) {
		this.beansClazz = beansClazz;
	}

	public Map<String, Object> getBeanObjects() {
		return beanObjects;
	}

	public void setBeanObjects(Map<String, Object> beanObjects) {
		this.beanObjects = beanObjects;
	}

	public Map<String, Map> getBeanPropertyValues() {
		return beanPropertyValues;
	}

	public void setBeanPropertyValues(Map<String, Map> beanPropertyValues) {
		this.beanPropertyValues = beanPropertyValues;
	}

	public Map<String, Map> getBeanPropertyClazz() {
		return beanPropertyClazz;
	}

	public void setBeanPropertyClazz(Map<String, Map> beanPropertyClazz) {
		this.beanPropertyClazz = beanPropertyClazz;
	}
	
	

	

}
