package handy.tools.interfaces.bean;

import handy.tools.constants.Bean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BeanDataMap extends BeanCommons implements IBeanDataMap, Bean {

	//beanObjects: Map<beanId, bean Object> - beanId should be unique
	private Map<String, Object> beanObjects;
	
	//set a limit to remove objects
	private Map<String,Integer> beanGetCnt;
	
	//holding bean info
	private IBeanInfoMap beanInfo;
	
	public abstract Object getRealBean(String beanId);
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
	
	public void setBeanObjects() {
		
		if(this.getBeanInfo().getCurrFileBeanIds().size() < 1) return;
		List<String> currBeanIds = this.getBeanInfo().getCurrFileBeanIds();
		
		//Map<String, Object> beanObjs = new HashMap<String, Object>();
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
	
	public synchronized Object getBean(String beanId) {
		
	//	System.out.println("in getBean(beanId): default unique code:" +
	//								getDefaultUniqueCode());
		String realBeanId = beanId + getDefaultUniqueCode();
		
		return getRealBean(realBeanId);
	}
	
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
