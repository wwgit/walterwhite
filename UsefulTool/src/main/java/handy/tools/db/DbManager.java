package handy.tools.db;

import handy.tools.helpers.DbHelper;

public class DbManager {
	
	private DbConfig config;
	private DbPool pool;
	private static DbManager manager = null;
	
	private DbManager(String configPath) {
		
		System.out.println("Start to init db manager !");		
		setConfig(configPath);
		setPool(getConfig());
	}
	
	private DbManager() {	
		System.out.println("Start to init db manager !");
		setConfig("config/db_config.properties");
		setPool(getConfig());
	}
	
	private static synchronized void sysInit(String configPath) {
		if(null == manager) {
			manager = new DbManager(configPath);
		}
	}
	private static synchronized void sysInit() {
		if(null == manager) {
			manager = new DbManager();
		}
	}
	
	public static DbManager getInstance(String configPath) {
		if(null == manager) {
			sysInit(configPath);
		}
		return manager;
	}
	public static DbManager getInstance() {
		if(null == manager) {
			sysInit();
		}
		return manager;
	}
		
	private void setConfig(String configPath) {
		System.out.println("Start to init db config !");
		DbConfig dcfg = new DbConfig();
		dcfg.parseConfigure(configPath);
		setConfig(dcfg);
	}
	
	public DbConfig getConfig() {
		return config;
	}
	private void setConfig(DbConfig config) {
		this.config = config;
	}
	public DbPool getPool() {
		return pool;
	}

	private void setPool(DbConfig config) {		
		this.pool = new DbPool(config);
	}	
	

}
