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
	//PropertyValues: Map<propertyName, propertyValue> - property name should be unique for one bean
	//For the same bean, property name must be unique 
	//PropertyValues:HashMap<String,Object>
	private Map<String, Map> beanPropertyValues;
	
	//beanPropertyClazz: Map<beanId, propertyClazzes> -  beanId should be unique
	//propertyClazzes: Map<propertyName, propertyClazz> - property name should be unique for one bean
	//For the same bean, property name must be unique
	//propertyClazzes:HashMap<String,Class<?>>
	private Map<String, Map> beanPropertyClazz;
	
	//beanPropertyRefBeanId: Map<beanId, propertyRefBeanId> -  beanId should be unique
	//propertyRefBeanId: Map<propertyName, refBeanId> - property name should be unique for one bean
	//For the same bean, property name must be unique
	//propertyRefBeanId:HashMap<String,String>
	private Map<String, Map> beanPropertyRefBeanId;
	
	protected abstract void setBeanPropertyClazz();
	protected abstract void setBeanPropertyValues();
	protected abstract void setBeanObjects();
	protected abstract void setBeansClazz();
	protected abstract void setBeanPropertyRefBeanId();
	protected abstract ConfigureParser getParser();
	protected abstract void setParser(String configPath);
	public abstract Object getBean(String beanId);
	

	public Map<String, Class<?>> getBeansClazz() {
		return beansClazz;
	}
	protected void setBeansClazz(Map<String, Class<?>> theBeansClazz) {
		this.beansClazz = theBeansClazz;
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

	protected void setBeanPropertyValues(Map<String, Map> beanPropertyValues) {
		this.beanPropertyValues = beanPropertyValues;
	}

	public Map<String, Map> getBeanPropertyClazz() {
		return beanPropertyClazz;
	}

	protected void setBeanPropertyClazz(Map<String, Map> beanPropertyClazz) {
		this.beanPropertyClazz = beanPropertyClazz;
	}
	public Map<String, Map> getBeanPropertyRefBeanId() {
		return beanPropertyRefBeanId;
	}
	protected void setBeanPropertyRefBeanId(Map<String, Map> beanPropertyRefBeanId) {
		this.beanPropertyRefBeanId = beanPropertyRefBeanId;
	}
	

	public void loadBeans(String configPath) {
		this.setParser(configPath);
		this.setBeansClazz();
		this.setBeanPropertyRefBeanId();
		this.setBeanPropertyValues();
		this.setBeanPropertyClazz();
		this.setBeanObjects();
	}


	public void lazyLoadBeans(String configPath) {
		this.setParser(configPath);
		this.setBeansClazz();
		this.setBeanPropertyRefBeanId();
		this.setBeanPropertyValues();
		this.setBeanPropertyClazz();
		
	}

	

}
