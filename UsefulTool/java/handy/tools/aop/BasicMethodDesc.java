package handy.tools.aop;

import javassist.CtClass;
import javassist.CtMethod;

public class BasicMethodDesc {
	
	public String methodName;
	public String methodBody;
	public String methodAccess;
	public String returnType;
	public String[] inParamsType;
	public CtMethod cMethod;
	
}
