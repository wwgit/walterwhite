package handy.tools.parser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;

import handy.tools.helpers.XmlHelper;
import handy.tools.interfaces.ConfigureParser;

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
		if(null == myBeanElements) {
			throw new NullPointerException("there is no bean element under beans element !");
		}
		this.beanElements = myBeanElements;
	}

	
	public XmlConfigureParser(String xmlPath) {
		setDoc(xmlPath);
		this.setBeans();
		this.setBeanElements();
	}
		
	@Override
	public Map<String, Class<?>> getBeanClazzes() {
		
		Map<String, Class<?>> beansClazzes = null;
		
		try {
			beansClazzes = new HashMap<String, Class<?>>();
			List<Element> myBeanElements = this.getBeanElements();
			for(int i = 0; i < myBeanElements.size(); i++) {
				Element e = myBeanElements.get(i);
				beansClazzes.put(e.attributeValue("id"), XmlHelper.getRequireClass(e.attributeValue("class")));
			}
		} catch(Exception e) {
			e.printStackTrace();
		}		
		
		return beansClazzes;
	}
	
	@Override
	public Map<String, Map> BeansProperties(Map<String, Class<?>> beanClazzes) {
		
		List<Element> beanElements = this.getBeanElements();
		Map<String, Map> beansProperties = new HashMap<String, Map>();
		for(int i = 0 ; i < beanElements.size(); i++) {
			Element bean = beanElements.get(i);
			beansProperties.put(bean.attributeValue("id"), getPropertyValues(bean, beanClazzes));
		}
		
		return beansProperties;
	}
	
	
	public Map<String,Object> getPropertyValues(Element beanElement, Map<String,Class<?>> beanClazzes) {

		Map<String,Object> propertyValues = null;
		List<Element> propertyElements = null;
		propertyElements = beanElement.elements("property");
		if(null == propertyElements) {
			return null;
		}
		
		propertyValues = new HashMap<String, Object>();
		for(int i = 0; i < propertyElements.size(); i++) {
			String propertyName = propertyElements.get(i).attributeValue("name");
			Object value = getPropertyValue(propertyElements.get(i), beanClazzes);
		}
		
		return propertyValues;
	}
	
	public Object getPropertyValue(Element property, Map<String,Class<?>> beanClazzes) {
		
		Object value = null;
		value = property.attributeValue("value");
		
		if(null != value) {
			return value;
		}
		
		try {
			
			value = property.element("value");
			if(null != value) {
				return value;
			}
			
			value = getRefObject(property, beanClazzes);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		
		return value;
	}


	public Object getRefObject(Element property, Map<String,Class<?>> beanClazzes) {
		
		Object value = null;
		
		
		
		return value;
	}



	
	

}
