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
	
	
	protected abstract Map<String,Map> BeansPropertiesTypes();
	
	protected abstract void setBeanPropertyClazz();
	protected abstract void setBeanPropertyValues();
	protected abstract void setBeanObjects();
	protected abstract void setBeansClazz();
	protected abstract ConfigureParser getParser();
	protected abstract void setParser(String configPath);
	

	public Map<String, Class<?>> getBeansClazz() {
		return beansClazz;
	}

	protected void setBeansClazz(Map<String, Class<?>> beansClazz) {
		this.beansClazz = beansClazz;
	}

	public Map<String, Object> getBeanObjects() {
		return beanObjects;
	}

	protected void setBeanObjects(Map<String, Object> beanObjects) {
		this.beanObjects = beanObjects;
	}

	public Map<String, Map> getBeanPropertyValues() {
		return beanPropertyValues;
	}

	protected void setBeanPropertyValues(Map<String, Map> beanPropertyValues) {
		this.beanPropertyValues = beanPropertyValues;
	}

	public Map<String, Map> getBeanPropertyClazz() {
		return beanPropertyClazz;
	}

	protected void setBeanPropertyClazz(Map<String, Map> beanPropertyClazz) {
		this.beanPropertyClazz = beanPropertyClazz;
	}
	

	public void loadBeans(String configPath) {
		this.setParser(configPath);
		this.setBeansClazz();
		this.setBeanPropertyValues();
		this.setBeanPropertyClazz();
		this.setBeanObjects();
	}


	public void lazyLoadBeans(String configPath) {
		this.setParser(configPath);
		this.setBeansClazz();
		this.setBeanPropertyValues();
		this.setBeanPropertyClazz();
		
	}	

}
