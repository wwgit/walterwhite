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
		System.out.println("Adding a ClassModifyTransformer instance to the JVM.");
		inst.addTransformer(trans);
	}

}
