package handy.tools.interfaces.bean;

import handy.tools.constants.Bean;
import handy.tools.constants.DataTypes;
import handy.tools.helpers.PathHelper;
import handy.tools.helpers.ReflectHelper;
import handy.tools.helpers.TypeHelper;

import java.util.HashMap;
import java.util.Map;



/** 
* @ClassName: BeanCommons 
* @Description: TODO(what to do) 
* @author walterwhite
* @date 2017年1月13日 下午2:11:42 
*  
*/
public abstract class BeanCommons extends PathHelper implements Bean {

	
	/** 
	* @Fields currentFilePath : unique code of absolute path of current file 
	*/ 
	private String currentFilePath;
	
	//
	
	/** 
	* @Fields defaultUniqueCode :  default unique code:
	* 							   hash code of config file: 
	* 						       hash code of the first config file loaded
	*/ 
	private String defaultUniqueCode;
	
	public String getCurrentFilePath() {
		return currentFilePath;
	}

	public void setCurrentFilePath(String currentFilePath) {
		this.currentFilePath = resolveAbsolutePath(currentFilePath);
	}

	public String getDefaultUniqueCode() {
		return defaultUniqueCode;
	}

	/** 
	* @Title: setDefaultUniqueCode 
	* @Description: TODO(setting default file unique code) 
	* @param   
	* @return void   
	* @throws 
	*/
	public void setDefaultUniqueCode() {
		String hashCode = loadBeanUniqCode(this.getCurrentFilePath());
		this.defaultUniqueCode = hashCode;
	}
	
	public boolean isRefBean(Object propertyValue) {
		
		int type = TypeHelper.parseType(propertyValue);
		
		if(type == DataTypes.JAVA_LANG_INTEGER || type == DataTypes.JAVA_BASIC_INT ) {
			
			int chk = ((Integer)propertyValue).intValue();				
			
			if(REF_LOCAL_NOT_INIT == chk || REF_BEAN_NOT_INIT == chk) {
				return true;			
			}					
		}
		
		return false;
	}
	
	protected void initBeanProperty(Object beanObj, String propertyName, 
			Class<?> propertyClazz, Object org_value) {
		Object value = org_value;
		System.out.println(propertyClazz.getName());
		if(false == (value.getClass() == propertyClazz)) {
				value = TypeHelper.getRequiredValue(org_value, propertyClazz.getName());
		}
		Map<Object, Class<?>> value_type = new HashMap<Object, Class<?>>();
		value_type.put(value, propertyClazz);
		ReflectHelper.callSetter(beanObj, propertyName, value_type);

	}
	
	
	/** 
	* @Title: loadBeanUniqCode 
	* @Description: TODO(get uniq code for bean using bean file ) 
	* @param @param beanFilePath - absolute path
	* @param @return  
	* @return String   
	* @throws 
	*/
	public String loadBeanUniqCode(String beanFilePath) {
	    String hashCode = String.valueOf(beanFilePath.hashCode()); 
		return hashCode;
	}
	
	
}
