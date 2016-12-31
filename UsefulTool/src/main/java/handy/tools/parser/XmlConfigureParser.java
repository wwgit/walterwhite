package handy.tools.parser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;

import handy.tools.helpers.XmlHelper;
import handy.tools.interfaces.ConfigureParser;


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
public class XmlConfigureParser extends ConfigureParser {
	
	private Document doc;
	private Element beans;
	private List<Element> beanElements;

	public Document getDoc() {
		return doc;
	}
	public void setDoc(Document doc) {
		this.doc = doc;
	}		
	public void setDoc(String xmlPath) {
		this.doc = XmlHelper.readXmlFrmFile(xmlPath);
	}
	public void setBeans() {
		
		Element theBeans = null;		
		theBeans = XmlHelper.findElement(getDoc(), "beans");
		if(null == theBeans) {
			throw new NullPointerException("beans element cannot be found !");
		}	
		this.beans = theBeans;
	}
	public Element getBeans() {
		return beans;
	}
	public List<Element> getBeanElements() {
		return beanElements;
	}
	public void setBeanElements() {
		List<Element> myBeanElements = getBeans().elements("bean");
		if(null == myBeanElements || myBeanElements.size() <1) {
			throw new NullPointerException("there is no bean element under beans element !");
		}
		this.beanElements = myBeanElements;
	}

	
	public XmlConfigureParser(String xmlPath) {
		loadConfig(xmlPath);
	}
	
		
	@Override
	public void setBeansClazz(String configHashCode) {
		
		Map<String, Class<?>> beansClazzes = null;
		
		try {
			beansClazzes = new HashMap<String, Class<?>>();
			List<Element> myBeanElements = this.getBeanElements();
			for(int i = 0; i < myBeanElements.size(); i++) {
				Element e = myBeanElements.get(i);
				beansClazzes.put(e.attributeValue("id") + configHashCode, XmlHelper.getRequireClass(e.attributeValue("class")));
			}
		} catch(Exception e) {
			e.printStackTrace();
		}				
		this.setBeansClazz(beansClazzes);
	}
	
	@Override
	public void BeansPropertiesValues(String configHashCode) {
		
		List<Element> beanElements = this.getBeanElements();
		Map<String, Map> beansProperties = new HashMap<String, Map>();
		for(int i = 0 ; i < beanElements.size(); i++) {
			Element bean = beanElements.get(i);
			beansProperties.put(bean.attributeValue("id") + configHashCode, getPropertyValues(bean));
		}
		this.setBeanPropertyValues(beansProperties);
	}
	
	public Map<String,Object> getPropertyValues(Element beanElement) {

		Map<String,Object> propertyValues = null;
		List<Element> propertyElements = null;
		propertyElements = beanElement.elements("property");
		if(null == propertyElements || propertyElements.size() <1) {
			return null;
		}
		
		propertyValues = new HashMap<String, Object>();
		String propertyName = null;
		Object value = null;
		try {
			for(int i = 0; i < propertyElements.size(); i++) {
				propertyName = propertyElements.get(i).attributeValue("name");
				value = getPropertyValue(propertyElements.get(i));
				propertyValues.put(propertyName, value);
			}
		} catch(Exception e) {
			e.printStackTrace();
			
		}
		
		return propertyValues;
	}
	
	
	public Object getPropertyValue(Element property) {
		
		Object value = null;
		value = property.attributeValue("value");
		
		if(null != value) {
			return value;
		}
		
		try {
			Element ele = property.element("value");
			if(null != ele) {
				value = ele.getText();
			}
			
			if(null != value) {
				return value;
			}
			
			value = getRefObject(property.element("ref"));
			
		} catch(Exception e) {
			e.printStackTrace();
		}
			
		return value;
	}


	public Object getRefObject(Element reference) {
		
		String refBeanId = null;
		Object result = null;
		
		refBeanId = reference.attributeValue("local");
		if(null != refBeanId) {
			result = REF_LOCAL_NOT_INIT;
			return result;
		}
		refBeanId = reference.attributeValue("bean");
		if(null != refBeanId) {
			result = REF_BEAN_NOT_INIT;
		}
		
		return result;
	}
	
	
	@Override
	public void BeansPropertiesRefBeanIds(String configHashCode) {
		
		List<Element> beanElements = this.getBeanElements();
		Map<String, Map> beansPropertiesRefBeanIds = new HashMap<String, Map>();
		Element bean = null;
		Map<String,String> propertyRefBeanIds = null;
		for(int i = 0; i < beanElements.size(); i++) {
			bean = beanElements.get(i);
			propertyRefBeanIds = getPropertyRefBeanIds(bean);
			if(null == propertyRefBeanIds || propertyRefBeanIds.size() < 1) {
				continue;
			}
			beansPropertiesRefBeanIds.put(bean.attributeValue("id") + configHashCode, propertyRefBeanIds);
		}
		
		this.setBeanPropertyRefBeanId(beansPropertiesRefBeanIds);
	}

	public Map<String,String> getPropertyRefBeanIds(Element beanElement) {

		Map<String,String> propertyRefBeanId = null;
		List<Element> propertyElements = null;
		propertyElements = beanElement.elements("property");
		if(null == propertyElements) {
			return null;
		}
				
		String propertyName = null;
		String refBeanId = null;
		Element refBean = null;
		try {		
			for(int i = 0; i < propertyElements.size(); i++) {
				refBean = propertyElements.get(i).element("ref");
				if(null == refBean) {
					continue;
				}			
				refBeanId = getPropertyBeanId(refBean);
				if(null == refBeanId) {
					throw new Exception("ref format error !");
				}
				propertyRefBeanId = new HashMap<String, String>();
				propertyName = propertyElements.get(i).attributeValue("name");
				propertyRefBeanId.put(propertyName, refBeanId);
			}
		} catch(Exception e) {
			e.printStackTrace();		
		}
		
		return propertyRefBeanId;
	}
	
	public String getPropertyBeanId(Element refBean) {
		
		String refBeanId = null;
		
		try {

			refBeanId = refBean.attributeValue("local");
			if(null != refBeanId) {
				return refBeanId;
			}
			refBeanId = refBean.attributeValue("bean");
			if(null != refBeanId) {
				return refBeanId;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return refBeanId;
	}
	
	@Override
	public void loadConfig(String xmlPath) {
		setDoc(xmlPath);
		setBeans();
		setBeanElements();
		loadBeansInfo(xmlPath);		
	}


}
