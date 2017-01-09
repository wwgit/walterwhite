package handy.tools.bean;

import handy.tools.interfaces.bean.BeanMapFacade;
import handy.tools.interfaces.templates.IXmlBeanTempSetter;
import handy.tools.interfaces.templates.XmlBeanTemplate;
import handy.tools.parser.XmlBeanMapParser;

public class XmlBeanMapFacadeImpl extends BeanMapFacade implements XmlBeanTemplate {

	@Override
	protected void initBeanParser(String filePath) {
		if(null == this.getBeanParser()) {
			setBeanParser(new XmlBeanMapParser());
		} 
	}
	
	@Override
	protected void initBeanParser() {	
		if(null == this.getBeanParser()) {
			setBeanParser(new XmlBeanMapParser());
		} 
	}
	
	public XmlBeanMapFacadeImpl(String filePath) {
		setBeanData(new BeanDataMapImpl(filePath));
		loadBeans(filePath);
	}
	
	public XmlBeanMapFacadeImpl(String ...filePaths) {
		if(null == getBeanData()) {
			setBeanData(new BeanDataMapImpl());
		}
		for(int i = 0; i < filePaths.length; i++) {
			loadBeans(filePaths[i]);
		}
	}
	
	public XmlBeanMapFacadeImpl() {
		setBeanData(new BeanDataMapImpl());
	}

	@Override
	protected void loadBeanTemplate(String filePath) {
		
		IXmlBeanTempSetter setter = (IXmlBeanTempSetter) this.getBeanParser();
		
		//load bean related template
		 setter.setXmlBeansTab(XML_BEANS_TAB);
		 setter.setXmlBeanTab(XML_BEAN_TAB);		
		 setter.setXmlAttriBeanIdTab(XML_ATTRI_BEAN_ID_TAB);
		 setter.setXmlAttriBeanClazzTab(XML_ATTRI_BEAN_CLAZZ_TAB);
		
		//load property related template
		 setter.setXmlPropertyTab(XML_PROPERTY_TAB);
		 setter.setXmlAttriPropertyNameTab(XML_ATTRI_PROPERTY_NAME_TAB);
		 setter.setXmlValueTab(XML_VALUE_TAB);
		 setter.setXmlRefBeanTab(XML_REF_BEAN_TAB);
		 setter.setXmlAttriRefBeanIdPropTab(XML_ATTRI_REF_BEAN_ID_PROP_TAB);
		 
		 this.getBeanParser().reloadParser(filePath);
		
	}

}
