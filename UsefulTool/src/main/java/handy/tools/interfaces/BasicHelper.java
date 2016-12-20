package handy.tools.interfaces;

public abstract class BasicHelper {
	
	public static String UpperCaseFirstChar(String str, StringBuilder sb) {
		
		sb.append(str);
		sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
		return sb.toString();
		
	}
	
	public static String ConvertClazToStr(Class clazz) {
		
		String str = null;
		
		str = clazz.getName();
		return str;
	}

}
