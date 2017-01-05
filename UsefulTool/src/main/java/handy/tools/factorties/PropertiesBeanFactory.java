package handy.tools.factorties;

import handy.tools.bean.PropBeanMapFacadeImpl;
import handy.tools.interfaces.bean.BeanFactory;


public class PropertiesBeanFactory extends BeanFactory {
	
	public PropertiesBeanFactory(String propPath) {
		this.setBeanFacade(new PropBeanMapFacadeImpl(propPath));
	}
	
	public PropertiesBeanFactory() {
		this.setBeanFacade(new PropBeanMapFacadeImpl());
	}
	
	public PropertiesBeanFactory(String ...propPaths) {
		this.setBeanFacade(new PropBeanMapFacadeImpl(propPaths));
	}

}
