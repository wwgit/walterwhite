package handy.tools.db;

import handy.tools.interfaces.DbHelper;

public class DbManager {
	
	private DbConfig config;
	private DbPool pool;
	
	public DbManager(String configPath) {
		setConfig(DbHelper.parseConfigFrmProperties(configPath));
		setPool(new DbPool(getConfig()));
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
