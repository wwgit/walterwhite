package handy.tools.interfaces.bean;

import java.util.List;
import java.util.Map;


/** 
* @ClassName: IBeanInfoMapParser 
* @Description: TODO(what to do) 
* @author walterwhite
* @date 2017年1月13日 下午2:17:39 
*  
*/
public interface IBeanInfoMapParser {
	
	public Map<String, Class<?>> setBeansClazz(String uniqCode);
	public Map<String, Map<String,Object>> BeansPropertiesValues(String uniqCode);
	public Map<String,Map<String, String>> BeansPropertiesRefBeanIds(String uniqCode);
	public List<String> setCurrFileBeanIds(String uniqCode);
	public void reloadParser(String filePath);

}
