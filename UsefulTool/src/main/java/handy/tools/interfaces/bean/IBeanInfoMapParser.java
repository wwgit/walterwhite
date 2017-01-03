package handy.tools.interfaces.bean;

import java.util.List;
import java.util.Map;

public interface IBeanInfoMapParser {
	
	public Map<String, Class<?>> setBeansClazz(String uniqCode);
	public Map<String, Map<String,Object>> BeansPropertiesValues(String uniqCode);
	public Map<String,Map<String, String>> BeansPropertiesRefBeanIds(String uniqCode);
	public List<String> setCurrFileBeanIds(String uniqCode);
	public void initParser();

}
