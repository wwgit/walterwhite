package handy.tools.interfaces;

import handy.tools.helpers.PathHelper;
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
	
	//default hash code of config file: hash code of the first config file loaded
	private String defaultConfigHashCode;
	
	protected abstract void setBeanPropertyClazz();
	protected abstract void setBeanPropertyValues(String configPathHashCode);
	protected abstract void setBeanObjects();
	protected abstract void setBeansClazz(String configPathHashCode);
	protected abstract void setBeanPropertyRefBeanId(String configPathHashCode);
	protected abstract ConfigureParser getParser();
	protected abstract void setParser(String configPath);
	public abstract Object getBean(String beanId);
	public abstract Object getBean(String beanId, String configPath);
	

	public Map<String, Class<?>> getBeansClazz() {
		return beansClazz;
	}
	protected void setBeansClazz(Map<String, Class<?>> theBeansClazz) {
		if(null == this.getBeansClazz()) {
			this.beansClazz = theBeansClazz;
		} else {
			this.getBeansClazz().putAll(theBeansClazz);
		}
		
	}

	public Map<String, Object> getBeanObjects() {
		return beanObjects;
	}

	public void setBeanObjects(Map<String, Object> beanObjects) {
		if(null == this.getBeanObjects()) {
			this.beanObjects = beanObjects;
		} else {
			this.getBeanObjects().putAll(beanObjects);
		}
		
	}

	public Map<String, Map> getBeanPropertyValues() {
		return beanPropertyValues;
	}

	protected void setBeanPropertyValues(Map<String, Map> beanPropertyValues) {
		if(null == this.getBeanPropertyValues()) {
			this.beanPropertyValues = beanPropertyValues;
		} else {
			this.getBeanPropertyValues().putAll(beanPropertyValues);
		}
		
	}

	public Map<String, Map> getBeanPropertyClazz() {
		return beanPropertyClazz;
	}

	protected void setBeanPropertyClazz(Map<String, Map> beanPropertyClazz) {
		if(null == this.getBeanPropertyClazz()) {
			this.beanPropertyClazz = beanPropertyClazz;
		} else {
			this.getBeanPropertyClazz().putAll(beanPropertyClazz);
		}
	}
	public Map<String, Map> getBeanPropertyRefBeanId() {
		return beanPropertyRefBeanId;
	}
	protected void setBeanPropertyRefBeanId(Map<String, Map> beanPropertyRefBeanId) {
		if(null == this.getBeanPropertyRefBeanId()) {
			this.beanPropertyRefBeanId = beanPropertyRefBeanId;
		} else {
			this.getBeanPropertyRefBeanId().putAll(beanPropertyRefBeanId);
		}
		
	}
	

	public synchronized void loadBeans(String configPath) {
		
		String hashCode = String.valueOf(PathHelper.resolveAbsolutePath(configPath).hashCode());
		
		if(null == this.getDefaultConfigHashCode()) {
			this.setDefaultConfigHashCode(hashCode);
		}
		
		this.setParser(configPath);
		this.setBeansClazz(hashCode);
		this.setBeanPropertyRefBeanId(hashCode);
		this.setBeanPropertyValues(hashCode);
		this.setBeanPropertyClazz();
		this.setBeanObjects();
	}


	public synchronized void lazyLoadBeans(String configPath) {
		
		String hashCode = String.valueOf(PathHelper.resolveAbsolutePath(configPath).hashCode());
		
		if(null == this.getDefaultConfigHashCode()) {
			this.setDefaultConfigHashCode(hashCode);
		}
		
		this.setParser(configPath);
		this.setBeansClazz(hashCode);
		this.setBeanPropertyRefBeanId(hashCode);
		this.setBeanPropertyValues(hashCode);
		this.setBeanPropertyClazz();
		
	}
	public String getDefaultConfigHashCode() {
		return defaultConfigHashCode;
	}
	public void setDefaultConfigHashCode(String defaultConfigHashCode) {
		this.defaultConfigHashCode = defaultConfigHashCode;
	}


	

}
