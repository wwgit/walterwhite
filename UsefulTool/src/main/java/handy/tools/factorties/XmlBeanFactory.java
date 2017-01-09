package handy.tools.factorties;

import handy.tools.bean.XmlBeanMapFacadeImpl;
import handy.tools.interfaces.bean.BeanFactory;

/** 
* @ClassName: XmlBeanFactory 
* @Description: TODO(what to do) 
*  * <beans>
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

* @author walterwhite
* @date 2017年1月9日 下午2:25:58 
*  
*/
public class XmlBeanFactory extends BeanFactory {

	
	public XmlBeanFactory(String xmlPath) {		
		this.setBeanFacade(new XmlBeanMapFacadeImpl(xmlPath));
	}
	
	public XmlBeanFactory() {
		this.setBeanFacade(new XmlBeanMapFacadeImpl());
	}
	
	public XmlBeanFactory(String ...xmlPaths) {
		this.setBeanFacade(new XmlBeanMapFacadeImpl(xmlPaths));
	}
	
	
}
