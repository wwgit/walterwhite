package handy.tools.helpers;

import handy.tools.annotations.MethodArgs;

import java.io.IOException;
import java.lang.annotation.Annotation;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.NotFoundException;


/** 
* @ClassName: JavassistHelper 
* @Description: TODO(what to do) 
* @author walterwhite
* @date 2017年1月10日 下午7:55:11 
*  
*/
public abstract class JavassistHelper {
	
	private static ClassPool cp = null;
	
	public static ClassPool getClassPool() {
		return cp;
	}
	
	public static ClassPool InitDefaultPool() {
		return cp = ClassPool.getDefault();
	}
	public static ClassPool InitNonDefPool() {
		if(null == cp) {
			cp = new ClassPool(true);
		}
		return cp;
	}
	public static ClassPool InitNonDefPool(String[] classFilePaths) throws NotFoundException {
		if(null == cp) {
			cp = new ClassPool(true);

		}		
		for(String path : classFilePaths) {
			cp.insertClassPath(path);
		}	
		return cp;
	}
	
		
	public static void removeMethod(CtClass cc, String methodName) {
		
		CtMethod m = null;
		try {
			m = cc.getDeclaredMethod(methodName);
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("method Not found");
		}
		if(null != m) {
			try {
				cc.removeMethod(m);
			} catch (NotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

    public static void oneMethodArgVerify(CtMethod method, Annotation argAnno) throws Exception {
    	
    	MethodArgs theArgAnno = (MethodArgs) argAnno;
    	String aspects = "handy.tools.aop.AspectsHandler.";
    	
    	if(theArgAnno.containsBasicTypes()) {
    		throw new Exception("For method argument check, "
    				+ "it does Not support for basic java types right now !");
    	}
    	if(theArgAnno.howManyArgs() < 1) {
    		throw new Exception("For method argument check, "
    				+ "it must at least contained one argument !");
    	}
    	
    	aspects += "argCheck($$);"; 	
    	System.out.println("verify aspects: " + aspects);
    	method.insertBefore(aspects);
    	
    }
   
	
	public static void testInsertBefore(String clazName, String mark, String absolutePath) throws NotFoundException, CannotCompileException, IOException, ClassNotFoundException {
		//ClassPool cp = new ClassPool(true);
		CtClass ctClaz = cp.getCtClass(clazName);
		CtMethod[] ctMethods = ctClaz.getDeclaredMethods();
		CtClass[] ctInterfaces = ctClaz.getInterfaces();
		String insertAspect = "{ System.out.println(\"" + mark + "\" + $1); }";
		String argChkAspect = "{ handy.tools.aop.AspectsHandler.argCheck($$);}";
		
		String methodFlag = "markToAvoidDuplicateModificationBefore";
		for(CtMethod method : ctMethods) {
				
			if(methodFlag.equals(method.getName())) {
				System.out.println("finding method: " + method.getName());
				System.out.println("has inserted");
				return;
			}
		}
		
		CtMethod newCtMethodFlag = CtNewMethod.make("public void " + methodFlag + "(int arg1){}", ctClaz);
		ctClaz.addMethod(newCtMethodFlag);
		
		for(CtMethod method : ctMethods) {
			System.out.println("inserting aspect: to " + method.getName());
			//System.out.println(insertAspect);

			method.insertBefore(insertAspect);
			method.insertBefore(argChkAspect);
		}

		ctClaz.writeFile(absolutePath);
		ctClaz.defrost();
		
	}
	
	public static void testInsertAfter(String clazName, String mark, String absolutePath) throws NotFoundException, CannotCompileException, IOException, ClassNotFoundException {
		//ClassPool cp = new ClassPool(true);
		CtClass ctClaz = cp.getCtClass(clazName);
		CtMethod[] ctMethods = ctClaz.getDeclaredMethods();
		CtClass[] ctInterfaces = ctClaz.getInterfaces();
		String insertAspect = "{ System.out.println(\"" + mark + "\"  + $class.getName()); }";
		String argChkAspect = "{ AspectsHandler.argCheck($$);}";
		
		String methodFlag = "markToAvoidDuplicateModificationAfter";
		for(CtMethod method : ctMethods) {
				
			if(methodFlag.equals(method.getName())) {
				System.out.println("finding method: " + method.getName());
				System.out.println("has inserted");
				return;
			}
		}
		
		CtMethod newCtMethodFlag = CtNewMethod.make("public void " + methodFlag + "(int arg1){}", ctClaz);
		ctClaz.addMethod(newCtMethodFlag);
		
		for(CtMethod method : ctMethods) {
			System.out.println("inserting aspect: to " + method.getName());
			//System.out.println(insertAspect);
			method.insertAfter(insertAspect,true);
		}

		ctClaz.writeFile(absolutePath);
		ctClaz.defrost();
		
	}	
	
}
