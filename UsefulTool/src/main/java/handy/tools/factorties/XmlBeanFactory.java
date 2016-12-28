package handy.tools.factorties;

import handy.tools.helpers.ReflectHelper;
import handy.tools.helpers.XmlHelper;
import handy.tools.interfaces.BeanFactory;
import handy.tools.interfaces.ConfigureParser;
import handy.tools.parser.XmlConfigureParser;

import java.util.Iterator;
import java.util.Map;

public class XmlBeanFactory extends BeanFactory {

	private ConfigureParser parser;
	
	public XmlBeanFactory(String xmlPath) {		
		parser = new XmlConfigureParser(xmlPath);
	}

	protected Map<String, Map> BeansPropertiesTypes() {
		
		Map<String, Map> beanPropTypes = null;
		Map<String, Class<?>> beanClazzes = getBeansClazz();
		
		if(null == beanClazzes || beanClazzes.size() < 1) {
			return null;
		}
			
		for(Iterator key_it = beanClazzes.keySet().iterator(); key_it.hasNext();) {
			String key = (String) key_it.next();
			Map<String, Class<?>> propertyTypes = ReflectHelper.
												  retrieveBeanPropertyTypes(beanClazzes.get(key));
			beanPropTypes.put(key, propertyTypes);
		}
		
		return beanPropTypes;
	}

	@Override
	protected void setBeanPropertyClazz() {
		setBeanPropertyClazz(BeansPropertiesTypes());
	}

	@Override
	protected void setBeanPropertyValues() {
		this.setBeanPropertyValues(this.getParser().BeansPropertiesValues());
	}

	@Override
	protected void setBeanObjects() {
		
	}

	@Override
	protected void setBeansClazz() {
		setBeansClazz(this.getParser().getBeanClazzes());
	}

	@Override
	protected ConfigureParser getParser() {
		return this.parser;
	}

	@Override
	protected void setParser(String xmlPath) {
		this.parser = new XmlConfigureParser(xmlPath);
	}
	
	
	
}
