package handy.tools.factorties;

import handy.tools.bean.XmlBeanMapFacadeImpl;
import handy.tools.interfaces.bean.BeanFactory;


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
public class XmlBeanFactory extends BeanFactory {

	
	public XmlBeanFactory(String xmlPath) {		
		this.setBeanFacade(new XmlBeanMapFacadeImpl(xmlPath));
	}
	
	public XmlBeanFactory() {
		this.setBeanFacade(new XmlBeanMapFacadeImpl());
	}		
	
}
