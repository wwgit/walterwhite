package handy.tools.helpers;

public abstract class BasicHelper {
	
	public static String UpperCaseFirstChar(String str) {
		
		StringBuilder sb = new StringBuilder(str);
		sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
		
		return sb.toString();
		
	}
	
	public static String ConvertClazToStr(Class clazz) {
		
		String str = null;
		
		str = clazz.getName();
		return str;
	}
	

}
