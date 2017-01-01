package handy.tools.interfaces.bean;

import handy.tools.constants.Bean;
import handy.tools.constants.DataTypes;
import handy.tools.helpers.PathHelper;
import handy.tools.helpers.ReflectHelper;
import handy.tools.helpers.TypeHelper;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public abstract class BeanFactory implements Bean {
	
	//beanObjects: Map<beanId, bean Object> - beanId should be unique
	private Map<String, Object> beanObjects;
	
	private BeanParser parser;
	
	//unique code of absolute path of current file
	private String currentFilePath;
	
	//default unique code: hash code of config file: hash code of the first config file loaded
	private String defaultUniqueCode;
	
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
		
		List<String> beanIds = this.getParser().getCurrFileBeanIds();
		if(null == beanIds) return;
		
		Map<String, Object> beanObjs = new HashMap<String, Object>();
		Object beanObj = null;
		
		if(null == this.getBeanObjects()) {
			this.setBeanObjects(new HashMap<String, Object>());
		}
		
		for(int i = 0; i < beanIds.size(); i++) {
			String beanId = beanIds.get(i);
			if(null != this.getBeanObjects().get(beanId)) {
				continue;
			}
			beanObj = getRealBean(beanId);
			if(null != beanObj) {
				beanObjs.put(beanId, beanObj);
			}
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
	
	public boolean isRefBean(Object propertyValue) {
		
		int type = TypeHelper.parseType(propertyValue);
		
		if(type == DataTypes.JAVA_LANG_INTEGER || type == DataTypes.JAVA_BASIC_INT ) {
			
			int chk = ((Integer)propertyValue).intValue();				
			
			if(REF_LOCAL_NOT_INIT == chk || REF_BEAN_NOT_INIT == chk) {
				return true;			
			}					
		}
		
		return false;
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
	
	public Object getRealBean(String beanIdUniqCode) {
		
		Object beanObj = null;
		
		try {

			if(null == this.getBeanObjects()) {
				this.setBeanObjects(new HashMap<String, Object>());
			}
			
			if(false == getBeanObjects().containsKey(beanIdUniqCode)) {
				if(false == this.getParser().getBeansClazz().containsKey(beanIdUniqCode)) {
					throw new Exception("bean Not loaded in BeanFactory. beanId: " + beanIdUniqCode);
				}
			} else {
				beanObj = getBeanObjects().get(beanIdUniqCode);
			}
			
			if(null != beanObj) {
				return beanObj;
			}

			beanObj = initBean(beanIdUniqCode, this.getParser().getBeansClazz().get(beanIdUniqCode));			
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


	public String getCurrentFilePath() {
		return currentFilePath;
	}

	public void setCurrentFilePath(String currentFilePath) {
		this.currentFilePath = PathHelper.resolveAbsolutePath(currentFilePath);
	}

	public String getDefaultUniqueCode() {
		return defaultUniqueCode;
	}

	public void setDefaultUniqueCode(String filePath) {
		String hashCode = null;
		if(null == this.getDefaultUniqueCode()) {
			hashCode = String.valueOf(PathHelper.resolveAbsolutePath(filePath).hashCode());
			this.defaultUniqueCode = hashCode;
		}		
		
	}

}
