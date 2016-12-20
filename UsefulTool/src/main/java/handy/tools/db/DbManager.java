package handy.tools.db;

import handy.tools.interfaces.DbHelper;

public class DbManager {
	
	private DbConfig config;
	private DbPool pool;
	
	public DbManager(String configPath) {
		DbConfig dcfg = new DbConfig();
		System.out.println("Start to init db config !");
		dcfg.parseConfigure(configPath,dcfg);
		//setConfig(DbHelper.parseConfigFrmProperties(configPath));
		setConfig(dcfg);
		//setPool(new DbPool(getConfig()));
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

}
