package handy.tools.parser;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import handy.tools.helpers.PathHelper;
import handy.tools.interfaces.ConfigureParser;

public class PropertiesConfigParser extends ConfigureParser {

	private Properties prop;
	
	public PropertiesConfigParser(String propPath) {
		this.setProp(propPath);
	}
	

	@Override
	public Map<String, Class<?>> getBeanClazzes() {
	
		return null;
	}

	@Override
	public Map<String, Map> BeansPropertiesValues() {

		return null;
	}

	@Override
	public Map<String, Map> BeansPropertiesRefBeanIds() {

		return null;
	}

	public Properties getProp() {
		return prop;
	}

	public void setProp(Properties prop) {
		this.prop = prop;
	}
	
	public void setProp(String propPath) {
		
		if(null != this.getProp()) {
			return;
		}
		Properties prop = new Properties();
		try {
			prop.load(PathHelper.resolveAbsoluteStream(propPath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	

}
