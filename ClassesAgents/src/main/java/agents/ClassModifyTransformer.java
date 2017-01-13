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

import javassist.CannotCompileException;
import javassist.CtClass;
import annotations.AnnoVerifyHandler;
import aop.JavassistHelper;

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
			if(theCtClazz.isInterface() == false) {
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
