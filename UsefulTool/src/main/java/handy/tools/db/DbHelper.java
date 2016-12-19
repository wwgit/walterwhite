package handy.tools.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.lang.model.element.Element;

public abstract class DbHelper {
	
	public static List doQueryAll(String url, String clazzName, String userName, String password, String sql) throws ClassNotFoundException, SQLException {
			
		if(null == url) {
			throw new SQLException("url is NUll !");
		}
		Class.forName(clazzName);
		Connection conn = null;
		if(null != userName && null != password) {
			conn = DriverManager.getConnection(url, userName, password);
			
		} else {
			conn = DriverManager.getConnection(url);
		}
		
		PreparedStatement statement = conn.prepareStatement(sql);
		List<String> result = new ArrayList<String>();
		
		ResultSet sqlRet = statement.executeQuery(sql);
		
		for(int i = 0; i < sqlRet.getFetchSize(); i++) {
			result.set(i, sqlRet.getString(i));
		}
		
		return result;
		
	}
	
	public static String prepareQuerySql(String queryHeader, String tableName, Map conds) {
		
		String sql = queryHeader + " " + tableName;
		StringBuilder sb = new StringBuilder();
		Set keys = conds.keySet();
		//conds
		String e = null;
		int i = 0;
		Iterator it = keys.iterator();
		while(it.hasNext()) {
			e = (String) it.next();
			//System.out.println(conds.get(e) + " 		count " + i);
			//System.out.println(and_or.get(i) + " 		count " + i);
			System.out.println(conds.get(e));
			//System.out.println(and_or.get(i));
			i++;
		}
		
		return null;
		
	}

}
