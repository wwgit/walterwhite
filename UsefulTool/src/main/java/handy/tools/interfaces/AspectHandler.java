package handy.tools.interfaces;

import handy.tools.aop.BasicClazDesc;
import handy.tools.aop.BasicMethodDesc;
import handy.tools.aop.ConstructDesc;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.List;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtMethod;
import javassist.NotFoundException;

//import javassist.CtClass;

public abstract class AspectHandler {
	
	
	public static ClassPool InitDefaultPool() {
		return ClassPool.getDefault();
	}
	public static ClassPool InitNonDefPool() {
		return new ClassPool(true);
	}
	public static ClassPool InitNonDefPool(String[] classFilePaths) throws NotFoundException {
		
		ClassPool pool = new ClassPool(true);		
		for(String path : classFilePaths) {
			pool.insertClassPath(path);
		}
		
		return pool;
	}	
	
	private static CtClass getBasicCType(String typeName) {
		
		if(typeName.matches("void")) {
			return CtClass.voidType;
		} else if(typeName.matches("int$")) {
			return CtClass.intType;
		} else if(typeName.matches("long$")) {
			return CtClass.longType;
		} else if(typeName.matches("double$")) {
			return CtClass.doubleType;
		} else if(typeName.matches("float$")) {
			return CtClass.floatType;
		} else if(typeName.matches("char$")) {
			return CtClass.charType;
		} else if(typeName.matches("byte$")) {
			return CtClass.byteType;
		} else if(typeName.matches("short$")) {
			return CtClass.shortType;
		} else if(typeName.matches("boolean$")) {
			return CtClass.booleanType;
		} else {
			return null;
		}
		
		
	}
	
	private static CtClass getBasicArrCType(String typeName, ClassPool pool) throws NotFoundException {
		
		if(typeName.matches("(\\[B$)|(byte Array)")) {
			return pool.getCtClass(new byte[]{}.getClass().toString());
		} else if(typeName.matches("(\\[C$)|(char Array)")) {
			return pool.getCtClass(new char[]{}.getClass().toString());
		} else {
			return null;
		}
		
	}
	
	
	private static void setMethodAccess(CtMethod method, String access) {
		
		if(access.matches("public")) {
			method.setModifiers(Modifier.PUBLIC);
		} else if(access.matches("private")) {
			method.setModifiers(Modifier.PRIVATE);
		} else if(access.matches("protected")) {
			method.setModifiers(Modifier.PROTECTED);
		}
		
		if(access.matches("synchronized")) {
			method.setModifiers(Modifier.SYNCHRONIZED);
		} else if(access.matches("static")) {
			method.setModifiers(Modifier.STATIC);
		} else {
			return;
		}
	}
	
	
	private static void setInterfaces(ClassPool pool, BasicClazDesc bcd) 
									throws NotFoundException, CannotCompileException {
		
		List<String> interfaceNames = bcd.interfaces;
		if(interfaceNames != null && interfaceNames.size() > 0) {
			for(String interfaceName : interfaceNames) {
				CtClass superClz = pool.getCtClass(interfaceName);
				if(null == superClz) {
					throw new NotFoundException(interfaceName + " Not Found !");
				}
				bcd.ctClazz.setSuperclass(superClz);
			}
		}	
		
	}
	
	private static void setConstructors(ClassPool pool, BasicClazDesc bcd) throws NotFoundException, CannotCompileException {
		
		List<ConstructDesc> constructors = bcd.construDesc;
		if(null != constructors && constructors.size() > 0) {
			for(int i = 0; i < constructors.size(); i++) {
				ConstructDesc ct = constructors.get(i);
				CtClass[] cParamTypes = pool.get(ct.inParamTypes);
				CtConstructor cConstructor = new CtConstructor(cParamTypes,bcd.ctClazz);
				cConstructor.setBody(ct.consBody);
				bcd.ctClazz.addConstructor(cConstructor);
			}
			
		}
		
	}
	
	private static void getMethodsReady(ClassPool pool, BasicClazDesc bcd) throws Exception {		
		
		if(null != bcd.methodsDesc && bcd.methodsDesc.size() > 0) {
			
			for(int i = 0; i < bcd.methodsDesc.size(); i++) {
				
				BasicMethodDesc bmd = bcd.methodsDesc.get(i);
				System.out.println(bmd.returnType);
				CtClass cRtnType = pool.getCtClass(bmd.returnType);
						
				//handle input param types
				String[] pTypesTmp = bmd.inParamsType;
				CtClass[] inParamTypes = null;
				if(null != pTypesTmp) {
					int paramSize = pTypesTmp.length;
					inParamTypes = new CtClass[paramSize];
					for(int j = 0; j < paramSize; j++) {
						inParamTypes[j] = pool.getCtClass(pTypesTmp[j]);
					}
				}
							
				addMethod(bcd.ctClazz, bmd.methodName, inParamTypes, 
						  bmd.methodBody, cRtnType, bmd.methodAccess);
			}
			
			
		}
		
			
	}
	
	public static void createClass(ClassPool pool, BasicClazDesc bcd) 
									throws Exception {
		
		if(null == pool || null == bcd || null == bcd.clazName) {
			throw new Exception("parameter error !");
		}
		
		bcd.ctClazz = pool.makeClass(bcd.clazName);
		
		if(null == bcd.ctClazz) {
			throw new Exception("makeClass failed !");
		}
		
		setInterfaces(pool, bcd);
		setConstructors(pool,bcd);		
		getMethodsReady(pool, bcd);	

	}
	
	public static void changeBeforeMethod(CtClass cc, String methodName, String aspect) 
									throws NotFoundException, CannotCompileException {
		System.out.println(methodName);
		System.out.println(cc);
		CtMethod m = cc.getDeclaredMethod(methodName);
		m.insertBefore(aspect);
		
	}
	
	public static void changeAfterMethod(CtClass cc, String methodName, String aspect) 
			throws NotFoundException, CannotCompileException {

			CtMethod m = cc.getDeclaredMethod(methodName);
			m.insertAfter(aspect);

	}
	
	public static void addMethod(CtClass cc, String methodName,CtClass[] inParamTypes, 
								String methodBody, CtClass cRtnType, String access) 
										throws CannotCompileException {				
		
		CtMethod m = new CtMethod(cRtnType, methodName,inParamTypes, cc);
		setMethodAccess(m, access);
		m.setBody(methodBody);
		cc.addMethod(m);
		
	}
	
	public static void addPublicMethod(CtClass cc, String methodName,CtClass[] inParamTypes, 
										String methodBody, CtClass cRtnType) 
												throws CannotCompileException {
		
		CtMethod m = new CtMethod(cRtnType, methodName,inParamTypes, cc);
		setMethodAccess(m, "public");
		m.setBody(methodBody);
		cc.addMethod(m);
		
	}
	
	public static void addNoParamMethod(CtClass cc, String methodName, 
										String methodBody, CtClass cRtnType, String access) 
											throws CannotCompileException {				
	
		CtMethod m = new CtMethod(cRtnType, methodName,new CtClass[]{}, cc);
		setMethodAccess(m, access);
		m.setBody(methodBody);
		cc.addMethod(m);

	}
	
	public static void removeMethod(CtClass cc, String methodName) {
		
		CtMethod m = null;
		try {
			m = cc.getDeclaredMethod(methodName);
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("method Not found");
		}
		if(null != m) {
			try {
				cc.removeMethod(m);
			} catch (NotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	

}
