/**   
* @Title: ClassModifyTransform.java 
* @Package agents 
* @Description: TODO(what to do) 
* @author walterwhite
* @date 2017年1月13日 下午2:45:04 
* @version V1.0   
*/
package agents;

import handy.tools.helpers.JavassistHelper;

import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

import javassist.CannotCompileException;
import javassist.CtClass;

/** 
 * @ClassName: ClassModifyTransform 
 * @Description: TODO(what to do) 
 * @author walterwhite
 * @date 2017年1月13日 下午2:45:04 
 *  
 */
public class ClassModifyTransformer implements ClassFileTransformer {

	
	public ClassModifyTransformer() {
		System.out.println("ClassModifyTransform initializing !");
	}
	
	
	/* (non-Javadoc)
	 * @see java.lang.instrument.ClassFileTransformer#transform(java.lang.ClassLoader, java.lang.String, java.lang.Class, java.security.ProtectionDomain, byte[])
	 */
	public byte[] transform(ClassLoader loader, String className,
			Class<?> classBeingRedefined, ProtectionDomain protectionDomain,
			byte[] classfileBuffer) throws IllegalClassFormatException {
		
		JavassistHelper.InitNonDefPool();
		CtClass theCtClazz = null;
		byte[] tranformed = null;
		
		try {
			theCtClazz = JavassistHelper.getClassPool().makeClass(new java.io.ByteArrayInputStream(  
			        classfileBuffer));
			
			if(theCtClazz.isInterface() == false) {
				if(theCtClazz.getName().startsWith("handy")) {
					System.out.println("ready to modify class Name: " + theCtClazz.getName());
					JavassistHelper.classMethodAddBefore(theCtClazz,
							"{handy.tools.aop.ArgumentsCheck.argCheck($$);}");
					System.out.println("modify class Done !");
				} else {
					return classfileBuffer;
				}
			} else {
				return classfileBuffer;
			}
			
			tranformed = theCtClazz.toBytecode();
			theCtClazz.defrost();
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (RuntimeException e) {
			e.printStackTrace();
		} catch (CannotCompileException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}	
//		System.out.println("return of transform method in Transformer !");
		return tranformed;
	}
	
}
