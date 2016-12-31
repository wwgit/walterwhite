package handy.tools.interfaces.templates;

public interface XmlConfigTemplate {
	
	//xmlBeansTab <beans><bean /></beans>
	public static final String XML_BEANS_TAB = "beans";
	
	//xmlBeanTab <beans><bean /></beans>
	public static final String XML_BEAN_TAB = "bean";
	
	//xmlAttriBeanIdTab; <bean id="" />
	public static final String XML_ATTRI_BEAN_ID_TAB = "id";
	
	//xmlAttriBeanClazzTab <bean class="" />
	public static final String XML_ATTRI_BEAN_CLAZZ_TAB = "class";
	
	//xmlPropertyTab <property />
	public static final String XML_PROPERTY_TAB = "property";
	
	//xmlValueTab <property><value></value></property> 
	//			  <property value = ""/>
	public static final String XML_VALUE_TAB = "value";
	
	//xmlAttriPropertyNameTab <property name="" />
	public static final String XML_ATTRI_PROPERTY_NAME_TAB = "name";
	
	//xmlRefBeanTab <property><ref></ref></property>
	public static final String XML_REF_BEAN_TAB = "ref";
	
	//xmlAttriRefBeanIdPropTab <property><ref local="beanId"></ref></property>
	public static final String XML_ATTRI_REF_BEAN_ID_PROP_TAB = "local";
	
}
