/**   
* @Title: MySqlCreateSQL.java 
* @Package store.db.sql.beans.definitions 
* @Description: TODO(what to do) 
* @author walterwhite
* @date 2017年1月20日 上午11:11:45 
* @version V1.0   
*/
package store.db.sql.beans.definitions;

/** 
 * @ClassName: MySqlCreateSQL 
 * @Description: TODO(what to do) 
 * @author walterwhite
 * @date 2017年1月20日 上午11:11:45 
 *  
 */
public class MySqlCreateSQL extends CreateTableSQL {

	/** 
	* @Fields mySqlEngine : MyISAM InnnoDB 
	*/ 
	private String mySqlEngine;
	
	/** 
	* @Fields format :  utf8,gbk
	*/ 
	private String format;
	
	private int autoIncrValue;
	
	public MySqlCreateSQL() {
		
		mySqlEngine = "InnoDB";
		format = "utf8";
		autoIncrValue = 1;
	}
	
	/* (non-Javadoc)
	 * @see store.db.sql.beans.definitions.CreateTableSQL#setAutoIncr(java.lang.StringBuilder)
	 */
	@Override
	public void setAutoIncr(StringBuilder sb) {
		sb.append("AUTO_INCREMENT");
	}

	/* (non-Javadoc)
	 * @see store.db.sql.beans.definitions.SQLDefinition#generateSQLTail()
	 */
	@Override
	protected void generateSQLTail() {
		
		this.getSb().append(")ENGINE=");
		this.getSb().append(this.mySqlEngine);
		this.getSb().append(" DEFAULT CHARSET=");
		this.getSb().append(this.format);
		this.getSb().append(" AUTO_INCREMENT=");
		this.getSb().append(this.autoIncrValue);
		
	}

	public String getMySqlEngine() {
		return mySqlEngine;
	}

	public void setMySqlEngine(String mySqlEngine) {
		this.mySqlEngine = mySqlEngine;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public int getAutoIncrValue() {
		return autoIncrValue;
	}

	public void setAutoIncrValue(int autoIncrValue) {
		this.autoIncrValue = autoIncrValue;
	}

}
