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

	public Object initBean(String beanIdUniqCode) throws Exception {
		
		if(false == this.getBeanInfo().getBeansClazz().containsKey(beanIdUniqCode)) {
			throw new Exception("bean Not loaded in BeanFactory. beanId: " + beanIdUniqCode);
		}
		
		Class<?> beanClazz = this.getBeanInfo().getBeansClazz().get(beanIdUniqCode);
		Object beanObj = null;
		Map<String,Object> propertyValues = null;
		Map<String, Class<?>> propertyTypes = null;
		
		propertyValues = this.getBeanInfo().getBeanPropertyValues().get(beanIdUniqCode);
		propertyTypes = this.getBeanInfo().getBeanPropertyClazz().get(beanIdUniqCode);
		beanObj = initBeanProperties(beanClazz, beanIdUniqCode, propertyValues, propertyTypes);	
		
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
				System.out.println("removing " + beanIdUniqCode + " after getBean from cache !");
				//getBeanObjects().remove(beanIdUniqCode);
				return beanObj;
			}

			beanObj = initBean(beanIdUniqCode);
			
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
