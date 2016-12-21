package handy.tools.files;

import handy.tools.helpers.FileHelper;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONReader;

public class JsonHelper extends FileHelper {
	
	/*tested
	 * 
	 * */
	public static boolean canBeJsonArray(String jsonStr) {
		
		Pattern pArr = Pattern.compile("^\\[.*(\\{(\"(\\w|\\d)+\":.*,?)*\\})+.*\\]$");
		Matcher m = pArr.matcher(jsonStr);
		return m.matches();
	}
	
	/*tested
	 * 
	 * */
	public static boolean canBeJsonObj(String jsonStr) {
		
		Pattern pJsonObj = Pattern.compile("^\\{.*\\}$");
		Matcher m = pJsonObj.matcher(jsonStr);
		return m.matches();
		
	}
	
	/*tested
	 * 
	 * */
	public static String findTargetInJsonFile(String path,String key) throws Exception {
		
		String result = null;
		String jsonStr = null;		

		jsonStr = readAllFrmFile(path,"utf-8");
			
		if(canBeJsonObj(jsonStr)) {
				result = RetriValInJsonObj(jsonStr,key);
		} else if(canBeJsonArray(jsonStr)) {
				result = RetriValInJsonArr(JSON.parseArray(jsonStr),key);
		} else {
			throw new Exception("json contents is Not valid !");
		}
						
		return result;		
	}
	
	/*tested
	 * 
	 * */
	public static String RetriValInJsonObj(String jsonStr, String key) {
		
		JSONObject jObj = null;		
		jObj = JSON.parseObject(jsonStr);
		String result = null;
		
		for(Map.Entry<String, Object> entry : jObj.entrySet()) {
			result = entry.getValue().toString();
			if(entry.getKey().equals(key)) {
				//System.out.println("Bing Go in JsonObj !" + result);
				break;
			} 
			
			if(canBeJsonObj(result)) {
				result = RetriValInJsonObj(jsonStr,key);
			} else if(canBeJsonArray(result)) {
				result = RetriValInJsonArr(JSON.parseArray(result),key);
			}
		}
				
		return result;
	}
	
	/*tested
	 * 
	 * */
	public static String RetriValInJsonArr(JSONArray jArr,String key) {
		
		String result = null;
		String arrVal = null;
		
		for(int i =0; i < jArr.size(); i++) {
			arrVal = jArr.get(i).toString();
			if(canBeJsonObj(arrVal)) {
				result = RetriValInJsonObj(arrVal,key);
			} else if(canBeJsonArray(arrVal)) {
				result = RetriValInJsonArr(JSON.parseArray(arrVal),key);
			}
			if(null != result) {
				//System.out.println("Bing Go JsonArr !" + result);
				break;
			}
			//System.out.println(result);
		}
			
		return result;
	}

}
