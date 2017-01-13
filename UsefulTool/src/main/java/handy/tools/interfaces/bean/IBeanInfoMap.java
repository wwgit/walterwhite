package handy.tools.interfaces.bean;

import java.util.List;
import java.util.Map;


/** 
* @ClassName: IBeanInfoMap 
* @Description: TODO(what to do) 
* @author walterwhite
* @date 2017年1月13日 下午2:12:44 
*  
*/
public interface IBeanInfoMap {
	
	public void setBeanPropertyValues(Map<String, Map<String, Object>> beanPropertyValues);
	
	public void setBeansClazz(Map<String, Class<?>> theBeansClazz);
	
	public void setBeanPropertyRefBeanId(Map<String, Map<String, String>> beanPropertyRefBeanId);
	
	public void setCurrFileBeanIds(List<String> currFileBeanIds);
	
	public void setBeanPropertyClazz();
	
	public List<String> getCurrFileBeanIds();
	public Map<String,Map<String, String>> getBeanPropertyRefBeanId();
	Map<String, Class<?>> getBeansClazz();
	Map<String, Map<String, Object>> getBeanPropertyValues();
	Map<String, Map<String,Class<?>>> getBeanPropertyClazz();
	

}
