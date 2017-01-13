package handy.tools.helpers;

import handy.tools.annotations.MethodArgs;

import java.io.IOException;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtBehavior;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.NotFoundException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;



/** 
* @ClassName: JavassistHelper 
* @Description: TODO(what to do) 
* @author walterwhite
* @date 2017年1月13日 下午2:13:30 
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
	
	public static CtClass[] getCtClazzes(String[] ClazzNames) throws Exception {
		
		CtClass[] theCtClazzes = new CtClass[ClazzNames.length];
		
		for(int i = 0; i < ClazzNames.length; i++) {
			theCtClazzes[i] = cp.get(ClazzNames[i]);
		}		
		return theCtClazzes;
	}
	
	public static CtClass[] getCtClazzes(Class<?>[] Clazzes) throws Exception {
		
		CtClass[] theCtClazzes = new CtClass[Clazzes.length];
		
		for(int i = 0; i < Clazzes.length; i++) {
			theCtClazzes[i] = cp.get(Clazzes[i].getName());
		}	
		return theCtClazzes;
	}
	
	
	public static void classMethodAddBefore(String clazName, String aspect) throws Exception {
		
		CtClass theCtClazz = cp.get(clazName);
		
		CtMethod[] methods = theCtClazz.getDeclaredMethods();
		for(CtMethod method : methods) {
			Object anno = method.getAnnotation(MethodArgs.class);
			if(anno instanceof MethodArgs) {
				System.out.println("anno MethodArgs found, calling methodInsert:" + method.getName());
				methodInsert(method,
						aspect);
			}
		}
		
	}
	
	public static void methodInsert(CtMethod method, String aspect) throws Exception {
		
		CtClass[] paramTypes = method.getParameterTypes();
      	
		if(paramTypes.length < 1) {
    		throw new Exception("For method argument check, "
    				+ "it must at least contained one argument !");
    	}
		for(CtClass paramType : paramTypes) {
			System.out.println("printing param types:" + paramType.getName());
			if(TypeHelper.isBasicOrBasicArray(paramType.getName())) return;			
		}
		method.insertBefore(aspect);
      	
	}
	

    public static void oneMethodArgVerify(CtClass ctClazz, String methodName, CtClass[] paramTypes) throws Exception {
    	
      	if(paramTypes.length < 1) {
    		throw new Exception("For method argument check, "
    				+ "it must at least contained one argument !");
    	}
      	  	
      	CtMethod method = ctClazz.getDeclaredMethod(methodName, paramTypes);
      
    	String aspects = "handy.tools.aop.AspectsHandler.";  
    	
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
