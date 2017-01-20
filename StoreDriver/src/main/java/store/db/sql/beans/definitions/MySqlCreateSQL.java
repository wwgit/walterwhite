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
		this.getSb().append(")");
		
	}

}
