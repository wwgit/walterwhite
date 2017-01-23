package handy.main;

import java.sql.Date;
import java.text.DateFormat;
import java.util.HashMap;
import java.util.Map;

import handy.tools.bean.XmlBeanMapFacadeImpl;
import handy.tools.helpers.FundationHelper;
import handy.tools.helpers.JavassistHelper;
import handy.tools.helpers.PathHelper;
import handy.tools.helpers.ReflectHelper;

/** 
* @ClassName: ToolMain 
* @Description: TODO(what to do) 
* @author walterwhite
* @date 2017年1月13日 下午2:09:45 
*  
*/
public class ToolMain {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		/*
		 * 
		 * example of using javassist - aop programing
		 * 
		 * */
		FundationHelper.UpperCaseFirstChar("helloWorld !");
//		TestBean bean = new TestBean();
//		ReflectHelper.retrieveBeanPropertyTypes(bean.getClass());
		PathHelper.resolveAbsolutePath("config/db.properties");
		XmlBeanMapFacadeImpl xmlBean = new XmlBeanMapFacadeImpl();
		
//		bean.setTestAnno("test aop");
//		bean.getFileSuffix("abc.xml");
		//System.out.println(bean.getTestAnno());
		
//		ClassFactory.getInstance().replaceMethod("handy.tools.aop.InstGenerator", 
//				"newInstance","{List obj = new ArrayList<>();return obj;}"
//				, new String[]{}, "java.util.List");
		
		//JavassistHelper.test();
//		String clazName = "handy.tools.helpers.BasicHelper";
		//Class<?> claaz = Class.forName(clazName).getM

//		JavassistHelper.InitNonDefPool();
//		JavassistHelper.classMethodAddBefore(clazName,
//											"{handy.tools.aop.AspectsHandler.argCheck($$);}");
//		JavassistHelper.getClassPool().appendClassPath(classRootPath);
		//JavassistHelper.testInsertBefore(clazName, "- a mark of insert before data ",classRootPath);
		
		//JavassistHelper.testInsertAfter(clazName, "- a mark of insert after data ",classRootPath);
		//TRY TEST AFTER INSERT
		//BasicHelper.getRequireClass("handy.tools.helpers.TypeHelper");
		
		//TypeHelper.getRequiredValue(1, "java.lang.String");
		//TypeHelper.parseType("asdf");
		
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
		
//		BeanFactory xmlBeanCreator = new XmlBeanFactory("example8.xml");
		//xmlBeanCreator.loadBeans("example8.xml");
//		BeanFactory propBeanCreator = new PropertiesBeanFactory("configs/db_config.properties");
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
			//TestBean refBeanChk3 = (TestBean) xmlBeanCreator.getBean("TestBean");
			//DbConfig propChk2 = (DbConfig) propBeanCreator.getBean("dbConfig1");
			//System.out.println("propChk:" + propChk2.getPassword());
			//System.out.println("refBeanChk3:" + refBeanChk3.getConfig().getUrl());
		}
		//TestBean refBeanChk3 = (TestBean) xmlBeanCreator.getBean("TestBean");
		//System.out.println("refBeanChk3:calling getTestAnno: " + refBeanChk3.getTestAnno());
		
		//ParseObjAnno.parseTypesAnnotation(refBeanChk3);
		
		//ParseObjAnno.parseMethodAnnotation(refBeanChk3,AnnoTypeTest.class);
		//ParseObjAnno.parseMethodAnnotation(refBeanChk3,AnnoMethodTest.class);
		//ParseObjAnno.parseFieldAnnotation(refBeanChk3, AnnoFieldTest.class);
		
		long endTime = System.currentTimeMillis();
		System.out.println("time " + (endTime - startTime) + "ms");	
		
		Class<?> claz = int.class;
		int[] i = new int[]{1,2,3,4};
		String[] value = "f1,f2,f3".split(",");
		String sv = "12345";
		int ivi = Integer.parseInt(sv);
		long lv = ivi;
		double dv = lv;
		float fv = (float) dv;
		
		
		
		int iv = (int) lv;
		iv = (int) dv;
		iv = (int) fv;
		
		Object[] objArr = new Object[10];
		
		Object objjj = objArr;
		
		System.out.print(objjj.getClass().getSimpleName() + "\n");
		System.out.print(dv + "\n");
		
		Object obj = value;
		System.out.println(obj.getClass().getSimpleName() + "	" + XmlBeanMapFacadeImpl[].class.getSimpleName());
		System.out.println(int[].class.getSimpleName().replaceAll("\\[\\]", ""));
		System.out.println(int.class.getSimpleName());
		Date date = new java.sql.Date(Long.parseLong("20010101"));
		System.out.println(Date.valueOf("2001-01-01"));
//		System.out.print(DateFormat.getDateInstance().parse("2001-01-02"));
		Object objdate = DateFormat.getDateInstance().parse("20010101");
		
		System.out.println(objdate.getClass());
		
		for(int t = 0; t < value.length; t++) {
			System.out.println(((String[])obj)[t]);
		}
			
		//ExcelHelper.readXlsWorkSheet(ExcelHelper.getXlsWorkBook("test.xls"));
		
	}

}
