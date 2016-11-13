package handy.tools.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class DbHelper {
	
	public static Connection getConn(String url, String userName, String password) throws ClassNotFoundException, SQLException {
		
		Class.forName(url);
		if(null == url) {
			throw new SQLException("url is NUll !");
		}
		if(null != userName && null != password) {
			return DriverManager.getConnection(url, userName, password);
		} else {
			return DriverManager.getConnection(url);
		}		
		
	}

}
