/**   
* @Title: ArgumentsCheck.java 
* @Package handy.tools.aop 
* @Description: TODO(what to do) 
* @author walterwhite
* @date 2017年1月29日 下午1:13:26 
* @version V1.0   
*/
package handy.tools.aop;

/** 
 * @ClassName: ArgumentsCheck 
 * @Description: TODO(what to do) 
 * @author walterwhite
 * @date 2017年1月29日 下午1:13:26 
 *  
 */
public abstract class ArgumentsCheck {
	
	public static void argCheck(int arg) {
		System.out.println("checking arguments ! " + int.class.getSimpleName());
		return;
	}
	
	public static void argCheck(Object arg) throws Exception {
		
		if(arg == null) throw new Exception("arg is null");
		System.out.println("checking arguments ! " + arg.getClass().getSimpleName());
		
	}
	
	public static void argCheck(Object ...args) throws Exception {
		
		System.out.println("checking arguments !");
		if(args == null) throw new Exception("args are null");
		if(args.length < 1) throw new Exception("args length is less than 1 " + args);
	}
	
	public static void argCheck(int arg, Object ...args) throws Exception {
		argCheck(arg);
		argCheck(args);
	}
	public static void argCheck(int arg, Object args) throws Exception {
		argCheck(arg);
		argCheck(args);
	}
	public static void argCheck(Object arg, Object args) throws Exception {
		argCheck(arg);
		argCheck(args);
	}
	
	public static void argCheck(Object arg, int args) throws Exception {
		argCheck(arg);
		argCheck(args);
	}
	public static void argCheck(Object[] arg, int args) throws Exception {
		argCheck(arg);
		argCheck(args);
	}
	
	public static void argCheck(Object[] ...args) throws Exception {
		
		System.out.println("checking arguments !");
		if(args == null) throw new Exception("args are null");
		if(args.length < 1) throw new Exception("args length is less than 1 " + args);
		for(int i = 0; i < args.length; i++) {
			if(args[i] == null) throw new Exception("args are null");
			if(args[i].length < 1) throw new Exception("args length is less than 1 " + args[i]);
		}
	}
	
	public static void argCheck(Object[] args, Object ...args2) throws Exception {		
		argCheck(args);
		argCheck(args2);
	}

	public static void argCheck(Object args, Object[] ...args2) throws Exception {
		argCheck(args);
		argCheck(args2);
	}

}
