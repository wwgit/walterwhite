package handy.main;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import handy.tools.db.ComplexValue;
import handy.tools.db.DbManager;
import handy.tools.interfaces.ClassFactory;
import handy.tools.interfaces.DbHelper;
import handy.tools.interfaces.FileHelper;
import handy.tools.interfaces.PathHelper;
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
		
		long startTime = System.currentTimeMillis();
		
		//charArr = NioHelper.byteArrToCharArr(buf,"utf-8");
		//String str = FileHelper.readAllFrmDirectMem("D:\\test5.json", "utf-8", 20480);
		//String str = FileHelper.readFrmMapMem("D:\\test5.json", "utf-8", 20480, 0);
		//String str = FileHelper.readAllFrmFile("D:\\test5.json","utf-8");
		//FileHelper.FileCopyOld("D:\\test2.json", "D:\\test6.json", 20480);
		//FileHelper.FastFileCopy("D:\\test2.json", "D:\\test10.json", 20480,200);
		//System.out.println("reading buff: " + str);
		
		Map conds = new HashMap();
		//Map and_or = new HashMap();
		List<String> and_or = new ArrayList<String>();
		for(int i = 0 ; i < 100; i++) {
			conds.put("cond_key"+i, "cond_value"+i);
			and_or.add(i, "and"+i);
		}
		
		//DbHelper.prepareQuerySql("select * from", "table1", conds, and_or);
		//System.out.println(System.getProperty("user.dir"));
		String str = "123";
		String[] arr = new String[2];
		Long lng = Long.valueOf(str);
		InputStream is = new FileInputStream("D:\\test2.json");
		ComplexValue cv = new ComplexValue();
		cv.flag = 0;
		cv.data = is;
		//lng.l
		Integer inter = Integer.valueOf(str);
		BigDecimal bd = new BigDecimal(1234567890);
		System.out.println(str.getClass().toString());
		System.out.println(lng.getClass().toString());
		System.out.println(inter.getClass().toString());
		System.out.println(bd.getClass().toString());
		System.out.println(arr.getClass().toString());
		System.out.println(is.getClass().toString());
		System.out.println(cv.getClass().toString());
		//System.out.println(PathHelper.resolveAbsolutePath("config/db_config.properties"));
		//DbManager manager = new DbManager("config/db_config.properties");
		long endTime = System.currentTimeMillis();
		
		System.out.println("程序运行时间：" + (endTime - startTime) + "ms");
		//System.out.println("程序运行时间：" + (endTime - startTime) + "ms");
		
		
	}

}
