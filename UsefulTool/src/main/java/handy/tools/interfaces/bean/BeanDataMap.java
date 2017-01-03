package handy.tools.interfaces.bean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BeanDataMap extends BeanCommons implements IBeanDataMap {

	//beanObjects: Map<beanId, bean Object> - beanId should be unique
	private Map<String, Object> beanObjects;
	
	//holding bean info
	private IBeanInfoMap beanInfo;
	
	public abstract Object getRealBean(String beanId);
	
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
		
		Map<String, Object> beanObjs = new HashMap<String, Object>();
		Object beanObj = null;
		
		if(null == this.getBeanObjects()) {
			this.setBeanObjects(new HashMap<String, Object>());
		}
		
		for(int i = 0; i < currBeanIds.size(); i++) {
			
			String beanId = currBeanIds.get(i);
			if(null != this.getBeanObjects().get(beanId)) {
				continue;				
			}		
			beanObj = getRealBean(beanId);
		}
		
		this.getBeanInfo().getCurrFileBeanIds().removeAll(this.getBeanInfo().getCurrFileBeanIds());
		this.setBeanObjects(beanObjs);		
	}
	
	public synchronized Object getBean(String beanId) {
		
		System.out.println("in getBean(beanId): default unique code:" +
									getDefaultUniqueCode());
		String realBeanId = beanId + getDefaultUniqueCode();
		
		return getRealBean(realBeanId);
	}
	
	public synchronized Object getBean(String beanId, String filePath) {	
		setCurrentFilePath(filePath);
		String realBeanId = beanId + String.valueOf(getCurrentFilePath().hashCode());
		System.out.println("in getBean(beanId,configPath): unique code:" +
							realBeanId);
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
	
	
}
