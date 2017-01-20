/**   
* @Title: UpdateSQL.java 
* @Package store.db.sql.beans 
* @Description: TODO(what to do) 
* @author walterwhite
* @date 2017年1月19日 上午10:31:10 
* @version V1.0   
*/
package store.db.sql.beans.definitions;

/** 
 * @ClassName: UpdateSQL 
 * @Description: TODO(what to do) 
 * @author walterwhite
 * @date 2017年1月19日 上午10:31:10 
 *  
 */
public class UpdateSQL extends SQLDefinition {
	
	public void setSQLKeyword() {
		this.setSQLKeyword("UPDATE");
	}
	
	public UpdateSQL() {
		this.setSQLKeyword();
	}
	
	/* (non-Javadoc)
	 * @see store.db.sql.beans.SQLDefinition#generateUsedFieldsStatment()
	 */
	@Override
	public void generateUsedFieldsStatment() throws Exception {
	
		if(null == this.getUsedFields()) throw new Exception("No fields setted for UPDATE !");
		String[] fields = this.getUsedFields();
		
		for(int i = 0; i < fields.length; i++) {
			this.getSb().append(fields[i]);
			this.getSb().append("=?");
			if(i < fields.length-1) {
				this.getSb().append(",");
			}
		}
		
	}

	/* (non-Javadoc)
	 * @see store.db.sql.beans.SQLDefinition#generateSQLTail()
	 */
	@Override
	public void generateSQLTail() {
		
	}

	/* (non-Javadoc)
	 * @see store.db.sql.beans.SQLDefinition#generateSQLHeader()
	 */
	@Override
	public void generateSQLHeader() {
		
		this.getSb().append(this.getSQLKeyword());
		this.getSb().append(" ");
		if(null != this.getDbName()) {
			this.getSb().append(this.getDbName());
			this.getSb().append(".");
		}
		
		this.getSb().append(this.getTableName());
		this.getSb().append(" SET");
		
	}
	
	public String generateSimpleUpdate(Object[] updateValues) throws Exception {
		
		String[] fields = this.getUsedFields();
		if(fields.length != updateValues.length) 
			throw new Exception("update values num does Not match field num !");
		
		if(this.getSb().length() > 0 ) this.getSb().delete(0, this.getSb().length());
		this.generateSQLHeader();
		this.getSb().append(" ");
		
		for(int i = 0; i < updateValues.length; i++) {
			this.getSb().append(fields[i]);
			this.getSb().append("=");
			this.getSb().append(updateValues[i]);
			if(i < fields.length-1) {
				this.getSb().append(",");
			}
		}
		if(null != this.getWhereConditions()) {
			this.getSb().append(" ");
			this.getWhereConditions().generateSimpleWhere(this.getSb());
		}		
		return this.getSb().toString();
		
	}

}
