package handy.tools.parser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;

import handy.tools.helpers.XmlHelper;
import handy.tools.interfaces.Parser;
import handy.tools.interfaces.bean.Bean;

public class XmlBeanParser extends XmlParser implements Parser, Bean {
	
	private Document doc;
	private Element beans;
	private List<Element> beanElements;
	
	/*  following variables are for holding an xml bean template  
	 *  bean template is to guide Parser how to understand an xml bean file of config.
	 * 
	 * */
	
	/*<beans />
	 * e.g: <beans></beans> beans is xmlBeansTab
	 * desc: xml tab of beans in which bean classes are described within the xml tab.
	 * */	
	private String xmlBeansTab;
	
	
	/*<bean />
	 * e.g: <bean></bean> bean is xmlBeanTab
	 * desc: xml tab of one bean in which one bean class is described within the xml tab.
	 * */
	private String xmlBeanTab;
	
	/*<bean xmlAttriBeanIdTab=beanId />
	 * e.g: <bean id=beanId/> id is xmlAttriBeanIdTab 
	 * desc: xml attribute tab of unique bean id of one bean class
	 * */
	private String xmlAttriBeanIdTab;
	
	/*<bean attriBeanClazzTab=beanClazzName /> 
	 * desc: xml attribute tab bean class of one bean class
	 * e.g: <bean class=bean.class.name/> class is attriBeanClazzTab
	 * */
	private String xmlAttriBeanClazzTab;	
	
	/*<property />
	 * e.g: property is xmlPropertyTab - element of parentNode
	 * 
	 * desc:property xml tab/xml attribute tab in which properties of a bean class are described
	 * or
	 * 
	 * <parentNode property="property of parent node"/>
	 * e.g: property is xmlPropertyTab: attribute of parentNode
	 * */
	private String xmlPropertyTab;
	
	/*<value />
	 * e.g: value is xmlValueTab - element of parentNode
	 * 
	 * desc:value xml tab/xml attribute tab in which value of a property of one bean class is described
	 * or
	 * <property value="property value" />
	 * e.g: value is xmlValueTab: attribute of property element
	 * */
	private String xmlValueTab;
	
	/*<property name="url"/>
	 * e.g: name is xmlAttriPropertyNameTab - property name = property name of a bean class
	 * desc: xml attribute tab of property name for one property of a bean class
	 * */
	private String xmlAttriPropertyNameTab;
	
	/*<ref />
	 * e.g: ref is xmlRefBeanTab - element of parentNode
	 * desc:xml tab/xml attribute tab of a reference bean as a value of one property of a bean class
	 * 		in some case, property of a bean is an object of another bean.
	 *      this can only be under property xml tab as an value of property 
	 * or
	 * <property ref="beanId of reference bean" />
	 * e.g: value is xmlValueTab: attribute of property element
	 * */
	private String xmlRefBeanTab;
	

	/*<ref local="beanId" />
	 * e.g: local is refBeanIdPropTab - attribute of ref element
	 * desc:xml attribute tab that represents reference bean location, it's value should be bean id. 
	 * */
	private String xmlAttriRefBeanIdPropTab;
	
	public XmlBeanParser(String xmlPath) {
		this.setDoc(xmlPath);
	}

	public Document getDoc() {
		return doc;
	}
	public void setDoc(Document doc) {
		this.doc = doc;
	}		
	public void setDoc(String xmlPath) {
		this.doc = XmlHelper.readXmlFrmFile(xmlPath);
	}
	public Element getBeans() {
		return beans;
	}
	
	public void setBeans() {
		
		Element theBeans = null;		
		theBeans = XmlHelper.findElement(getDoc(), this.getXmlBeansTab());
		if(null == theBeans) {
			throw new NullPointerException("beans element cannot be found !");
		}	
		this.beans = theBeans;
	}

	public List<Element> getBeanElements() {
		return beanElements;
	}
	
	public void setBeanElements() {
		List<Element> myBeanElements = getBeans().elements(this.getXmlBeanTab());
		if(null == myBeanElements || myBeanElements.size() <1) {
			throw new NullPointerException("there is no bean element under beans element !");
		}
		this.beanElements = myBeanElements;
	}
	
	public String createUniqBeanIdWithAttri(Element bean, String uniqueStr) {
		String uniqBeanId = bean.attributeValue(this.getXmlAttriBeanIdTab()) + uniqueStr;
		return uniqBeanId;
	}
	
