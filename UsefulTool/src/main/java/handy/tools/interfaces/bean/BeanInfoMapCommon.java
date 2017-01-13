package handy.tools.interfaces.bean;

import handy.tools.helpers.ReflectHelper;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/** 
* @ClassName: BeanInfoMapCommon 
* @Description: TODO(what to do) 
* @author walterwhite
* @date 2017年1月13日 下午2:12:14 
*  
*/
public abstract class BeanInfoMapCommon implements IBeanInfoMap {
	
		/** 
		* @Fields beansClazz :  beanId should be unique
		*/ 
		private Map<String, Class<?>> beansClazz;
		
		/** 
		* @Fields beanPropertyClazz :  Map<beanId, propertyClazzes> -  beanId should be unique
		* propertyClazzes: Map<propertyName, propertyClazz> - property name should be unique for one
		* For the same bean, property name must be unique
		* propertyClazzes:HashMap<String,Class<?>>
		*/ 
		private Map<String, Map<String,Class<?>>> beanPropertyClazz;
		
		/** 
		* @Fields beanPropertyValues : Map<beanId, propertyInfo> -  beanId should be unique
		* PropertyValues: Map<propertyName, propertyValue> - property name should be unique for one bean
		* For the same bean, property name must be unique
		* PropertyValues:HashMap<String,Object>
		*/ 
		private Map<String, Map<String,Object>> beanPropertyValues;
		
		/** 
		* @Fields beanPropertyRefBeanId : Map<beanId, propertyRefBeanId> -  beanId should be unique
		* propertyRefBeanId: Map<propertyName, refBeanId> - property name should be unique for one bean
		* For the same bean, property name must be unique 
		* propertyRefBeanId:HashMap<String,String>
		*/ 
		private Map<String, Map<String,String>> beanPropertyRefBeanId;
		
		/** 
		* @Fields currFileBeanIds : 
		* for saving bean ids in current file loaded - in order to SetBeanObjects quickly 
		*/ 
		private List<String> currFileBeanIds;
		
		public BeanInfoMapCommon(String filePath) {
			initBeanInfo();
		}
		
		public BeanInfoMapCommon() {
			initBeanInfo();
		}
		
		public void initBeanInfo() {
			this.beansClazz = new HashMap<String, Class<?>>();
			this.beanPropertyClazz = new HashMap<String, Map<String,Class<?>>>();
			this.beanPropertyValues = new HashMap<String, Map<String,Object>>();
			this.beanPropertyRefBeanId = new HashMap<String, Map<String,String>>();
			this.currFileBeanIds = new LinkedList<String>();
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
		
		public Map<String, Map<String,Class<?>>> getBeanPropertyClazz() {
			return beanPropertyClazz;
		}

		public void setBeanPropertyClazz() {
			
			Map<String, Map<String, Class<?>>> beanPropTypes = null;
			Map<String, Class<?>> beanClazzes = this.getBeansClazz();
			
			if(null == beanClazzes || beanClazzes.size() < 1) {
				return;
			}
			
			beanPropTypes = new HashMap<String,Map<String, Class<?>>>();
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
		
		public Map<String, Map<String, Object>> getBeanPropertyValues() {
			return beanPropertyValues;
		}

		public void setBeanPropertyValues(Map<String, Map<String, Object>> beanPropertyValues) {
			if(null == this.getBeanPropertyValues()) {
				this.beanPropertyValues = beanPropertyValues;
			} else {
				this.getBeanPropertyValues().putAll(beanPropertyValues);
			}
			
		}
		
		public Map<String,Map<String, String>> getBeanPropertyRefBeanId() {
			return beanPropertyRefBeanId;
		}
		public void setBeanPropertyRefBeanId(Map<String, Map<String, String>> beanPropertyRefBeanId) {
			if(null == this.getBeanPropertyRefBeanId()) {
				this.beanPropertyRefBeanId = beanPropertyRefBeanId;
			} else {
				this.getBeanPropertyRefBeanId().putAll(beanPropertyRefBeanId);
			}
			
		}
		public List<String> getCurrFileBeanIds() {
			return currFileBeanIds;
		}
		public void setCurrFileBeanIds(List<String> currFileBeanIds) {
			if(null == this.getCurrFileBeanIds()) {
				this.currFileBeanIds = currFileBeanIds;
			} else {
				this.getCurrFileBeanIds().addAll(currFileBeanIds);
			}
			
		}

}
