package handy.tools.interfaces.templates;


/** 
* @ClassName: IPropBeanTemplate 
* @Description: TODO
*  * beanId_realBeanId=beanClazzName
 *   realBeanId.beanClazzName.propertyName=value
 *   realBeanId must be unique
 *   
 *   beanId_user1=Test.User
 *   user1.Test.User.name=example
* 
* @author walterwhite
* @date 2017年1月9日 下午2:30:58 
*  
*/
public interface IPropBeanTemplate {
	
	/** 
	* @Fields BEAN_ID_TAB :e.g beanId_TestBean=My.Test.Bean.Class 
	*/ 
	public static final String BEAN_ID_TAB = "beanId_";
	
	/** 
	* @Fields CLAZ_HEADER_SEPERATOR :e.g  TestBean_My.Test.Bean.Class; if seperator is _ 
	*/ 
	public static final String CLAZ_HEADER_SEPERATOR = ".";
	
	/** 
	* @Fields CLAZ_PROP_SEPERATOR : 
	* e.g My.Test.Bean.Class.propName=propValue; if seperator is . 
	*/ 
	public static final String CLAZ_PROP_SEPERATOR = ".";

}
