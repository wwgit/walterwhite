/**   
* @Title: InsertSQL.java 
* @Package store.db.sql.beans 
* @Description: TODO(what to do) 
* @author walterwhite
* @date 2017年1月18日 下午6:11:11 
* @version V1.0   
*/
package store.db.sql.beans;

/** 
 * @ClassName: InsertSQL 
 * @Description: TODO(what to do) 
 * @author walterwhite
 * @date 2017年1月18日 下午6:11:11 
 *  
 */
public class InsertSQL extends SQLDefinition {

	public void setSQLKeyword() {
		this.setSQLKeyword("INSERT");
	}
	
	public InsertSQL() {
		this.setSQLKeyword();
	}
	
	
	/* (non-Javadoc)
	 * @see store.db.sql.beans.SQLDefinition#generateUsedFieldsStatment()
	 */
	@Override
	public String generateUsedFieldsStatment() {
		
		return null;
	}

	/* (non-Javadoc)
	 * @see store.db.sql.beans.SQLDefinition#generateSQLTail()
	 */
	@Override
	public String generateSQLTail() {
		
		return null;
	}

	/* (non-Javadoc)
	 * @see store.db.sql.beans.SQLDefinition#generateSimpleSQL()
	 */
	@Override
	public String generateSimpleSQL() {
		
		return null;
	}

	/* (non-Javadoc)
	 * @see store.db.sql.beans.SQLDefinition#generateSQLHeader()
	 */
	@Override
	public String generateSQLHeader() {
		
		StringBuilder sb = new StringBuilder();
		sb.append(this.getSQLKeyword());
		sb.append(" INTO ");
		if(null != this.getDbName()) {
			sb.append(this.getDbName());
			sb.append(".");
		}
		sb.append(this.getTableName());
		
		return sb.toString();
	}

}
