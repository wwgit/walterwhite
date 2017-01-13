package handy.tools.interfaces.bean;

import java.util.List;
import java.util.Map;


/** 
* @ClassName: IBeanDataMap 
* @Description: TODO(what to do) 
* @author walterwhite
* @date 2017年1月13日 下午2:12:35 
*  
*/
public interface IBeanDataMap {
	
	
	/** 
	* @Title: getBean 
	* @Description: TODO(what to do) 
	* @param @param beanId
	* @param @return  
	* @return Object   
	* @throws 
	*/
	public Object getBean(String beanId);

	
	/** 
	* @Title: getBean 
	* @Description: TODO(what to do) 
	* @param @param beanId
	* @param @param filePath
	* @param @return  
	* @return Object   
	* @throws 
	*/
	public Object getBean(String beanId, String filePath);
	
	
	/** 
	* @Title: getCurrentFilePath 
	* @Description: TODO(what to do) 
	* @param @return  
	* @return String   
	* @throws 
	*/
	public String getCurrentFilePath();
	
	public void setBeanObjects();
	public void setBeanPropertyClazz();
	
	public void setBeanPropertyValues(Map<String, Map<String, Object>> beanPropertyValues);
	
	public void setBeansClazz(Map<String, Class<?>> theBeansClazz);
	
	public void setBeanPropertyRefBeanId(Map<String, Map<String, String>> beanPropertyRefBeanId);
	
	public void setCurrFileBeanIds(List<String> currFileBeanIds);
	
	public void setCurrentFilePath(String currentFilePath);
	
	public void setDefaultUniqueCode();
	

}
