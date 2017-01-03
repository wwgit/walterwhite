package handy.tools.bean;

import handy.tools.interfaces.IXmlBeanTempSetter;
import handy.tools.interfaces.bean.BeanMapFacade;
import handy.tools.interfaces.templates.XmlBeanTemplate;
import handy.tools.parser.XmlBeanMapParser;

public class XmlBeanMapFacadeImpl extends BeanMapFacade implements XmlBeanTemplate {

	@Override
	protected void initBeanParser(String filePath) {
		this.setBeanParser(new XmlBeanMapParser(filePath));
		
	}
	
	public XmlBeanMapFacadeImpl(String filePath) {
		setBeanData(new BeanDataMapImpl(filePath));
		loadBeans(filePath);
	}
	
	public XmlBeanMapFacadeImpl() {
		setBeanData(new BeanDataMapImpl());
	}
	
	public void loadBeanTemplate() {
		
		//load bean related template
		IXmlBeanTempSetter templateSetter = (IXmlBeanTempSetter) this.getBeanParser();
		templateSetter.setXmlBeansTab(XML_BEANS_TAB);
		templateSetter.setXmlBeanTab(XML_BEAN_TAB);		
		templateSetter.setXmlAttriBeanIdTab(XML_ATTRI_BEAN_ID_TAB);
		templateSetter.setXmlAttriBeanClazzTab(XML_ATTRI_BEAN_CLAZZ_TAB);
		
		//load property related template
		templateSetter.setXmlPropertyTab(XML_PROPERTY_TAB);
		templateSetter.setXmlAttriPropertyNameTab(XML_ATTRI_PROPERTY_NAME_TAB);
		templateSetter.setXmlValueTab(XML_VALUE_TAB);
		templateSetter.setXmlRefBeanTab(XML_REF_BEAN_TAB);
		templateSetter.setXmlAttriRefBeanIdPropTab(XML_ATTRI_REF_BEAN_ID_PROP_TAB);
		
		//can only load element beans and elements of bean after template is loaded
		this.getBeanParser().initParser();
	}

}
