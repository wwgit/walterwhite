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
	public void generateUsedFieldsStatment() {	
		
		if(null != this.getUsedFields()) {
			this.getSb().append(this.getUsedFields());	
		} else {
			this.getSb().append("*");
		}
	}

	/* (non-Javadoc)
	 * @see store.db.sql.beans.SQLDefinition#generateSQLTail()
	 */
	@Override
	public void generateSQLTail() {

		this.getSb().append("from ");
		if(null != this.getDbName()) {
			this.getSb().append(this.getDbName());
			this.getSb().append(".");
		}
		this.getSb().append(this.getTableName());
	}


	/* (non-Javadoc)
	 * @see store.db.sql.beans.SQLDefinition#generateSQLHeader()
	 */
	@Override
	public void generateSQLHeader() {
		this.getSb().append(this.getSQLKeyword());
	}

}
