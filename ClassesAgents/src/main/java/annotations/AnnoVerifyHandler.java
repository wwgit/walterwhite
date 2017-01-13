
package annotations;

import java.lang.reflect.Method;

import aop.JavassistHelper;


/** 
* @ClassName: AnnoVerifyHandler 
* @Description: TODO(what to do) 
* @author walterwhite
* @date 2017年1月13日 下午2:16:40 
*  
*/
public abstract class AnnoVerifyHandler {
	
	/** 
	* @Title: MethodAnnoChk 
	* @Description: TODO(aop related functions) 
	* @param @param theClazz  
	* @return void   
	* @throws 
	*/
	public static void MethodAnnoChk(Class<?> theClazz) {
		
		Method[] methods = null;	
		try {
//			no inherited methods
			methods = theClazz.getDeclaredMethods();
			for(int i = 0; i < methods.length; i++) {
				if(methods[i].isAnnotationPresent(MethodArgs.class)) {
					System.out.println("MethodArgs annotation found !");
				}
			}		
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void MethodAnnoChk(String clazzName) {
		
		Method[] methods = null;	
		try {
//			no inherited methods
			methods = JavassistHelper.getClassPool().get(clazzName).toClass().getDeclaredMethods();
			for(int i = 0; i < methods.length; i++) {
				if(methods[i].isAnnotationPresent(MethodArgs.class)) {
					System.out.println("MethodArgs annotation found !");
				}
			}			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}	

}
