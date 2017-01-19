/**   
* @Title: SqlFieldsDesc.java 
* @Package store.db.sql.interfaces 
* @Description: TODO(what to do) 
* @author walterwhite
* @date 2017年1月19日 下午3:29:00 
* @version V1.0   
*/
package store.db.sql.interfaces;

/** 
 * @ClassName: SqlFieldsDesc 
 * @Description: TODO(what to do) 
 * @author walterwhite
 * @date 2017年1月19日 下午3:29:00 
 *  
 */
public interface ISQLFieldsDesc {
	
	public void setPrimaryKey(StringBuilder sb);
	
	public void setTablePrimaryKey(StringBuilder sb, String[] tableFields);
	
	public void setTablePrimaryKey(StringBuilder sb, String[] tableFields, String keyName);
	
	public void setTableForeignKey(StringBuilder sb, String tableName, String[] tableFields, String[] foreignTablefields);
	
	public void setTableForeignKey(StringBuilder sb, String tableName, String[] tableFields,
									String[] foreignTablefields, String keyName);
	
	public void setNull(StringBuilder sb);
	
	public void setNotNull(StringBuilder sb);
	
	public void setNullAsDefault(StringBuilder sb);
	
	public void setAutoIncr(StringBuilder sb);
	

}
