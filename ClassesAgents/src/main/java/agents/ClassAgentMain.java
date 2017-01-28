/**   
* @Title: ClassAgentMain.java 
* @Package agents 
* @Description: TODO(what to do) 
* @author walterwhite
* @date 2017年1月28日 上午10:22:28 
* @version V1.0   
*/
package agents;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;

/** 
 * @ClassName: ClassAgentMain 
 * @Description: TODO(what to do) 
 * @author walterwhite
 * @date 2017年1月28日 上午10:22:28 
 *  
 */
public class ClassAgentMain {
	
	public static void agentmain(String agentArgs, Instrumentation _inst) {
		
		System.out.println("agentArgs: " + agentArgs);
		// Initialize the static variables we use to track information.  
		Instrumentation inst = _inst;
		ClassFileTransformer trans = new ClassModifyTransformer();
//		System.out.println(trans.getClass().getSimpleName());

		System.out.println("redefine supported ? " + inst.isRedefineClassesSupported());
		System.out.println("retransform supported ? " + inst.isRetransformClassesSupported());
//		inst.
		Class<?>[] classes = inst.getAllLoadedClasses();
	    for(Class<?> cls :classes){
	        	if(cls.getName().startsWith("handy")) {
	        		System.out.println(cls.getName());
	        	}
	    }
		System.out.println("Adding a ClassModifyTransformer instance to the JVM.");
		inst.addTransformer(trans);
	    
	}

}
