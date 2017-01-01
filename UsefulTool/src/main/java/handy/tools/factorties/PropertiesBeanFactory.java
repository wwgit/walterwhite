package handy.tools.factorties;

import handy.tools.interfaces.bean.BeanFactory;
import handy.tools.parser.PropertiesConfigParser;

public class PropertiesBeanFactory extends BeanFactory {
	
	public PropertiesBeanFactory(String propPath) {
		loadBeans(propPath);
	}
	
	public PropertiesBeanFactory() {
		
	}
	
	@Override
	protected void initParser(String propPath) {
		if(null == this.getParser()) {
			this.setParser(new PropertiesConfigParser(propPath));	
		} else {
			this.getParser().loadResource(propPath);
		}
	}

}
