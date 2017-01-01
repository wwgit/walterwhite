package handy.tools.interfaces.bean;

import handy.tools.helpers.PathHelper;
import handy.tools.helpers.ReflectHelper;
import handy.tools.helpers.TypeHelper;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public abstract class BeanFactory implements Bean {
	
	//beanObjects: Map<beanId, bean Object> - beanId should be unique
	private Map<String, Object> beanObjects;
	
	private BeanParser parser;
	
	protected abstract void initParser(String configPath);
	
	public synchronized void loadBeans(String configPath) {			
		initParser(configPath);
		setBeanObjects();
	}


	public synchronized void lazyLoadBeans(String configPath) {		
		initParser(configPath);		
	}
	
	protected BeanParser getParser() {
		return this.parser;
	}

	protected void setParser(BeanParser myParser) {
		this.parser = myParser;
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

	protected void setBeanObjects() {
		
		Map<String, Object> beanObjs = new HashMap<String, Object>();
			
		Iterator beanIdIt = this.getParser().getBeansClazz().keySet().iterator();
		String beanId = null;
		Object beanObj = null;
		Class<?> beanClazz = null;
		
		while(beanIdIt.hasNext()) {
			beanId = ((String) beanIdIt.next());
			beanClazz = this.getParser().getBeansClazz().get(beanId);
			beanObj = initBean(beanId, beanClazz);
			if(null == beanObj) {
				continue;
			}
			beanObjs.put(beanId, beanObj);
			beanId = null; beanObj = null;
		}
		this.setBeanObjects(beanObjs);		
	}
	
	@SuppressWarnings("unchecked")
	protected Object initBean(String beanId, Class<?> beanClazz) {
		
		Object beanObj = null;
		Map<String,Object> propertyValues = null;
		Map<String, Class<?>> propertyTypes = null;
		
		propertyValues = this.getParser().getBeanPropertyValues().get(beanId);
		propertyTypes = this.getParser().getBeanPropertyClazz().get(beanId);
		beanObj = initBeanProperties(beanClazz, beanId, propertyValues, propertyTypes);				
		
		return beanObj;
	}
	
	protected Object initBeanProperties(Class<?> beanClazz, String beanId, 
			  Map<String,Object> propertyValues, 
			  Map<String, Class<?>> propertyTypes) {

		Map<Object, Class<?>> value_type = null;
		String propertyName = null;
		Object value = null;
		Class<?> propertyClazz = null;
		Object beanObj = null;

		try {
		
			if(null == propertyValues || null == propertyTypes) {
				if(null == beanClazz) {
					throw new Exception("some input paramters is null !");
				}
				return beanClazz.newInstance();
			}
		
			beanObj = beanClazz.newInstance();
			for(Iterator key_it = propertyValues.keySet().iterator(); key_it.hasNext();) {
		
				propertyName = (String) key_it.next();
				propertyClazz = propertyTypes.get(propertyName);
				value = propertyValues.get(propertyName);
				
				if(this.getParser().isRefBean(value)) {
					@SuppressWarnings("unchecked")
					Map<String, String> propertyRefBeanIds = this.getParser().getBeanPropertyRefBeanId().get(beanId);
					String refBeanId = propertyRefBeanIds.get(propertyName);
					value = getBean(refBeanId);
				}
		
				initBeanProperty(beanObj, propertyName, propertyClazz, value);	
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return beanObj;

	}
	
	protected void initBeanProperty(Object beanObj, String propertyName, 
			Class<?> propertyClazz, Object org_value) {
		Object value = org_value;
		if(false == value.getClass().equals(propertyClazz)) {
				value = TypeHelper.getRequiredValue(org_value, propertyClazz.getName());
		}
		Map<Object, Class<?>> value_type = new HashMap<Object, Class<?>>();
		value_type.put(value, propertyClazz);
		ReflectHelper.callSetter(beanObj, propertyName, value_type);

	}	
	
	public synchronized Object getBean(String beanId) {
		
		Object beanObj = null;
		
		try {
			String realBeanId = beanId + this.getParser().getDefaultUniqueCode();
			if(null == this.getBeanObjects()) {
				this.setBeanObjects(new HashMap<String, Object>());
			}
			
			if(false == getBeanObjects().containsKey(realBeanId)) {
				if(false == this.getParser().getBeansClazz().containsKey(realBeanId)) {
					throw new Exception("bean Not loaded in BeanFactory. beanId: " + beanId);
				}
			} else {
				beanObj = getBeanObjects().get(realBeanId);
			}
			
			if(null != beanObj) {
				return beanObj;
			}

			beanObj = initBean(realBeanId, this.getParser().getBeansClazz().get(realBeanId));			
		}
		catch (Exception e) {			
			e.printStackTrace();
		}		
		
		return beanObj;
	}
	
	public synchronized Object getBean(String beanId, String configPath) {
		
		Object beanObj = null;
		
		
		try {
			String realBeanId = beanId + String.valueOf(PathHelper.resolveAbsolutePath(configPath).hashCode());
			if(null == this.getBeanObjects()) {
				this.setBeanObjects(new HashMap<String, Object>());
			}
			
			if(false == getBeanObjects().containsKey(realBeanId)) {
				if(false == this.getParser().getBeansClazz().containsKey(realBeanId)) {
					throw new Exception("bean Not loaded in BeanFactory. beanId: " + beanId);
				}
			} else {
				beanObj = getBeanObjects().get(realBeanId);
			}
			
			if(null != beanObj) {
				return beanObj;
			}

			beanObj = initBean(realBeanId, this.getParser().getBeansClazz().get(realBeanId));			
		}
		catch (Exception e) {			
			e.printStackTrace();
		}		
		
		return beanObj;
	}

}
