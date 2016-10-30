package handy.tools.interfaces;


import handy.tools.aop.BasicClazDesc;
import java.io.IOException;
import java.util.List;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;

public class ClassFactory {
	
	private static final String proxyClassPath = "D:\\360sych\\360sych"
								+ "\\git_repos\\PersonalSourceRepository\\coffee-test\\target\\classes";
	
	private ClassPool pool;
	//private InstGenerator instMaker;
	
	private ClassFactory() {
		
		try {
			setPool(AspectHandler.InitNonDefPool(new String[]{proxyClassPath}));
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//setInstMaker(new InstGenerator());		
	}
	
	private static class Singleton {
		 private static final ClassFactory cf = new ClassFactory();
	}
	public static final ClassFactory getInstance() {
		return Singleton.cf;
	}
	public ClassPool getPool() {
		return pool;
	}
	public void setPool(ClassPool pool) {
		this.pool = pool;
	}
	
	public void buildClazz(BasicClazDesc bcd, String[] importClazzes) {
		//System.out.println(getPool().getCtClass("handy.auto.test.interfaces.TestResult"));
		if(getPool() == null) {
			try {
				setPool(AspectHandler.InitNonDefPool(new String[]{proxyClassPath}));
			} catch (NotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(null != importClazzes && importClazzes.length > 0) {
			for(String importClazz : importClazzes) {
				getPool().importPackage(importClazz);
			}
		}
		
		try {
			AspectHandler.createClass(getPool(), bcd);
			bcd.ctClazz.writeFile(proxyClassPath);
			bcd.ctClazz.defrost();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void insertAspect(String clazName, String methodName, String aspect, String direct) {
			//System.out.println(getPool().getCtClass("handy.auto.test.aop.checkpoints.MathChkPoint"));
			
			if(getPool() == null) {
				try {
					setPool(AspectHandler.InitNonDefPool(new String[]{proxyClassPath}));
				} catch (NotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			CtClass cc = null;
			try {
				cc = getPool().getCtClass(clazName);
			} catch (NotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			//System.out.println(cc);
			
			try {
				if(direct.equals("before")) {
					System.out.println(methodName);
					AspectHandler.changeBeforeMethod(cc, methodName, aspect);
				} else if(direct.equals("after")) {
					AspectHandler.changeAfterMethod(cc, methodName, aspect);
				}
				cc.writeFile(proxyClassPath);
				cc.defrost();
							
			} catch (NotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (CannotCompileException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}

	public void addPubEmptyMethod(String clazzName, String methodName, String rtnType) {
		
		if(getPool() == null) {
			try {
				setPool(AspectHandler.InitNonDefPool(new String[]{proxyClassPath}));
			} catch (NotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		CtClass cc = null;
		CtClass cRtnType = null;
		try {
			
			cc = getPool().getCtClass(clazzName);
			//cc.getdECLAREDmE
			cRtnType = getPool().getCtClass(rtnType);
			AspectHandler.addNoParamMethod(cc, methodName,"{return;}", cRtnType, "public");
			cc.writeFile(proxyClassPath);
			cc.defrost();
			
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CannotCompileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public void replaceMethod(String clazzName, String methodName, String methodBody,
								String[] inParamType, String rtnType) {
		if(getPool() == null) {
			try {
				setPool(AspectHandler.InitNonDefPool(new String[]{proxyClassPath}));
			} catch (NotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		CtClass cc = null;
		try {
			cc = getPool().getCtClass(clazzName);
		} catch (NotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			
			CtMethod m = cc.getDeclaredMethod(methodName);
			CtClass[] cInParmTypes = new CtClass[inParamType.length];
			if(null != inParamType && inParamType.length > 0) {
				for(int i = 0; i < inParamType.length; i++) {
					cInParmTypes[i] = getPool().getCtClass(inParamType[i]);
				}
			}
					
			CtMethod m_new = new CtMethod(getPool().getCtClass(rtnType), methodName,cInParmTypes, cc);
			
			cc.removeMethod(m);
			cc.addMethod(m_new);
			
			cc.writeFile(proxyClassPath);
			cc.defrost();
			
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CannotCompileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
	}
	
}
