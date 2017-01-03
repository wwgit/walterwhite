package handy.tools.bean;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import handy.tools.interfaces.bean.BeanDataMap;

public class BeanDataMapImpl extends BeanDataMap {


	public BeanDataMapImpl(String filePath) {
		this.setBeanInfo(new BeanInfoMapImpl(filePath));
		this.setBeanObjects(new HashMap<String, Object>());
	}
	
	public BeanDataMapImpl() {
		this.setBeanInfo(new BeanInfoMapImpl());
		this.setBeanObjects(new HashMap<String, Object>());
	}

	private Object initBean(String beanId, Class<?> beanClazz) throws Exception {
		
		if(false == this.getBeanInfo().getBeansClazz().containsKey(beanId)) {
			throw new Exception("bean Not loaded in BeanFactory. beanId: " + beanId);
		}
		
		Object beanObj = null;
		Map<String,Object> propertyValues = null;
		Map<String, Class<?>> propertyTypes = null;
		
		propertyValues = this.getBeanInfo().getBeanPropertyValues().get(beanId);
		propertyTypes = this.getBeanInfo().getBeanPropertyClazz().get(beanId);
		beanObj = initBeanProperties(beanClazz, beanId, propertyValues, propertyTypes);	
		
		return beanObj;
	}
	
	private Object initBeanProperties(Class<?> beanClazz, String beanId, 
			  Map<String,Object> propertyValues, 
			  Map<String, Class<?>> propertyTypes) {

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
				
				if(isRefBean(value)) {
					@SuppressWarnings("unchecked")
					Map<String, String> propertyRefBeanIds = this.getBeanInfo().getBeanPropertyRefBeanId().get(beanId);
					String refBeanId = propertyRefBeanIds.get(propertyName);
					value = getBean(refBeanId, getCurrentFilePath());
				}
		
				initBeanProperty(beanObj, propertyName, propertyClazz, value);	
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return beanObj;

	}
	
	public Object getRealBean(String beanIdUniqCode) {
		
		Object beanObj = null;
		
		try {

			if(null == this.getBeanObjects()) {
				this.setBeanObjects(new HashMap<String, Object>());
			}
			
			if(true == getBeanObjects().containsKey(beanIdUniqCode)) {
				beanObj = getBeanObjects().get(beanIdUniqCode);
			} 
			
			if(null != beanObj) {
				return beanObj;
			}

			beanObj = initBean(beanIdUniqCode, this.getBeanInfo().getBeansClazz().get(beanIdUniqCode));
			
			if(null != beanObj) {
				this.getBeanObjects().put(beanIdUniqCode, beanObj);
				
				//remove current bean info after completing initializing
				this.getBeanInfo().getBeanPropertyValues().remove(beanIdUniqCode);
				this.getBeanInfo().getBeanPropertyClazz().remove(beanIdUniqCode);
				this.getBeanInfo().getBeansClazz().remove(beanIdUniqCode);
				this.getBeanInfo().getBeanPropertyRefBeanId().remove(beanIdUniqCode);
			}
			
		}
		catch (Exception e) {			
			e.printStackTrace();
		}		
		
		return beanObj;
	}

}
