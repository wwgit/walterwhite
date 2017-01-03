package handy.tools.interfaces.bean;

import handy.tools.constants.Bean;
import handy.tools.constants.DataTypes;
import handy.tools.helpers.PathHelper;
import handy.tools.helpers.ReflectHelper;
import handy.tools.helpers.TypeHelper;

import java.util.HashMap;
import java.util.Map;

public abstract class BeanCommons implements Bean {

	
	//unique code of absolute path of current file
	private String currentFilePath;
	
	//default unique code: hash code of config file: hash code of the first config file loaded
	private String defaultUniqueCode;
	
	public String getCurrentFilePath() {
		return currentFilePath;
	}

	public void setCurrentFilePath(String currentFilePath) {
		this.currentFilePath = PathHelper.resolveAbsolutePath(currentFilePath);
	}

	public String getDefaultUniqueCode() {
		return defaultUniqueCode;
	}

	public void setDefaultUniqueCode(String filePath) {
		String hashCode = null;
		if(null == this.getDefaultUniqueCode()) {
			hashCode = loadBeanUniqCode(filePath);
			this.defaultUniqueCode = hashCode;
		}		
		
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
		if(false == value.getClass().equals(propertyClazz)) {
				value = TypeHelper.getRequiredValue(org_value, propertyClazz.getName());
		}
		Map<Object, Class<?>> value_type = new HashMap<Object, Class<?>>();
		value_type.put(value, propertyClazz);
		ReflectHelper.callSetter(beanObj, propertyName, value_type);

	}
	
	public String loadBeanUniqCode(String beanFilePath) {
	    String hashCode = String.valueOf(PathHelper.resolveAbsolutePath(beanFilePath).hashCode()); 
		return hashCode;
	}
	
	
}
