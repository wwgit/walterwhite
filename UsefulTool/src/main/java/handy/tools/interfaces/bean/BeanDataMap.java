package handy.tools.interfaces.bean;

import handy.tools.constants.Bean;

import java.util.List;
import java.util.Map;


/** 
* @ClassName: BeanDataMap 
* @Description: TODO(what to do) 
* @author walterwhite
* @date 2017年1月13日 下午2:11:55 
*  
*/
public abstract class BeanDataMap extends BeanCommons implements IBeanDataMap, Bean {
	
	/** 
	* @Fields beanObjects : Map<beanId, bean Object> - beanId should be unique 
	*/ 
	private Map<String, Object> beanObjects;
	
	
	/** 
	* @Fields beanGetCnt : set a limit to remove objects 
	*/ 
	private Map<String,Integer> beanGetCnt;
	
	/** 
	* @Fields beanInfo : holding bean info 
	*/ 
	private IBeanInfoMap beanInfo;
	
	/** 
	* @Title: getRealBean 
	* @Description: TODO(what to do) 
	* @param @param beanId
	* @param @return  
	* @return Object   
	* @throws 
	*/
	public abstract Object getRealBean(String beanId);
	/** 
	* @Title: initBean 
	* @Description: TODO(what to do) 
	* @param @param beanIdUniqCode
	* @param @return
	* @param @throws Exception  
	* @return Object   
	* @throws 
	*/
	public abstract Object initBean(String beanIdUniqCode) throws Exception;
	
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

	public IBeanInfoMap getBeanInfo() {
		return beanInfo;
	}

	public void setBeanInfo(IBeanInfoMap beanInfo) {
		this.beanInfo = beanInfo;
	}
	
	public void setBeanPropertyClazz() {
		this.beanInfo.setBeanPropertyClazz();
	}

	
	/* (non-Javadoc)
	 * @see handy.tools.interfaces.bean.IBeanDataMap#setBeanObjects()
	 */
	public void setBeanObjects() {
		
		if(this.getBeanInfo().getCurrFileBeanIds().size() < 1) return;
		List<String> currBeanIds = this.getBeanInfo().getCurrFileBeanIds();
		
		Object beanObj = null;
		
		try {
			for(int i = 0; i < currBeanIds.size(); i++) {
				
				String beanId = currBeanIds.get(i);
				beanObj = initBean(beanId);
				if(null != beanObj) {
					this.getBeanObjects().put(beanId, beanObj);
					this.getBeanGetCnt().put(beanId, 0);
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		this.getBeanInfo().getCurrFileBeanIds().removeAll(this.getBeanInfo().getCurrFileBeanIds());		
	}

	
	/* (non-Javadoc)
	 * @see handy.tools.interfaces.bean.IBeanDataMap#getBean(java.lang.String)
	 */
	public synchronized Object getBean(String beanId) {
		
	//	System.out.println("in getBean(beanId): default unique code:" +
	//								getDefaultUniqueCode());
		String realBeanId = beanId + getDefaultUniqueCode();
		
		return getRealBean(realBeanId);
	}

	
	/* (non-Javadoc)
	 * @see handy.tools.interfaces.bean.IBeanDataMap#getBean(java.lang.String, java.lang.String)
	 */
	public synchronized Object getBean(String beanId, String filePath) {	
		setCurrentFilePath(filePath);
		String realBeanId = beanId + loadBeanUniqCode(filePath);
	//	System.out.println("in getBean(beanId,configPath): unique code:" +
	//						realBeanId);
		return getRealBean(realBeanId);
	}
	
	public void setBeanPropertyValues(
			Map<String, Map<String, Object>> beanPropertyValues) {
		this.getBeanInfo().setBeanPropertyValues(beanPropertyValues);		
	}

	public void setBeansClazz(Map<String, Class<?>> theBeansClazz) {
		this.getBeanInfo().setBeansClazz(theBeansClazz);
		
	}

	public void setBeanPropertyRefBeanId(
			Map<String, Map<String, String>> beanPropertyRefBeanId) {
		this.getBeanInfo().setBeanPropertyRefBeanId(beanPropertyRefBeanId);	
	}

	public void setCurrFileBeanIds(List<String> currFileBeanIds) {
		this.getBeanInfo().setCurrFileBeanIds(currFileBeanIds);		
	}
	public Map<String,Integer> getBeanGetCnt() {
		return beanGetCnt;
	}
	public void setBeanGetCnt(Map<String,Integer> beanGetCnt) {
		this.beanGetCnt = beanGetCnt;
	}
	
	
}
