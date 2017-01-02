package handy.tools.interfaces.bean;

import handy.tools.helpers.PathHelper;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public abstract class BeanFactory extends BeanCommons {
	
	//beanObjects: Map<beanId, bean Object> - beanId should be unique
	private Map<String, Object> beanObjects;
	
	private BeanParser parser;
	
	protected abstract void initParser(String filePath);
	
	public synchronized void loadBeans(String filePath) {			
		initParser(filePath);
		setDefaultUniqueCode(filePath);
		setCurrentFilePath(filePath);		
		setBeanObjects();
	}


	public synchronized void lazyLoadBeans(String filePath) {		
		initParser(filePath);
		setDefaultUniqueCode(filePath);
		setCurrentFilePath(filePath);	
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
		
		if(this.getParser().getCurrFileBeanIds().size() < 1) return;
		List<String> currBeanIds = this.getParser().getCurrFileBeanIds();
		
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
		
		this.getParser().getCurrFileBeanIds().removeAll(this.getParser().getCurrFileBeanIds());
		this.setBeanObjects(beanObjs);		
	}
	
	@SuppressWarnings("unchecked")
	protected Object initBean(String beanId, Class<?> beanClazz) throws Exception {
		
		if(false == this.getParser().getBeansClazz().containsKey(beanId)) {
			throw new Exception("bean Not loaded in BeanFactory. beanId: " + beanId);
		}
		
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
					Map<String, String> propertyRefBeanIds = this.getParser().getBeanPropertyRefBeanId().get(beanId);
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

			beanObj = initBean(beanIdUniqCode, this.getParser().getBeansClazz().get(beanIdUniqCode));
			
			if(null != beanObj) {
				this.getBeanObjects().put(beanIdUniqCode, beanObj);
				
				//remove current bean info after completing initializing
				this.getParser().getBeanPropertyValues().remove(beanIdUniqCode);
				this.getParser().getBeanPropertyClazz().remove(beanIdUniqCode);
				this.getParser().getBeansClazz().remove(beanIdUniqCode);
				this.getParser().getBeanPropertyRefBeanId().remove(beanIdUniqCode);
			}
			
		}
		catch (Exception e) {			
			e.printStackTrace();
		}		
		
		return beanObj;
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


}
