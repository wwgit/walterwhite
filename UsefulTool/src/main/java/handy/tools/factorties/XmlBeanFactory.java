package handy.tools.factorties;

import handy.tools.helpers.XmlHelper;
import handy.tools.interfaces.BeanFactory;
import handy.tools.interfaces.ConfigureParser;
import handy.tools.parser.XmlConfigureParser;

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
	
	
	
}
