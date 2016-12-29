package handy.tools.factorties;

import handy.tools.constants.DataTypes;
import handy.tools.helpers.ReflectHelper;
import handy.tools.helpers.TypeHelper;
import handy.tools.interfaces.BeanFactory;
import handy.tools.interfaces.ConfigureParser;
import handy.tools.parser.XmlConfigureParser;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class XmlBeanFactory extends BeanFactory {

	private ConfigureParser parser;
	
	public XmlBeanFactory(String xmlPath) {		
		//parser = new XmlConfigureParser(xmlPath);
		//this.loadBeans(xmlPath);
		this.lazyLoadBeans(xmlPath);
	}

	protected Object initBean(String beanId, Class<?> beanClazz) {
		
		Object beanObj = null;
		Map<String,Object> propertyValues = null;
		Map<String, Class<?>> propertyTypes = null;
		
		propertyValues = getBeanPropertyValues().get(beanId);
		propertyTypes = getBeanPropertyClazz().get(beanId);
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
				
				if(this.parser.isRefBean(value)) {
					Map<String, String> propertyRefBeanIds = getBeanPropertyRefBeanId().get(beanId);
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
		
		Object value = TypeHelper.getRequiredValue(org_value, propertyClazz.getName());
		Map<Object, Class<?>> value_type = new HashMap<Object, Class<?>>();
		value_type.put(value, propertyClazz);
		ReflectHelper.callSetter(beanObj, propertyName, value_type);
		
	}
	
	protected Map<String, Map> BeansPropertiesTypes() {
		
		Map<String, Map> beanPropTypes = null;
		Map<String, Class<?>> beanClazzes = getBeansClazz();
		
		if(null == beanClazzes || beanClazzes.size() < 1) {
			return null;
		}
		
		beanPropTypes = new HashMap<String,Map>();
		for(Iterator key_it = beanClazzes.keySet().iterator(); key_it.hasNext();) {
			String key = (String) key_it.next();
			Map<String, Class<?>> propertyTypes = ReflectHelper.
												  retrieveBeanPropertyTypes(beanClazzes.get(key));
			if(null == propertyTypes) {
				continue;
			}
			beanPropTypes.put(key, propertyTypes);
		}
		
		return beanPropTypes;
	}
	
	@Override
	public Object getBean(String beanId) {
		
		Object beanObj = null;

		try {
			
			if(null == this.getBeanObjects()) {
				this.setBeanObjects(new HashMap<String, Object>());
			}
			
			if(false == getBeanObjects().containsKey(beanId)) {
				if(false == getBeansClazz().containsKey(beanId)) {
					throw new Exception("bean Not loaded in BeanFactory. beanId: " + beanId);
				}
			} else {
				beanObj = getBeanObjects().get(beanId);
			}
			
			if(null != beanObj) {
				return beanObj;
			}

			beanObj = initBean(beanId, getBeansClazz().get(beanId));			
		}
		catch (Exception e) {			
			e.printStackTrace();
		}		
		
		return beanObj;
	}

	@Override
	protected void setBeanPropertyClazz() {
		setBeanPropertyClazz(BeansPropertiesTypes());
	}

	@Override
	protected void setBeanPropertyValues() {
		this.setBeanPropertyValues(this.getParser().BeansPropertiesValues());
	}

	@Override
	protected void setBeanObjects() {
		
		Map<String, Object> beansObjects = this.getBeanObjects();
		if(null == beansObjects) {
			beansObjects = new HashMap<String, Object>();
		}
		
		Iterator beanIdIt = this.getBeansClazz().keySet().iterator();
		String beanId = null;
		Object beanObj = null;
		Class<?> beanClazz = null;
		
		while(beanIdIt.hasNext()) {
			beanId = (String) beanIdIt.next();
			beanClazz = this.getBeansClazz().get(beanId);
			beanObj = initBean(beanId, beanClazz);
			if(null == beanObj) {
				continue;
			}
			beansObjects.put(beanId, beanObj);
		}
		this.setBeanObjects(beansObjects);
		
	}

	@Override
	protected void setBeansClazz() {
		setBeansClazz(this.getParser().getBeanClazzes());
	}

	@Override
	protected ConfigureParser getParser() {
		return this.parser;
	}

	@Override
	protected void setParser(String xmlPath) {
		if(null == this.parser) {
			this.parser = new XmlConfigureParser(xmlPath);	
		}	
	}

	@Override
	protected void setBeanPropertyRefBeanId() {
		this.setBeanPropertyRefBeanId(this.parser.BeansPropertiesRefBeanIds());		
	}
	
	
	
}