package handy.tools.db;

import handy.tools.helpers.DbHelper;

public class DbManager {
	
	private DbConfig config;
	private DbPool pool;
	
	public DbManager(String configPath) {
		
		System.out.println("Start to init db manager !");		
		setConfig(configPath);
		setPool(getConfig());
	}
	
	public DbManager() {
		
	}
	
	public void setConfig(String configPath) {
		System.out.println("Start to init db config !");
		DbConfig dcfg = new DbConfig();
		dcfg.parseConfigure(configPath);
		setConfig(dcfg);
	}
	
	public DbConfig getConfig() {
		return config;
	}
	public void setConfig(DbConfig config) {
		this.config = config;
	}
	public DbPool getPool() {
		return pool;
	}
	public void setPool(DbPool pool) {
		this.pool = pool;
	}
	public void setPool(DbConfig config) {		
		this.pool = new DbPool(config);
	}
	
	

}
