package handy.tools.parser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;

import handy.tools.helpers.XmlHelper;
import handy.tools.interfaces.ConfigureParser;
import handy.tools.interfaces.templates.XmlConfigTemplate;


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
public class XmlConfigureParser extends ConfigureParser implements XmlConfigTemplate {
	
	private XmlParser xmlParser;

	
	public XmlConfigureParser(String xmlPath) {
		loadConfig(xmlPath);
	}
	
		
	@Override
	public void setBeansClazz(String configHashCode) {
						
		this.setBeansClazz(this.getXmlParser().setBeansClazzWithAttri(configHashCode));
	}
	
	@Override
	public void BeansPropertiesValues(String configHashCode) {
		
		this.setBeanPropertyValues(this.getXmlParser().BeansPropertiesValues(configHashCode));
	}	
	
	@Override
	public void BeansPropertiesRefBeanIds(String configHashCode) {
		
		this.setBeanPropertyRefBeanId(this.getXmlParser().BeansPropertiesRefBeanIds(configHashCode));
	}

	
	@Override
	public void loadConfig(String xmlPath) {
		this.setXmlParser(new XmlParser(xmlPath));
		this.loadTemplate();
		this.getXmlParser().setBeans();
		this.getXmlParser().setBeanElements();
		loadBeansInfo(xmlPath);		
	}


	public XmlParser getXmlParser() {
		return xmlParser;
	}


	public void setXmlParser(XmlParser xmlParser) {
		this.xmlParser = xmlParser;
	}


	@Override
	public void loadTemplate() {
		
		//load bean related template
		this.getXmlParser().setXmlBeansTab(XML_BEANS_TAB);
		this.getXmlParser().setXmlBeanTab(XML_BEAN_TAB);		
		this.getXmlParser().setXmlAttriBeanIdTab(XML_ATTRI_BEAN_ID_TAB);
		this.getXmlParser().setXmlAttriBeanClazzTab(XML_ATTRI_BEAN_CLAZZ_TAB);
		
		//load property related template
		this.getXmlParser().setXmlPropertyTab(XML_PROPERTY_TAB);
		this.getXmlParser().setXmlAttriPropertyNameTab(XML_ATTRI_PROPERTY_NAME_TAB);
		this.getXmlParser().setXmlValueTab(XML_VALUE_TAB);
		this.getXmlParser().setXmlRefBeanTab(XML_REF_BEAN_TAB);
		this.getXmlParser().setXmlAttriRefBeanIdPropTab(XML_ATTRI_REF_BEAN_ID_PROP_TAB);
		
	}


}
