/**   
* @Title: TestDataCreator.java 
* @Package store.db.sql.testdata 
* @Description: TODO(what to do) 
* @author walterwhite
* @date 2017年1月9日 下午4:47:09 
* @version V1.0   
*/
package store.db.sql.testdata;

import handy.tools.constants.DataTypes;
import handy.tools.helpers.TypeHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/** 
 * @ClassName: TestDataCreator 
 * @Description: TODO(what to do) 
 * @author walterwhite
 * @date 2017年1月9日 下午4:47:09 
 *  
 */

public abstract class TestDataCreator {
	
	
	/**
	 * @throws Exception  
	* @Title: rowDataGenerateUTF8 
	* @Description: TODO(what to do) 
	* @param @param howMany
	* @param @param colNames
	* @param @param colTypes
	* @param @return  
	* @return List<Map<String,Object>> rows -  <String,Object> represents one row data:colunm,value
	* @throws 
	*/
	public static List<Map<String,Object>> rowDataGenerateUTF8(int howMany, Map<String,String> columns_types) 
																throws Exception {
		
		Map<String,Object> row = null;
		List<Map<String,Object>> data = null;
		int type = 0;
		data = new ArrayList<Map<String,Object>>();
		for(int i = 0; i < howMany; i++) {
			row = new HashMap<String,Object>();
			for(Entry<String, String> colTypeMap: columns_types.entrySet()) {
				type = TypeHelper.parseType(colTypeMap.getValue());
				switch(type) {
				case DataTypes.JAVA_LANG_STRING:
					String s_value = new String("utf-8");s_value = "autoData" + colTypeMap.getKey();
					row.put(colTypeMap.getKey(), s_value);
					break;
				case DataTypes.JAVA_BASIC_INT:
					int i_value = i;
					row.put(colTypeMap.getKey(), i_value);break;
				case DataTypes.JAVA_LANG_INTEGER:
					int ii_value = i;
					row.put(colTypeMap.getKey(), ii_value);break;
				default:
					throw new Exception("column type " + colTypeMap.getValue() + "does Not support !");
				}
			}
			data.add(row);
		}	
		
		return data;
		
	}
	
	

}
