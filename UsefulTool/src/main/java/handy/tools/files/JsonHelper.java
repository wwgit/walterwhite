package handy.tools.files;

import handy.tools.interfaces.FileHelper;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONReader;

public class JsonHelper extends FileHelper {
	
	
	public static boolean canBeJsonArray(String jsonStr) {
		
		Pattern pArr = Pattern.compile("^\\[.*(\\{(\"(\\w|\\d)+\":.*,?)*\\})+.*\\]$");
		Matcher m = pArr.matcher(jsonStr);
		return m.matches();
	}
	
	public static boolean canBeJsonObj(String jsonStr) {
		
		Pattern pJsonObj = Pattern.compile("^\\{.*\\}$");
		Matcher m = pJsonObj.matcher(jsonStr);
		return m.matches();
		
	}
	
	public static String findTargetInJsonFile(String path,String key) throws Exception {
		
		String result = null;
		String jsonStr = null;		

		jsonStr = readFileContents(path);
			
		if(canBeJsonObj(jsonStr)) {
				result = RetriValInJsonObj(jsonStr,key);
		} else if(canBeJsonArray(jsonStr)) {
				result = RetriValInJsonArr(JSON.parseArray(jsonStr),key);
		} else {
			throw new Exception("json contents is Not valid !");
		}
						
		return result;		
	}
	
	
	/*
	 * has bug using JSON Reader retired
	 * */
	public static String findTargetInJReaderObj(JSONReader jReader,String key) {
		
		String value = null;
		String actKey = null;
		
		jReader.startObject();	
		while(jReader.hasNext()) {
			actKey = jReader.readString();
			value = jReader.readObject().toString();
			System.out.println("key: " + actKey);
			System.out.println("value: " + value);
			//jReader.readObject().toString()
			if(actKey.equals(key)) {
				//jReader.endObject();
				System.out.println("Bingo ! " + value);
				break;
			}
			if(canBeJsonObj(value)) {
				//jReader.startObject();
				value = findTargetInJReaderObj(jReader,key);		
			} else if(canBeJsonArray(value)) {			
				value = findTargetInJReaderArr(jReader,key);
			}
		}
		jReader.endObject();			
		return value;
	}
	
	
	/*
	 * has bug using JSON Reader retired
	 * */
	public static String findTargetInJReaderArr(JSONReader jReader, String key) {
		
		String jsonStr = null;
		String result = null;
		jReader.startArray();
		
		while(jReader.hasNext()) {
			jsonStr = jReader.readString();
			System.out.println("array value: " + jsonStr);
			if(canBeJsonObj(jsonStr)) {
				result = findTargetInJReaderObj(jReader,key);
				
			} else if(canBeJsonArray(jsonStr)) {
				result = findTargetInJReaderArr(jReader,key);
			}
			if(null != result) {
				System.out.println("Bingo ! " + result);
				break;
			}
			
		}

		jReader.endArray();
		
		return result;
		
	}
	
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
