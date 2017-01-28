/**   
* @Title: AgentsMain.java 
* @Package agents 
* @Description: TODO(what to do) 
* @author walterwhite
* @date 2017年1月13日 下午3:23:17 
* @version V1.0   
*/
package agents;

import com.sun.tools.attach.VirtualMachine;


/** 
 * @ClassName: AgentsMain 
 * @Description: TODO(what to do) 
 * @author walterwhite
 * @date 2017年1月13日 下午3:23:17 
 *  
 */
public class AgentsMain {

	/** 
	 * @Title: main 
	 * @Description: TODO(what to do) 
	 * @param @param args  
	 * @return void   
	 * @throws 
	 */
	public static void main(String[] args) {
		
		String path = System.getProperty("java.class.path");
		System.out.println(path);
		
		VirtualMachine vm;
		try {
			vm = VirtualMachine.attach("5540");
			vm.loadAgent("D:\\360sych\\360sych\\git_repos\\java_repos\\walterwhite\\ClassesAgents\\target\\ClassesAgents-0.0.1-SNAPSHOT.jar");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