	public Map<String, Class<?>> setBeansClazzWithAttri(String uniqueStr) {
		
		Map<String, Class<?>> beansClazzes = null;
		
		try {
			beansClazzes = new HashMap<String, Class<?>>();
			List<Element> myBeanElements = this.getBeanElements();
			for(int i = 0; i < myBeanElements.size(); i++) {
				Element e = myBeanElements.get(i);
				beansClazzes.put(createUniqBeanIdWithAttri(e,uniqueStr),
								XmlHelper.getRequireClass(e.attributeValue(
														  this.getXmlAttriBeanClazzTab())));
			}
		} catch(Exception e) {
			e.printStackTrace();
		}				
		return beansClazzes;
	}
	
	
	public Map<String, Map> BeansPropertiesValues(String uniqueStr) {
		
		List<Element> beanElements = this.getBeanElements();
		Map<String, Map> beansProperties = new HashMap<String, Map>();
		for(int i = 0 ; i < beanElements.size(); i++) {
			Element bean = beanElements.get(i);
			beansProperties.put(createUniqBeanIdWithAttri(bean, uniqueStr), getPropertyValues(bean));
		}
		return beansProperties;
	}
	
	
	public Map<String,Object> getPropertyValues(Element beanElement) {

		Map<String,Object> propertyValues = null;
		List<Element> propertyElements = null;
		propertyElements = beanElement.elements(this.getXmlPropertyTab());
		if(null == propertyElements || propertyElements.size() <1) {
			return null;
		}
		
		propertyValues = new HashMap<String, Object>();
		String propertyName = null;
		Object value = null;
		try {
			for(int i = 0; i < propertyElements.size(); i++) {
				propertyName = propertyElements.get(i).attributeValue(this.getXmlAttriPropertyNameTab());
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
		value = property.attributeValue(this.getXmlValueTab());
		
		if(null != value) {
			return value;
		}
		
		try {
			Element ele = property.element(this.getXmlValueTab());
			if(null != ele) {
				value = ele.getText();
			}
			
			if(null != value) {
				return value;
			}
			
			value = markRefBeanObject(property);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
			
		return value;
	}
	
	
	public boolean hasRefBean(Element property) {
		Object obj = null;
		try {
			obj = property.attribute(this.getXmlRefBeanTab());
			if(null != obj) {
				return true;
			}
			obj = property.element(this.getXmlRefBeanTab());
			if(null != obj) {
				return true;
			}
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		return false; 
	}
	
	public Object returnRefMark() {
		
		if(this.getXmlAttriRefBeanIdPropTab().equals(REF_LOCAL_BEAN)) {
			return REF_LOCAL_NOT_INIT;
		} else if(this.getXmlAttriRefBeanIdPropTab().equals(REF_BEAN_BEAN)) {
			return REF_BEAN_NOT_INIT;
		} else {
			return null;
		}
	}
	
	public Object markRefBeanObject(Element property) {
		
		Object result = null;

		try {
			if(hasRefBean(property)) {
				result = returnRefMark();
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Map<String, Map> BeansPropertiesRefBeanIds(String uniqueStr) {
		
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
			beansPropertiesRefBeanIds.put(createUniqBeanIdWithAttri(bean, uniqueStr), propertyRefBeanIds);
		}
		
		return beansPropertiesRefBeanIds;
	}

	public Map<String,String> getPropertyRefBeanIds(Element beanElement) {

		Map<String,String> propertyRefBeanId = null;
		List<Element> propertyElements = null;
		propertyElements = beanElement.elements(this.getXmlPropertyTab());
		if(null == propertyElements) {
			return null;
		}
				
		String propertyName = null;
		String refBeanId = null;
		Element refBean = null;
		try {		
			for(int i = 0; i < propertyElements.size(); i++) {
				refBean = propertyElements.get(i).element(this.getXmlRefBeanTab());
				if(null == refBean) {
					continue;
				}			
				refBeanId = getPropertyBeanId(refBean);
				if(null == refBeanId) {
					throw new Exception("ref format error !");
				}
				propertyRefBeanId = new HashMap<String, String>();
				propertyName = propertyElements.get(i).attributeValue(this.getXmlAttriPropertyNameTab());
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
			refBeanId = refBean.attributeValue(this.getXmlAttriRefBeanIdPropTab());			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return refBeanId;
	}
	
	
	public String getXmlAttriBeanIdTab() {
		return xmlAttriBeanIdTab;
	}
	public void setXmlAttriBeanIdTab(String xmlAttriBeanIdTab) {
		this.xmlAttriBeanIdTab = xmlAttriBeanIdTab;
	}
	public String getXmlAttriBeanClazzTab() {
		return xmlAttriBeanClazzTab;
	}
	public void setXmlAttriBeanClazzTab(String xmlAttriBeanClazzTab) {
		this.xmlAttriBeanClazzTab = xmlAttriBeanClazzTab;
	}
	public String getXmlPropertyTab() {
		return xmlPropertyTab;
	}
	public void setXmlPropertyTab(String xmlPropertyTab) {
		this.xmlPropertyTab = xmlPropertyTab;
	}
	public String getXmlValueTab() {
		return xmlValueTab;
	}
	public void setXmlValueTab(String xmlValueTab) {
		this.xmlValueTab = xmlValueTab;
	}
	public String getXmlAttriPropertyNameTab() {
		return xmlAttriPropertyNameTab;
	}
	public void setXmlAttriPropertyNameTab(String xmlAttriPropertyNameTab) {
		this.xmlAttriPropertyNameTab = xmlAttriPropertyNameTab;
	}
	public String getXmlRefBeanTab() {
		return xmlRefBeanTab;
	}
	public void setXmlRefBeanTab(String xmlRefBeanTab) {
		this.xmlRefBeanTab = xmlRefBeanTab;
	}
	public String getXmlAttriRefBeanIdPropTab() {
		return xmlAttriRefBeanIdPropTab;
	}
	public void setXmlAttriRefBeanIdPropTab(String xmlAttriRefBeanIdPropTab) {
		this.xmlAttriRefBeanIdPropTab = xmlAttriRefBeanIdPropTab;
	}
	public String getXmlBeansTab() {
		return xmlBeansTab;
	}
	public void setXmlBeansTab(String xmlBeansTab) {
		this.xmlBeansTab = xmlBeansTab;
	}
	public String getXmlBeanTab() {
		return xmlBeanTab;
	}
	public void setXmlBeanTab(String xmlBeanTab) {
		this.xmlBeanTab = xmlBeanTab;
	}
	

}
