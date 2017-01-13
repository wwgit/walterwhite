/**   
* @Title: ClassModifyAgent.java 
* @Package agents 
* @Description: TODO(what to do) 
* @author walterwhite
* @date 2017年1月13日 下午2:41:37 
* @version V1.0   
*/
package agents;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;

/** 
 * @ClassName: ClassModifyAgent 
 * @Description: TODO(what to do) 
 * This method is called before the application’s main-method is called, 
 * when this agent is specified to the Java VM. 
 * 
 * 
 * @author walterwhite
 * @date 2017年1月13日 下午2:41:37 
 *  
 */
public class ClassModifyAgent {

	/** 
	 * @Title: main 
	 * @Description: TODO(what to do) 
	 * @param @param args  
	 * @return void   
	 * @throws 
	 */
	public static void premain(String agentArgs, Instrumentation _inst) {
		
		System.out.println("agentArgs: " + agentArgs);
		// Initialize the static variables we use to track information.  
		Instrumentation inst = _inst;
		ClassFileTransformer trans = new ClassModifyTransformer();
		System.out.println("Adding a ClassModifyTransformer instance to the JVM.");
		inst.addTransformer(trans);
	}

}
