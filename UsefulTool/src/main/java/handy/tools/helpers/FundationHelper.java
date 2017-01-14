package handy.tools.helpers;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import handy.tools.annotations.MethodArgs;
import handy.tools.constants.DataTypes;
import handy.tools.constants.FileFormat;
import handy.tools.constants.TxTFile;


/** 
* @ClassName: BasicHelper 
* @Description: TODO(what to do) 
* @author walterwhite
* @date 2017年1月13日 下午2:13:10 
*  
*/
public abstract class FundationHelper implements TxTFile, FileFormat, DataTypes {
	

	/** 
	* @Title: GetAbsoluteFilePath 
	* @Description: TODO(try to resolve absolute path for a relative path - not tested yet) 
	* @param @param filePath
	* @param @return  
	* @return String   
	* @throws 
	*/
//	@MethodArgs
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
	
//	@MethodArgs
	public static String UpperCaseFirstChar(String str) {
		
		StringBuilder sb = new StringBuilder(str);
		sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
		System.out.println(sb.toString());
		return sb.toString();
		
	}
	
	@MethodArgs
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
	
//	@MethodArgs
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
	
//	@MethodArgs
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
