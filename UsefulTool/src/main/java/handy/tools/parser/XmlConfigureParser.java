package handy.tools.parser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;

import handy.tools.helpers.XmlHelper;
import handy.tools.interfaces.bean.BeanParser;
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
public class XmlConfigureParser extends BeanParser implements XmlConfigTemplate {
	
	private XmlBeanParser parser;
	
	public XmlConfigureParser(String xmlPath) {
		loadResource(xmlPath);
	}
	
		
	@Override
	public void setBeansClazz(String configHashCode) {
						
		this.setBeansClazz(this.getParser().setBeansClazzWithAttri(configHashCode));
	}
	
	@Override
	public void BeansPropertiesValues(String configHashCode) {
		
		this.setBeanPropertyValues(this.getParser().BeansPropertiesValues(configHashCode));
	}	
	
	@Override
	public void BeansPropertiesRefBeanIds(String configHashCode) {
		
		this.setBeanPropertyRefBeanId(this.getParser().BeansPropertiesRefBeanIds(configHashCode));
	}

	
	@Override
	public void loadResource(String xmlPath) {
		this.setParser(new XmlBeanParser(xmlPath));
		this.loadTemplate();
		this.getParser().setBeans();
		this.getParser().setBeanElements();
		loadBeansInfo(xmlPath);		
	}


	public XmlBeanParser getParser() {
		return parser;
	}


	public void setParser(XmlBeanParser parser) {
		this.parser = parser;
	}


	@Override
	public void loadTemplate() {
		
		//load bean related template
		this.getParser().setXmlBeansTab(XML_BEANS_TAB);
		this.getParser().setXmlBeanTab(XML_BEAN_TAB);		
		this.getParser().setXmlAttriBeanIdTab(XML_ATTRI_BEAN_ID_TAB);
		this.getParser().setXmlAttriBeanClazzTab(XML_ATTRI_BEAN_CLAZZ_TAB);
		
		//load property related template
		this.getParser().setXmlPropertyTab(XML_PROPERTY_TAB);
		this.getParser().setXmlAttriPropertyNameTab(XML_ATTRI_PROPERTY_NAME_TAB);
		this.getParser().setXmlValueTab(XML_VALUE_TAB);
		this.getParser().setXmlRefBeanTab(XML_REF_BEAN_TAB);
		this.getParser().setXmlAttriRefBeanIdPropTab(XML_ATTRI_REF_BEAN_ID_PROP_TAB);
		
	}


}
