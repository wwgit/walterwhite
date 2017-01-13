package handy.tools.interfaces.templates;

/** 
* @ClassName: XmlBeanTemplate 
* @Description: TODO(provides xml bean template for initializing ) 
* @author walterwhite
* @date 2017年1月13日 下午2:18:14 
*  
*/


public interface XmlBeanTemplate {
	
	/** 
	* @Fields XML_BEANS_TAB : <beans><bean /></beans>  
	*/ 
	public static final String XML_BEANS_TAB = "beans";
	

	/** 
	* @Fields XML_BEAN_TAB : <beans><bean /></beans> 
	*/ 
	public static final String XML_BEAN_TAB = "bean";
	
	/** 
	* @Fields XML_ATTRI_BEAN_ID_TAB : <bean id="" /> 
	*/ 
	public static final String XML_ATTRI_BEAN_ID_TAB = "id";
	
	/** 
	* @Fields XML_ATTRI_BEAN_CLAZZ_TAB : <bean class="" /> 
	*/ 
	public static final String XML_ATTRI_BEAN_CLAZZ_TAB = "class";
	
	/** 
	* @Fields XML_PROPERTY_TAB : <property /> 
	*/ 
	public static final String XML_PROPERTY_TAB = "property";
	

	/** 
	* @Fields XML_VALUE_TAB : <property><value></value></property> 
	*   					  <property value = ""/>
	*/ 
	public static final String XML_VALUE_TAB = "value";
	
	/** 
	* @Fields XML_ATTRI_PROPERTY_NAME_TAB :  <property name="" />
	*/ 
	public static final String XML_ATTRI_PROPERTY_NAME_TAB = "name";
	
	/** 
	* @Fields XML_REF_BEAN_TAB : <property><ref></ref></property> 
	*/ 
	public static final String XML_REF_BEAN_TAB = "ref";

	/** 
	* @Fields XML_ATTRI_REF_BEAN_ID_PROP_TAB : 
	* <property><ref local="beanId"></ref></property> 
	*/ 
	public static final String XML_ATTRI_REF_BEAN_ID_PROP_TAB = "local";
	
}
