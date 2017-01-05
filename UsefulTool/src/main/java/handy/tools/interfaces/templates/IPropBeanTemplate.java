package handy.tools.interfaces.templates;


/*e.g
 * beanId_realBeanId=beanClazzName
 * realBeanId.beanClazzName.propertyName=value
 * realBeanId must be unique
 * 
 * beanId_user1=Test.User
 * user1.Test.User.name=example
 * */
public interface IPropBeanTemplate {
	
	//private String beanIdTab;
	//e.g beanId_TestBean=My.Test.Bean.Class
	public static final String BEAN_ID_TAB = "beanId_";
	
	//private String clazHeaderSeperator;
	//e.g TestBean_My.Test.Bean.Class; if seperator is _
	public static final String CLAZ_HEADER_SEPERATOR = ".";
	
	//private String clazPropSeperator;
	//e.g My.Test.Bean.Class.propName=propValue; if seperator is .
	public static final String CLAZ_PROP_SEPERATOR = ".";

}
