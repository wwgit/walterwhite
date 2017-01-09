package handy.tools.helpers;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import handy.tools.constants.DataTypes;
import handy.tools.constants.FileFormat;
import handy.tools.constants.TxTFile;

/** 
* @ClassName: BasicHelper 
* @Description: TODO(some very basic needs - and very general functions) 
* @author walterwhite
* @date 2017年1月9日 下午5:30:57 
*  
*/
public abstract class BasicHelper implements TxTFile, FileFormat, DataTypes {
	

	/** 
	* @Title: GetAbsoluteFilePath 
	* @Description: TODO(try to resolve absolute path for a relative path - not tested yet) 
	* @param @param filePath
	* @param @return  
	* @return String   
	* @throws 
	*/
	public static String GetAbsoluteFilePath(String filePath) {
		
		String path = null;
		//System.out.println("GetAbsoluteFilePath: " + filePath);
		File file = new File(filePath);
		if(!file.exists()) {
			try {
				System.out.println("file not exist \\".replaceAll("\\\\", ""));
				System.out.println(System.getProperty("user.dir").replaceAll("\\\\", "/") + "/" + filePath);
				file = new File(System.getProperty("user.dir").replaceAll("\\\\", "/") + "/" + filePath);
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		try {
			path = file.getCanonicalPath();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return path;
	}
	
	public static String UpperCaseFirstChar(String str) {
		
		StringBuilder sb = new StringBuilder(str);
		sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
		
		return sb.toString();
		
	}
	
	
	public static Class<?> getRequireClass(String type) throws ClassNotFoundException {
		
		Class<?> requiredClz = null;
		int dataType = TypeHelper.parseType(type);
		
		switch(dataType) {
		case DataTypes.JAVA_BASIC_INT:
			requiredClz = int.class;
			break;
		case DataTypes.JAVA_BASIC_DOUBLE:
			requiredClz = double.class;
			break;
		case DataTypes.JAVA_BASIC_LONG:
			requiredClz = long.class;
			break;
		case DataTypes.JAVA_LANG_INTEGER:
			requiredClz = Integer.class;
			break;
		case DataTypes.JAVA_LANG_DOUBLE:
			requiredClz = Double.class;
			break;
		case DataTypes.JAVA_LANG_LONG:
			requiredClz = Long.class;
			break;	
		case DataTypes.JAVA_LANG_STRING:
			requiredClz = String.class;
			break;	
		default:
			requiredClz = Class.forName(type);
			break;
		}
		
		return requiredClz;
	}
	
	public int getFileSuffix(String filePath) {
		
		if(filePath.endsWith("properties")) {
			return TXTFILE_SUFFIX_PROPERTY;
		} else if(filePath.endsWith("xml")) {
			return TXTFILE_SUFFIX_XML;
		} else if(filePath.endsWith("json")) {
			return TXTFILE_SUFFIX_JSON;
		} else {
			return 0;
		}
		
	}
	
	
	public static Map<String,String> StrArrayToHashMap(String[] keys, String[] values) throws Exception {
		Map<String,String> rtnMap = null;
		if(keys.length != values.length) {
			throw new Exception("length does Not equal for each other !");
		}
		if(keys.length < 1) {
			throw new Exception("not element in keys !");
		}
		if(keys == null || values == null) {
			throw new NullPointerException("inputed keys or values is NULL !");
		}
		
		rtnMap = new HashMap<String,String>();
		for(int i = 0; i < keys.length; i++) {
			rtnMap.put(keys[i], values[i]);
		}
		
		return rtnMap;
	}
	
	public static Map<String,Object> StrObjArrayToHashMap(String[] keys, Object[] values) throws Exception {
		Map<String,Object> rtnMap = null;
		if(keys.length != values.length) {
			throw new Exception("length does Not equal for each other !");
		}
		if(keys.length < 1) {
			throw new Exception("not element in keys !");
		}
		if(keys == null || values == null) {
			throw new NullPointerException("inputed keys or values is NULL !");
		}
		
		rtnMap = new HashMap<String,Object>();
		for(int i = 0; i < keys.length; i++) {
			rtnMap.put(keys[i], values[i]);
		}
		
		return rtnMap;
	}

}
