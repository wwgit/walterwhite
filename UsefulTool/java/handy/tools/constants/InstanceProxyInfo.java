package handy.tools.constants;

import handy.tools.aop.BasicMethodDesc;

public abstract class InstanceProxyInfo {

	public static String clazzName = "handy.tools.aop.InstanceProxy";
	private static final String[] parentClazNames = null;
	//private static final String m_name_newInst = "newInstance";
	//private static final String[] inParamTypes = {"java.lang.String"};
	//private static final String returnType = null;
	
	
	public static BasicMethodDesc newInstanceDesc() {
		
		BasicMethodDesc bmd = new BasicMethodDesc();
		bmd.inParamsType = new String[]{"java.lang.String","java.lang.String"};
		bmd.methodAccess = "public";
		bmd.methodName = "newInstance";
		
		bmd.methodBody = "{" 
				+ " obj"
				+ ""
				+ ""
				+ ""
				+ "}";
		
		
		
		return bmd;
	}
	
}
