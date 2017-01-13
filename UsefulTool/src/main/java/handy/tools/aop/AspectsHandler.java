/**   
* @Title: AspectsHandler.java 
* @Package handy.tools.aop 
* @Description: TODO(what to do) 
* @author walterwhite
* @date 2017��1��10�� ����6:28:42 
* @version V1.0   
*/
package handy.tools.aop;


/** 
* @ClassName: AspectsHandler 
* @Description: TODO(what to do) 
* @author walterwhite
* @date 2017年1月13日 下午2:15:01 
*  
*/
public abstract class AspectsHandler {
	
	public static void argCheck(Object args) throws Exception {
		
		System.out.println("checking Object arguments !");
		if(args == null) {
			throw new Exception("args are null");
		}
		/*for(Object arg : args) {
			if(null == arg) throw new Exception("one of inputted arg is null");
		}*/
		
		
	}
	public static void argCheck(int args) throws Exception {
		
		System.out.println("checking int arguments !");
		/*for(Object arg : args) {
			if(null == arg) throw new Exception("one of inputted arg is null");
		}*/
		
		
	}

	public static void argCheck(Object ...args) throws Exception {
		
		System.out.println("checking int arguments !");
		for(Object arg : args) {
			if(null == arg) throw new Exception("one of inputted arg is null");
		}
		
		
	}
	public static void argCheck(Object args, Object args2) throws Exception {
		
		System.out.println("checking int arguments !");
		/*if(args == null || args2 == null) {
			throw new Exception("args are null");
		}*/
			
	}
	public static void argCheck(int args, Object args2) throws Exception {
		
		System.out.println("checking int arguments !");
		if(args2 == null) {
			throw new Exception("args are null");
		}
			
	}
	

}
