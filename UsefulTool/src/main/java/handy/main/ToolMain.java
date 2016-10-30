package handy.main;

import handy.tools.interfaces.ClassFactory;

public class ToolMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		/*
		 * 
		 * example of using javassist - aop programing
		 * 
		 * */
		
		
		ClassFactory.getInstance().replaceMethod("handy.tools.aop.InstGenerator", 
				"newInstance","{List obj = new ArrayList<>();return obj;}"
				, new String[]{}, "java.util.List");
		
		
		
		
		//GenInstance maker = new InstGenerator();
		
		
		//ClassFactory.getInstance().buildClazz(MathChkPointInfo.getClazInfo("int"),new String[]{"handy.auto.test.interfaces.TestResult"});
		
		//TestChkPoint tcp = new MathChkPoint();
		
		System.out.println("change class method logic");
		
		ClassFactory.getInstance().insertAspect("handy.auto.test.aop.checkpoints.MathChkPoint", "smaller",
												"{System.out.println(\"hello modifier\");}", "before");

	}

}
