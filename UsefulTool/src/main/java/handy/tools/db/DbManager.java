package handy.tools.db;

import handy.tools.interfaces.PathHelper;

import java.io.IOException;
import java.util.Properties;

public class DbManager {
	
	private DbConfig config;
	private DbPool pool;
	
	public DbManager(String configPath) {
		Properties prop = new Properties();
		try {
			System.out.println("Init DB Manager !");
			prop.load(PathHelper.resolveAbsoluteStream(configPath));
			setConfig(new DbConfig(prop.getProperty("db.url"),prop.getProperty("db.url"),
					prop.getProperty("db.username"),prop.getProperty("db.password")));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
