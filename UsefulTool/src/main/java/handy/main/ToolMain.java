package handy.main;

import java.util.HashMap;
import java.util.Map;

import handy.tools.annotations.AnnoFieldTest;
import handy.tools.annotations.AnnoMethodTest;
import handy.tools.annotations.AnnoTypeTest;
import handy.tools.annotations.ParseObjAnno;
import handy.tools.db.TestBean;
import handy.tools.factorties.PropertiesBeanFactory;
import handy.tools.factorties.XmlBeanFactory;
import handy.tools.interfaces.bean.BeanFactory;

public class ToolMain {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		/*
		 * 
		 * example of using javassist - aop programing
		 * 
		 * */
		
		
	//	ClassFactory.getInstance().replaceMethod("handy.tools.aop.InstGenerator", 
	//			"newInstance","{List obj = new ArrayList<>();return obj;}"
	//			, new String[]{}, "java.util.List");
		
		
		
		
		//GenInstance maker = new InstGenerator();
		
		
		//ClassFactory.getInstance().buildClazz(MathChkPointInfo.getClazInfo("int"),new String[]{"handy.auto.test.interfaces.TestResult"});
		
		//TestChkPoint tcp = new MathChkPoint();
		
		//System.out.println("change class method logic");
		//System.out.println("change class method logic");
		
	//	ClassFactory.getInstance().insertAspect("handy.auto.test.aop.checkpoints.MathChkPoint", "smaller",
									//			"{System.out.println(\"hello modifier\");}", "before");

		//byte[] buf = new byte[6553500];
		
		
		//for(int i = 0; i < buf.length; i++) {
			//buf[i] = '9';
		//}
		
		//char[] cBuf = NioHelper.byteArrToCharArr(buf,"utf-8");
		//char[] charArr = new char[buf.length];
		
		
		
		//charArr = NioHelper.byteArrToCharArr(buf,"utf-8");
		//String str = FileHelper.readAllFrmDirectMem("D:\\test5.json", "utf-8", 20480);
		//String str = FileHelper.readFrmMapMem("D:\\test5.json", "utf-8", 20480, 0);
		//String str = FileHelper.readAllFrmFile("D:\\test5.json","utf-8");
		//FileHelper.FileCopyOld("D:\\test2.json", "D:\\test6.json", 20480);
		//FileHelper.FastFileCopy("D:\\test2.json", "D:\\test10.json", 20480,200);
		//System.out.println("reading buff: " + str);
		

		//System.out.println(PathHelper.resolveAbsolutePath("config/db_config.properties"));
		//DbManager manager = DbManager.getInstance("config/db_config.properties");
		
		Map<String, Object> data = new HashMap<String, Object>();
		
		data.put("good1", "value1");
		data.put("good2", "value2");
		data.put("good3", "value3");
		data.put("good4", "value4");
		//data.put("element3", "value3");
		
		BeanFactory xmlBeanCreator = new XmlBeanFactory("example8.xml");
		//xmlBeanCreator.loadBeans("example8.xml");
		//ConfigureParser propBeanCreator = new PropertiesConfigParser("config/db_config.properties");
		BeanFactory propBeanCreator = new PropertiesBeanFactory("config/db_config.properties");
		long startTime = System.currentTimeMillis();
		//xmlBeanCreator.lazyLoadBeans("example7.xml","example8.xml");
		
		//Testing propChk = (Testing) propBeanCreator.getBean("dbConfig1");
		//System.out.println("propChk:" + propChk.getPassword());
		
		//DbConfig propChk2 = (DbConfig) propBeanCreator.getBean("third");
		//System.out.println("propChk2:" + propChk2.getPassword());
		//DbConfig eleValChk = (DbConfig) xmlBeanCreator.getBean("SMSConfig");
		//System.out.println("eleValChk:" + eleValChk.getUrl());
		
		//DbConfig multValChk = (DbConfig) xmlBeanCreator.getBean("SMSConfig2");
		//System.out.println("multValChk:" + multValChk.getUrl());
		//System.out.println("multValChk:" + multValChk.getPassword());
		//System.out.println("multValChk:" + multValChk.getDbSize());
		//TestBean refBeanChk = (TestBean) xmlBeanCreator.getBean("TestBean");
		//System.out.println("refBeanChk:" + refBeanChk.getConfig().getUrl());
		
		
	//	TestBean refBeanChk2 = (TestBean) xmlBeanCreator.getBean("TestBean","example8.xml");
	//	System.out.println("refBeanChk2:" + refBeanChk2.getConfig().getUrl());
		
	//	TestBean refBeanChk3 = (TestBean) xmlBeanCreator.getBean("TestBean","example8.xml");
	//	System.out.println("refBeanChk3:" + refBeanChk3.getConfig().getUrl());
		
		for(int i = 0; i < 10000; i++) {
			//System.out.println(i);
			//System.out.println(xmlBeanCreator.getBean("TestBean","example8.xml"));
			TestBean refBeanChk3 = (TestBean) xmlBeanCreator.getBean("TestBean");
			//DbConfig propChk2 = (DbConfig) propBeanCreator.getBean("dbConfig1");
			//System.out.println("propChk:" + propChk2.getPassword());
			//System.out.println("refBeanChk3:" + refBeanChk3.getConfig().getUrl());
		}
		TestBean refBeanChk3 = (TestBean) xmlBeanCreator.getBean("TestBean");
		System.out.println("refBeanChk3:calling getTestAnno: " + refBeanChk3.getTestAnno());
		
		ParseObjAnno.parseTypesAnnotation(refBeanChk3);
		
		//ParseObjAnno.parseMethodAnnotation(refBeanChk3,AnnoTypeTest.class);
		//ParseObjAnno.parseMethodAnnotation(refBeanChk3,AnnoMethodTest.class);
		//ParseObjAnno.parseFieldAnnotation(refBeanChk3, AnnoFieldTest.class);
		
		long endTime = System.currentTimeMillis();
		System.out.println("程序运行时间：" + (endTime - startTime) + "ms");
		//System.out.println("程序运行时间：" + (endTime - startTime) + "ms");
		
		
		//ExcelHelper.readXlsWorkSheet(ExcelHelper.getXlsWorkBook("test.xls"));
		
		
	}

}
