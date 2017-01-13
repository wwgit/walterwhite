/**   
* @Title: ClassModifyTransform.java 
* @Package agents 
* @Description: TODO(what to do) 
* @author walterwhite
* @date 2017年1月13日 下午2:45:04 
* @version V1.0   
*/
package agents;

import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

import agents.annotations.AnnoVerifyHandler;
import agents.aop.JavassistHelper;
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
//			System.out.println("making class:" + theCtClazz);
			if(theCtClazz.getName().startsWith("java")) {
//				System.out.println("return because: sys class:" + theCtClazz.getName());
				return classfileBuffer;
			}
			if(theCtClazz.getName().startsWith("sun")) {
//				System.out.println("return because: sys class:" + theCtClazz.getName());
				return classfileBuffer;
			}
			if(theCtClazz.isInterface() == false) {
				System.out.println("ready to modify class Name:" + theCtClazz.getName());
				AnnoVerifyHandler.MethodAnnoChk(theCtClazz.getName());
			}
			tranformed = theCtClazz.toBytecode();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CannotCompileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return tranformed;
	}
	
	

}
