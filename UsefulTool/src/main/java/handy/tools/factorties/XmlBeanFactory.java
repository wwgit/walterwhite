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
	
	public Map<String, Class<?>> setBeansClazzFrmXml(String xmlPath) {
		
		Map<String, Class<?>> beansClazz = null;	
		try {
			beansClazz = parser.getBeanClazzes();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return beansClazz;
	}

	@Override
	public void loadBeans(String xmlPath) {
		
		
	}

	@Override
	public void lazyLoadBeans(String xmlPath) {
		
		
	}

	@Override
	public Map<String, Map> BeansPropertiesTypes() {
		
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
	
	
	
}
