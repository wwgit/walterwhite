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
import java.lang.instrument.UnmodifiableClassException;

/** 
 * @ClassName: ClassAgentMain 
 * @Description: TODO(what to do) 
 * @author walterwhite
 * @date 2017年1月28日 上午10:22:28 
 *  
 */
public class ClassAgentMain {
	
	public static void agentmain(String agentArgs, Instrumentation _inst) throws UnmodifiableClassException {
		
		System.out.println("agentArgs: " + agentArgs);
		// Initialize the static variables we use to track information.  
		Instrumentation inst = _inst;
		ClassFileTransformer trans = new ClassModifyTransformer();
		
		System.out.println("Adding a ClassModifyTransformer instance to the JVM.");
		inst.addTransformer(trans,true);

		System.out.println("redefine supported ? " + inst.isRedefineClassesSupported());
		System.out.println("retransform supported ? " + inst.isRetransformClassesSupported());
//		inst.
		Class<?>[] classes = inst.getAllLoadedClasses();
		int i = 0;
	    for(Class<?> cls :classes){
	        	if(cls.getName().startsWith("handy")) {
//	        		System.out.println(cls.getName());
//	        		System.out.println("can be modified: ?" + inst.isModifiableClass(cls));
	        		i++;
	        	}
	    }
	    
	    Class<?>[] mClasses = new Class<?>[i];
	    int n = 0;
	    for(int m = 0; m < classes.length; m++){
        	if(classes[m].getName().startsWith("handy")) {
//        		System.out.println(classes[m].getName());
//        		System.out.println("can be modified: ?" + inst.isModifiableClass(classes[m]));
        		mClasses[n] = classes[m];
        		n++;
        	}
	    }
	    System.out.println("Retransforming classes start...");
	    inst.retransformClasses(mClasses);
	    System.out.println("Retransforming classes end...");
	}

}
