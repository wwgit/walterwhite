package store.db.sql.beans;

import store.annotations.ForeignKeyAnno;
import store.annotations.Table;
import store.annotations.TableField;

@Table(tableName = "db_config",dbName="testperf")
@ForeignKeyAnno(foreignKeyFields = "pool_size", refTableFields = "testtable_pky_id",
				refTableName = "testTable", keyName = "testTable_fk")
public class DbConfig {

	@TableField(fieldName = "url", fieldType = String.class)
	private String url;
	
	@TableField(fieldName = "db_driver", fieldType = String.class)
	private String dbDriver;
	
	@TableField(fieldName = "user_name", fieldType = String.class)
	private String userName;
	
	@TableField(fieldName = "password", fieldType = String.class, fieldLength = 255)
	private String password;
	
	@TableField(fieldName = "pool_size", fieldType = int.class, fieldLength = 10)
	private int poolSize;
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getDbDriver() {
		return dbDriver;
	}
	public void setDbDriver(String dbDriver) {
		this.dbDriver = dbDriver;
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
	public int getPoolSize() {
		return poolSize;
	}
	public void setPoolSize(int poolSize) {
		this.poolSize = poolSize;
	}
}
