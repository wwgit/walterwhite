/**   
* @Title: SelectSQL.java 
* @Package store.db.sql.beans 
* @Description: TODO(what to do) 
* @author walterwhite
* @date 2017年1月17日 下午12:54:41 
* @version V1.0   
*/
package store.db.sql.beans;

/** 
 * @ClassName: SelectSQL 
 * @Description: TODO(what to do) 
 * @author walterwhite
 * @date 2017年1月17日 下午12:54:41 
 *  
 */
public class SelectSQL extends SQLDefinition {

	
	public void setSQLKeyword() {
		this.setSQLKeyword("SELECT");
	}
	
	public SelectSQL() {
		this.setSQLKeyword();
	}
	
	
	/* (non-Javadoc)
	 * @see store.db.sql.beans.SQLDefinition#generateUsedFieldsStatment()
	 */
	@Override
	public String generateUsedFieldsStatment() {		
		return null != this.getUsedFields() ? this.getUsedFields() : "*";
	}

	/* (non-Javadoc)
	 * @see store.db.sql.beans.SQLDefinition#generateSQLTail()
	 */
	@Override
	public String generateSQLTail() {
		StringBuilder sb = new StringBuilder();
		sb.append("from ");
		if(null != this.getDbName()) {
			sb.append(this.getDbName());
			sb.append(".");
		}
		sb.append(this.getTableName());
		return sb.toString();
	}


	/* (non-Javadoc)
	 * @see store.db.sql.beans.SQLDefinition#generateSQLHeader()
	 */
	@Override
	public String generateSQLHeader() {
		
		return this.getSQLKeyword();
	}

}
