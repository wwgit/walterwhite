package handy.tools.factorties;

import handy.tools.helpers.XmlHelper;

import java.util.HashMap;
import java.util.Map;

public class BeanFactory {
	
	//beanInfo: Map<beanId, bean Class> - beanId should be unique
	private Map<String, Class<?>> beansClazz;
	
	//beanPropertyValues: Map<beanId, propertyInfo> -  beanId should be unique
	//propertyInfo: Map<attributeName, attributeValue> - attribute name should be unique
	//For the same object, property name(alias as attributeName) must be unique 
	private Map<String, Map> beanPropertyValues;
	
	//beanPropertyClazz: Map<beanId, propertyClazz> -  beanId should be unique
	//propertyClazz: Map<attributeName, attributeTypeClazz> - attribute name should be unique
	//For the same object, property name(alias as attributeName) must be unique 
	private Map<String, Map> beanPropertyClazz;
	
	
	public Map<String, Class<?>> setBeansInfoFrmXml(String xmlPath) {
		
		Map<String, Class<?>> beansClazz = null;	
		try {
			beansClazz = XmlHelper.getBeansInfo(xmlPath);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return beansClazz;
	}
	

}
