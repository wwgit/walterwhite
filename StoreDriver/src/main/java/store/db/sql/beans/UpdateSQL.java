/**   
* @Title: UpdateSQL.java 
* @Package store.db.sql.beans 
* @Description: TODO(what to do) 
* @author walterwhite
* @date 2017年1月19日 上午10:31:10 
* @version V1.0   
*/
package store.db.sql.beans;

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
	public String generateUsedFieldsStatment() throws Exception {
		if(null == this.getUsedFields()) throw new Exception("No fields setted for UPDATE !");
		StringBuilder sb = new StringBuilder();
		String[] fields = this.getUsedFields().split(",");
		
		for(int i = 0; i < fields.length; i++) {
			sb.append(fields[i]);
			sb.append("=?");
			if(i < fields.length-1) {
				sb.append(",");
			}
		}
		
		return sb.toString();
	}

	/* (non-Javadoc)
	 * @see store.db.sql.beans.SQLDefinition#generateSQLTail()
	 */
	@Override
	public String generateSQLTail() {
		
		return null;
	}

	/* (non-Javadoc)
	 * @see store.db.sql.beans.SQLDefinition#generateSQLHeader()
	 */
	@Override
	public String generateSQLHeader() {
		
		StringBuilder sb = new StringBuilder();
		sb.append(this.getSQLKeyword());
		sb.append(" ");
		if(null != this.getDbName()) {
			sb.append(this.getDbName());
			sb.append(".");
		}
		
		sb.append(this.getTableName());
		sb.append(" SET");
		
		return sb.toString();
	
	}
	
	public String generateSimpleUpdate(Object[] updateValues, Object[] whereValues) throws Exception {
		
		String[] fields = this.getUsedFields().split(",");
		if(fields.length != updateValues.length) throw new Exception("update values num does Not match field num !");
		
		StringBuilder sb = new StringBuilder();
		sb.append(this.generateSQLHeader());
		sb.append(" ");
		
		for(int i = 0; i < updateValues.length; i++) {
			sb.append(fields[i]);
			sb.append("=");
			sb.append(updateValues[i]);
			if(i < fields.length-1) {
				sb.append(",");
			}
		}
		if(null != this.getWhereConditions()) {
			sb.append(" ");
			sb.append(this.getWhereConditions().generateSimpleWhere(whereValues));
		}		
		return sb.toString();
		
	}

}
