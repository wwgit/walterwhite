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
import handy.tools.helpers.DbHelper;
import handy.tools.helpers.FileHelper;
import handy.tools.helpers.PathHelper;
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
		DbManager manager = new DbManager("config/db_config.properties");
		
		/*Map<String, Object> mData = new HashMap<String,Object>();
		//mData.put("Id", 4080);
		mData.put("Name", "GuangZhou");
		mData.put("CountryCode", "CHN");
		mData.put("population", 35000000);
		mData.put("District", "GuangZhou");
		
		
		List<Map> data = new ArrayList<Map>();
		//data.add(mData);
		for(int i = 0; i < 2000000; i++) {
			data.add(mData);
		}*/

		long startTime = System.currentTimeMillis();
		String sql = "select * from city where ";
		Map result = manager.getPool().doQuery(manager.getPool().retrieveConnection(),
												sql, 
												new String[]{"name=","Id<="},
												new String[]{"and"},
												new Object[]{"GuangZhou",2000});
		//Iterator eit = result.entrySet().iterator();

		//System.out.println("value of key Rafah: " + result.get("Rafah"));
		
		//LinkedList list = (LinkedList) result.get("Shanghai");
		//System.out.println(list.size());

		//Set keys = result.keySet();
		//Iterator it = keys.iterator();
		/*while(it.hasNext()) {
			//System.out.println(it.next());
			System.out.println("result: " + result.get(it.next()));
		}*/
		
		//Map Data = manager.getPool().doQuery(sql);
		//Iterator it = Data.values().iterator();
		//while(it.hasNext()) {
			//System.out.println("query data: " + it.next());
		//}
		
		long endTime = System.currentTimeMillis();
		manager.getPool().closeConnections();
		System.out.println("how many data returned: " + result.size());
		System.out.println("程序运行时间：" + (endTime - startTime) + "ms");
		//System.out.println("程序运行时间：" + (endTime - startTime) + "ms");
		
		
	}

}
