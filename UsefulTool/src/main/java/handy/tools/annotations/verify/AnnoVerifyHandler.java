
package handy.tools.annotations.verify;

import handy.tools.helpers.JavassistHelper;
import javassist.CtMethod;



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
	/*public static void MethodAnnoChk(Class<?> theClazz) {
		
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
		
	}*/
	
	public static void MethodAnnoChk(String clazzName) {
		
		//Method[] methods = null;
		CtMethod[] cmethods = null;
		try {
//			no inherited methods
			//methods = JavassistHelper.getClassPool().get(clazzName).toClass().getDeclaredMethods();
			cmethods = JavassistHelper.getClassPool().get(clazzName).getDeclaredMethods();
			for(int i = 0; i < cmethods.length; i++) {
				//System.out.println("no to Class:" + cmethods[i].getName());
				
				Object[] annos = cmethods[i].getAnnotations();
				for(int j = 0; j < annos.length; j++) {
					System.out.println("anno " + annos[j] + " for method " + cmethods[i].getName());
					
				}
				if(1 > cmethods[i].getParameterTypes().length) {
					//System.out.println("param num:" + cmethods[i].getParameterTypes().length);
					continue;
				}
				JavassistHelper.oneMethodArgVerify(JavassistHelper.getClassPool().get(clazzName),
												   cmethods[i].getName(), cmethods[i].getParameterTypes());
				
//				System.out.println("end of method " + cmethods[i].getName());
				/*MethodArgs annos = (MethodArgs) cmethods[i].getAnnotation(MethodArgs.class);
				
				
				if(true == annos instanceof MethodArgs) {
					System.out.println("MethodArgs anno:" + annos);
				} else {
					System.out.println("not MethodArgs anno:" + annos);
				}*/
				/*if(cmethods[i].isAnnotationPresent(MethodArgs.class)) {
					System.out.println("MethodArgs annotation found !");
				}*/
			}			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}	

}
