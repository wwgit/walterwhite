package handy.tools.db;

public class DbManager {
	
	private DbConfig config;
	private DbPool pool;
	private static DbManager manager = null;
	
	private DbManager(DbConfig config) {
		
		System.out.println("Start to init db manager !");		
		setConfig(config);
		setPool(getConfig());
	}
	
	private DbManager() {	
		System.out.println("Start to init db manager without init DbConfig and db pool!");
		//setConfig(DEFAULT_DB_CONFIG);
		//setPool(getConfig());
	}
	
	private static synchronized void sysInit(DbConfig config) {
		if(null == manager) {
			manager = new DbManager(config);
		}
	}
	private static synchronized void sysInit() {
		if(null == manager) {
			manager = new DbManager();
		}
	}
	
	public static DbManager getInstance(DbConfig config) {
		if(null == manager) {
			sysInit(config);
		}
		return manager;
	}
	public static DbManager getInstance() {
		if(null == manager) {
			sysInit();
		}
		return manager;
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
	
	public Object readResolve() {
		return manager;
	}

}
