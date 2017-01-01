package handy.tools.factorties;

import handy.tools.helpers.PathHelper;
import handy.tools.interfaces.bean.BeanFactory;
import handy.tools.parser.XmlConfigureParser;


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
		this.loadBeans(xmlPath);
	}
	
	public XmlBeanFactory() {
		
	}

	@Override
	protected void initParser(String xmlPath) {

		if(null == this.getParser()) {
			this.setParser(new XmlConfigureParser(xmlPath));	
		} else {
			this.getParser().loadResource(xmlPath);
		}
	}
		
	
}
