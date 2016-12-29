package handy.main;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import handy.tools.db.ComplexValue;
import handy.tools.db.DbManager;
import handy.tools.factorties.XmlBeanFactory;
import handy.tools.helpers.DbHelper;
import handy.tools.helpers.FileHelper;
import handy.tools.helpers.PathHelper;
import handy.tools.helpers.XmlHelper;
import handy.tools.interfaces.BeanFactory;
import handy.tools.interfaces.ClassFactory;
import handy.tools.io.NioHelper;

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
		
		//XmlHelper.createXmlDoc("config/example7.xml", "example", data);
		//XmlHelper.createEmptyXmlDoc("example7.xml", "examples");
		//XmlHelper.addSubElements("example7.xml", "examples", data);
		//XmlHelper.createEmptyXmlDoc("d://example.xml");
		//XmlHelper.getAttributesOfElement(XmlHelper.readXmlFrmFile("example7.xml"), "good1");
		
		long startTime = System.currentTimeMillis();
		
		//System.out.println("ref_local_null".hashCode());
		//System.out.println("ref_local_null".hashCode());
		//System.out.println("ref_local_null".hashCode());
		/*XmlHelper.getBean("example7.xml", "DbConfig");
		XmlHelper.getBean("example7.xml", "SMSConfig");
		XmlHelper.getBean("example7.xml", "SMSConfig2");
		XmlHelper.getBean("example7.xml", "SMSConfig3");
		XmlHelper.getBean("example7.xml", "SMSConfig4");
		XmlHelper.getBean("example7.xml", "SMSConfig5");
		XmlHelper.getBean("example7.xml", "SMSConfig6");*/
		
		BeanFactory xmlBeanCreator = new XmlBeanFactory("example7.xml");
		Object DbConfig = xmlBeanCreator.getBean("DbConfig");

		long endTime = System.currentTimeMillis();
		System.out.println("程序运行时间：" + (endTime - startTime) + "ms");
		//System.out.println("程序运行时间：" + (endTime - startTime) + "ms");
		
		
	}

}
