package handy.tools.interfaces.bean;

import handy.tools.constants.Bean;
import handy.tools.constants.TxTFile;
import handy.tools.helpers.PathHelper;
import handy.tools.helpers.ReflectHelper;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public abstract class BeanParser implements Bean, TxTFile {
	
	//beansClazz: Map<beanId, bean Class> - beanId should be unique
	private Map<String, Class<?>> beansClazz;
	
	//beanPropertyClazz: Map<beanId, propertyClazzes> -  beanId should be unique
	//propertyClazzes: Map<propertyName, propertyClazz> - property name should be unique for one bean
	//For the same bean, property name must be unique
	//propertyClazzes:HashMap<String,Class<?>>
	private Map<String, Map> beanPropertyClazz;
	
	//beanPropertyValues: Map<beanId, propertyInfo> -  beanId should be unique
	//PropertyValues: Map<propertyName, propertyValue> - property name should be unique for one bean
	//For the same bean, property name must be unique 
	//PropertyValues:HashMap<String,Object>
	private Map<String, Map> beanPropertyValues;
	
	//beanPropertyRefBeanId: Map<beanId, propertyRefBeanId> -  beanId should be unique
	//propertyRefBeanId: Map<propertyName, refBeanId> - property name should be unique for one bean
	//For the same bean, property name must be unique
	//propertyRefBeanId:HashMap<String,String>
	private Map<String, Map> beanPropertyRefBeanId;	
		
	public abstract void setBeansClazz(String configHashCode);
	public abstract void BeansPropertiesValues(String configHashCode);
	public abstract void BeansPropertiesRefBeanIds(String configHashCode);
	public abstract void loadResource(String configPath);
	public abstract void loadTemplate();
	
	protected void loadBeansInfo(String beanFilePath) {
		String hashCode = loadBeanUniqCode(beanFilePath);
		setBeansClazz(hashCode);
		setBeanPropertyClazz();
		BeansPropertiesRefBeanIds(hashCode);
		BeansPropertiesValues(hashCode);
	}
	
	private String loadBeanUniqCode(String beanFilePath) {
	    String hashCode = String.valueOf(PathHelper.resolveAbsolutePath(beanFilePath).hashCode()); 
		return hashCode;
	}
	
	public Map<String, Class<?>> getBeansClazz() {
		return beansClazz;
	}
	public void setBeansClazz(Map<String, Class<?>> theBeansClazz) {
		if(null == this.getBeansClazz()) {
			this.beansClazz = theBeansClazz;
		} else {
			this.getBeansClazz().putAll(theBeansClazz);
		}
	}
	
	public Map<String, Map> getBeanPropertyClazz() {
		return beanPropertyClazz;
	}

	public void setBeanPropertyClazz() {
		
		Map<String, Map> beanPropTypes = null;
		Map<String, Class<?>> beanClazzes = this.getBeansClazz();
		
		if(null == beanClazzes || beanClazzes.size() < 1) {
			return;
		}
		
		beanPropTypes = new HashMap<String,Map>();
		for(Iterator key_it = beanClazzes.keySet().iterator(); key_it.hasNext();) {
			String key = (String) key_it.next();
			Map<String, Class<?>> propertyTypes = ReflectHelper.
												  retrieveBeanPropertyTypes(beanClazzes.get(key));
			if(null == propertyTypes) {
				continue;
			}
			beanPropTypes.put(key, propertyTypes);
		}
		
		if(null == this.getBeanPropertyClazz()) {
			this.beanPropertyClazz = beanPropTypes;
		} else {
			this.getBeanPropertyClazz().putAll(beanPropTypes);
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

	

}
