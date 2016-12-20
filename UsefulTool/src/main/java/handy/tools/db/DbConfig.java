package handy.tools.db;

public class DbConfig {

	private String url;
	private String dbClazz;
	private String userName;
	private String password;
	
	public DbConfig(String dbUrl, String clazz, String user, String pwd) {
		setUrl(dbUrl);
		setDbClazz(clazz);
		setUserName(user);
		setPassword(pwd);
	}
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getDbClazz() {
		return dbClazz;
	}
	public void setDbClazz(String dbClazz) {
		this.dbClazz = dbClazz;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
