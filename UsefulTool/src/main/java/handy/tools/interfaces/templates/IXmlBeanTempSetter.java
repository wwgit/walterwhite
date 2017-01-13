package handy.tools.interfaces.templates;



/** 
* @ClassName: IXmlBeanTempSetter 
* @Description: TODO(what to do) 
* @author walterwhite
* @date 2017年1月13日 下午2:11:27 
*  
*/
public interface IXmlBeanTempSetter {
	
	public void setXmlBeansTab(String XML_BEANS_TAB);
	public void setXmlBeanTab(String XML_BEAN_TAB);		
	public void setXmlAttriBeanIdTab(String XML_ATTRI_BEAN_ID_TAB);
	public void setXmlAttriBeanClazzTab(String XML_ATTRI_BEAN_CLAZZ_TAB);
	
	//load property related template
	public void setXmlPropertyTab(String XML_PROPERTY_TAB);
	public void setXmlAttriPropertyNameTab(String XML_ATTRI_PROPERTY_NAME_TAB);
	public void setXmlValueTab(String XML_VALUE_TAB);
	public void setXmlRefBeanTab(String XML_REF_BEAN_TAB);
	public void setXmlAttriRefBeanIdPropTab(String XML_ATTRI_REF_BEAN_ID_PROP_TAB);

}
