package handy.tools.db;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import handy.tools.interfaces.ConfigureHelper;

public class DbConfig extends ConfigureHelper {

	private String url;
	private String dbDriver;
	private String userName;
	private String password;
	private int dbSize;
	
	public DbConfig(String dbUrl, String driver, String user, String pwd, int size) {
		setUrl(dbUrl);
		setDbDriver(driver);
		setUserName(user);
		setPassword(pwd);
		setDbSize(size);
	}
	
	public DbConfig() {
		System.out.println(" new dbConfig object !");
	}
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		System.out.println("calling setUrl " + url);
		this.url = url;
	}

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		System.out.println("calling setUserName " + userName);
		this.userName = userName;
	}
	public String getPassword() {
		
		return password;
	}
	public void setPassword(String password) {
		System.out.println("calling setPassword " + password);
		this.password = password;
	}

	public String getDbDriver() {
		return dbDriver;
	}

	public void setDbDriver(String dbDriver) {
		System.out.println("calling setDbDriver " + dbDriver);
		this.dbDriver = dbDriver;
	}

	public int getDbSize() {
		return dbSize;
	}

	public void setDbSize(int dbSize) {
		System.out.println("calling setDbSize " + dbSize);
		this.dbSize = dbSize;
	}

	@Override
	public void parseConfigure(String configPath, Object configObj) {
		// TODO Auto-generated method stub
		parseDBConfig(configPath, configObj);
	}
	
	private void parseDBConfig(String configPath, Object configObj) {
		
		int flag = parseFileSuffix(configPath);
		
		switch(flag) {
			case CONFIG_SUFFIX_PROPERTY:
			try {
				System.out.println("start to parse properties\n" + configPath);
				parsePropertyConf(configPath, configObj);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				break;
			case CONFIG_SUFFIX_XML:
				parseXmlConf(configPath);
				break;
			case CONFIG_SUFFIX_JSON:
				parseJsonConf(configPath);
				break;
			default:
				break;
		}
	}
	
	private void parseXmlConf(String configPath) {
		
	}
	private void parseJsonConf(String configPath) {
		
	}
	
	
}
