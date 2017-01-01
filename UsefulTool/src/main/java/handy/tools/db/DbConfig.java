package handy.tools.db;


public class DbConfig {

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
		//System.out.println(" new dbConfig object !");
		//System.out.println("ref_local_null".hashCode());
	}
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		//System.out.println("calling setUrl " + url);
		this.url = url;
	}

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		//System.out.println("calling setUserName " + userName);
		this.userName = userName;
	}
	public String getPassword() {
		
		return password;
	}
	public void setPassword(String password) {
		//System.out.println("calling setPassword " + password);
		this.password = password;
	}

	public String getDbDriver() {
		return dbDriver;
	}

	public void setDbDriver(String dbDriver) {
		//System.out.println("calling setDbDriver " + dbDriver);
		this.dbDriver = dbDriver;
	}

	public int getDbSize() {
		return dbSize;
	}

	public void setDbSize(int dbSize) {
		//System.out.println("calling setDbSize " + dbSize);
		this.dbSize = dbSize;
	}	
	
}
