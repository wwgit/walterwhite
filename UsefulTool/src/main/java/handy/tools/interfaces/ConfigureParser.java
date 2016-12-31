package handy.tools.interfaces;

import handy.tools.constants.DataTypes;
import handy.tools.helpers.PathHelper;
import handy.tools.helpers.ReflectHelper;
import handy.tools.helpers.TypeHelper;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.dom4j.Element;

public abstract class ConfigureParser {
	
	public static final int REF_BEAN_NOT_INIT = "REFERENCE_BEAN_NOT_INIT".hashCode();
	public static final int REF_LOCAL_NOT_INIT = "REFERENCE_LOCAL_NOT_INIT".hashCode();
	
	public static final int CONFIG_SUFFIX_PROPERTY = 1;
	public static final int CONFIG_SUFFIX_XML = 2;
	public static final int CONFIG_SUFFIX_JSON = 3;
	
	//default hash code of config file: hash code of the first config file loaded
	private String defaultConfigHashCode;
	
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
	public abstract void loadConfig(String configPath);
	
	public void loadBeansInfo(String configPath) {
		String hashCode = loadConfigHashCode(configPath);
		setBeansClazz(hashCode);
		setBeanPropertyClazz();
		BeansPropertiesRefBeanIds(hashCode);
		BeansPropertiesValues(hashCode);
	}
	
	public String loadConfigHashCode(String configPath) {
	    String hashCode = String.valueOf(PathHelper.resolveAbsolutePath(configPath).hashCode());
		if(null == this.getDefaultConfigHashCode()) {
			this.setDefaultConfigHashCode(hashCode);
		}
		return hashCode;
	}
	
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
	
	public String getDefaultConfigHashCode() {
		return defaultConfigHashCode;
	}
	public void setDefaultConfigHashCode(String defaultConfigHashCode) {
		this.defaultConfigHashCode = defaultConfigHashCode;
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
