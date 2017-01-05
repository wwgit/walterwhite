package handy.tools.interfaces.bean;

import java.util.List;
import java.util.Map;

public interface IBeanDataMap {
	
	
	public Object getBean(String beanId);
	public Object getBean(String beanId, String filePath);	
	
	public void setBeanObjects(String filePath);
	public void setBeanPropertyClazz();
	
	public void setBeanPropertyValues(Map<String, Map<String, Object>> beanPropertyValues);
	
	public void setBeansClazz(Map<String, Class<?>> theBeansClazz);
	
	public void setBeanPropertyRefBeanId(Map<String, Map<String, String>> beanPropertyRefBeanId);
	
	public void setCurrFileBeanIds(List<String> currFileBeanIds);
	
	public void setCurrentFilePath(String currentFilePath);
	
	public void setDefaultUniqueCode(String filePath);
	

}
