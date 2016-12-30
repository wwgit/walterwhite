package handy.tools.factorties;

import handy.tools.helpers.PathHelper;
import handy.tools.helpers.ReflectHelper;
import handy.tools.helpers.TypeHelper;
import handy.tools.interfaces.BeanFactory;
import handy.tools.interfaces.ConfigureParser;
import handy.tools.parser.XmlConfigureParser;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/*e.g
 * <beans>
 * 	<bean id="id" class="handy.tools.parser">
 * 		<property name="url" value="value"/>
 * 	</bean>
 *  <bean id="id" class="handy.tools.parser">
 * 		<property name="url"><value>value</value></property>
 * 	</bean>
 *  <bean id="id" class="handy.tools.parser">
 * 		<property name="url"><ref local=beanId /></property>
 * 	</bean>
 * </beans>
 * 
 * */
public class XmlBeanFactory extends BeanFactory {

	private ConfigureParser parser;
	
	public XmlBeanFactory(String xmlPath) {		
		this.loadBeans(xmlPath);
	}
	
	public XmlBeanFactory() {
		
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
		Object value = org_value;
		if(false == value.getClass().equals(propertyClazz)) {
			value = TypeHelper.getRequiredValue(org_value, propertyClazz.getName());
		}
		Map<Object, Class<?>> value_type = new HashMap<Object, Class<?>>();
		value_type.put(value, propertyClazz);
		ReflectHelper.callSetter(beanObj, propertyName, value_type);
		
	}
	
	protected Map<String, Map> BeansPropertiesTypes() {
		
		Map<String, Map> beanPropTypes = null;
		Map<String, Class<?>> beanClazzes = this.getBeansClazz();
		
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
	public synchronized Object getBean(String beanId) {
		
		Object beanObj = null;
		
		try {
			String realBeanId = beanId + this.getDefaultConfigHashCode();
			if(null == this.getBeanObjects()) {
				this.setBeanObjects(new HashMap<String, Object>());
			}
			
			if(false == getBeanObjects().containsKey(realBeanId)) {
				if(false == getBeansClazz().containsKey(realBeanId)) {
					throw new Exception("bean Not loaded in BeanFactory. beanId: " + beanId);
				}
			} else {
				beanObj = getBeanObjects().get(realBeanId);
			}
			
			if(null != beanObj) {
				return beanObj;
			}

			beanObj = initBean(beanId, getBeansClazz().get(realBeanId));			
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
	protected void setBeanPropertyValues(String configPathHashCode) {
		this.setBeanPropertyValues(this.getParser().BeansPropertiesValues(configPathHashCode));
	}

	@Override
	protected void setBeanObjects() {
		
		if(null == this.getBeanObjects()) {
			this.setBeanObjects(new HashMap<String, Object>());
		}
		
		Iterator beanIdIt = this.getBeansClazz().keySet().iterator();
		String beanId = null;
		Object beanObj = null;
		Class<?> beanClazz = null;
		
		while(beanIdIt.hasNext()) {
			beanId = ((String) beanIdIt.next());
			beanClazz = this.getBeansClazz().get(beanId);
			beanObj = initBean(beanId, beanClazz);
			if(null == beanObj) {
				continue;
			}
			this.getBeanObjects().put(beanId, beanObj);
			beanId = null; beanObj = null;
		}
		
	}

	@Override
	protected void setBeansClazz(String configPathHashCode) {
		setBeansClazz(this.getParser().getBeanClazzes(configPathHashCode));
	}

	@Override
	protected ConfigureParser getParser() {
		return this.parser;
	}

	@Override
	protected void setParser(String xmlPath) {
		if(null == this.parser) {
			this.parser = new XmlConfigureParser(xmlPath);	
		} else {
			this.parser.loadConfig(xmlPath);
		}
	}

	@Override
	protected void setBeanPropertyRefBeanId(String configPathHashCode) {
		this.setBeanPropertyRefBeanId(this.parser.BeansPropertiesRefBeanIds(configPathHashCode));		
	}

	@Override
	public Object getBean(String beanId, String configPath) {
		
		Object beanObj = null;
		
		try {
			String realBeanId = beanId + PathHelper.resolveAbsolutePath(configPath).hashCode();
			if(null == this.getBeanObjects()) {
				this.setBeanObjects(new HashMap<String, Object>());
			}
			
			if(false == getBeanObjects().containsKey(realBeanId)) {
				if(false == getBeansClazz().containsKey(realBeanId)) {
					throw new Exception("bean Not loaded in BeanFactory. beanId: " + beanId);
				}
			} else {
				beanObj = getBeanObjects().get(realBeanId);
			}
			
			if(null != beanObj) {
				return beanObj;
			}

			beanObj = initBean(realBeanId, getBeansClazz().get(realBeanId));			
		}
		catch (Exception e) {			
			e.printStackTrace();
		}		
		
		return beanObj;
	}
		
	
}
