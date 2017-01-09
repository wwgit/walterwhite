
package handy.tools.bean;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import handy.tools.interfaces.bean.BeanDataMap;

/** 
* @ClassName: BeanDataMapImpl 
* @Description: TODO(what to do) 
* @author walterwhite
* @date 2017年1月9日 下午2:22:56 
*  
*/


public class BeanDataMapImpl extends BeanDataMap {


	public BeanDataMapImpl(String filePath) {
		this.setBeanInfo(new BeanInfoMapImpl(filePath));
		this.setBeanObjects(new HashMap<String, Object>());
		this.setBeanGetCnt(new HashMap<String, Integer>());
	}
	
	public BeanDataMapImpl() {
		this.setBeanInfo(new BeanInfoMapImpl());
		this.setBeanObjects(new HashMap<String, Object>());
		this.setBeanGetCnt(new HashMap<String, Integer>());
	}

	
	/* (non-Javadoc)
	 * @see handy.tools.interfaces.bean.BeanDataMap#initBean(java.lang.String)
	 */
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
					value = getRealBean(refBeanId);
				}
		
				initBeanProperty(beanObj, propertyName, propertyClazz, value);	
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return beanObj;

	}
	
	/* (non-Javadoc)
	 * @see handy.tools.interfaces.bean.BeanDataMap#getRealBean(java.lang.String)
	 */
	public Object getRealBean(String beanIdUniqCode) {
		
		Object beanObj = null;
		
		try {
			
			if(true == getBeanObjects().containsKey(beanIdUniqCode)) {
				beanObj = getBeanObjects().get(beanIdUniqCode);
			} 
			
			if(null != beanObj) {
				//System.out.println("removing " + beanIdUniqCode + " after getBean from cache !");
				//to enhance the performance
				int i = getBeanGetCnt().get(beanIdUniqCode) + 1;
				getBeanGetCnt().put(beanIdUniqCode, i);
				
				if(GET_BEAN_CALL_LIMIT <= getBeanGetCnt().get(beanIdUniqCode)) {
					getBeanObjects().remove(beanIdUniqCode);
				} 
				return beanObj;
			}
			beanObj = initBean(beanIdUniqCode);
			
			if(null != beanObj) {
				getBeanObjects().put(beanIdUniqCode, beanObj);
				getBeanGetCnt().put(beanIdUniqCode, 0);
			}
			
		}
		catch (Exception e) {			
			e.printStackTrace();
		}		
		
		return beanObj;
	}

}
