package handy.tools.aop;

import java.util.List;

import javassist.CtClass;

public class BasicClazDesc {
	
	public String clazName;
	public CtClass ctClazz;
	public List<String> interfaces;
	public List<BasicMethodDesc> methodsDesc;
	public List<ConstructDesc> construDesc;

}
