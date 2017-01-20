package store.db.sql.beans.definitions;

import java.util.List;

/** 
* @ClassName: SQLDefinition 
* @Description: TODO(what to do) 
* @author walterwhite
* @date 2017年1月17日 上午11:00:36 
*  
*/
public abstract class SQLDefinition {
	
	
	/** 
	* @Fields SQLKeyword expected to support: SELECT,INSERT,UPDATE,DELETE,RIGHT JOIN,LEFT JOIN,CREATE TABLE  
	*/ 
	private String SQLKeyword;
	
	private String dbName;
	
	private String tableName;
	
	private StringBuilder sb;
	
	/** 
	* @Fields usedFields :  
	* e.g field-a,field-b,field-c,field-d
	* for select statement: SELECT field-a,field-b,field-c,field-d from dbName.tableName
	* by default: usedFields = *  e.g  SELECT * from dbName.tableName
	* for update statement: UPDATE dbName.tableName set field-a=?,field-b=?,field-c=?,field-d=?
	* by default:usedFields cannot be NULL
	* for insert statement: INSERT INTO dbName.tableName (field-a,field-b,field-c,field-d) values (?,?,?,?)
	* by default: usedFields = null e.g INSERT INTO dbName.tableName values (?,?,?,?...)
	*/ 
	private String[] usedFields;
	
	private List<Object[]> fieldsValues;
	
	private WhereDefinition whereConditions;	

	/** 
	* @Title: generateUsedFieldsStatment 
	* @Description: TODO(what to do) 
	* @param @throws Exception  
	* @return void   
	* @throws 
	*/
	protected abstract void generateUsedFieldsStatment() throws Exception;
	protected abstract void generateSQLTail();
	
	public SQLDefinition() {
		this.setSb(new StringBuilder());
	}
	
	/** 
	* @Title: generateSQLHeader 
	* @Description: TODO(what to do)
	* UPDATE dbName.tableName SET
	* DELETE
	* SELECT
	* INSERT INTO dbName.tableName
	* INSERT INTO dbName.tableName VALUES
	* CREATE TABLE IF NOT EXISTS dbName.tableName ( 
	* @param @return  
	* @return void   
	* @throws 
	*/
	protected abstract void generateSQLHeader();
	
	/**
	 * @throws Exception  
	* @Title: generateSQLStatment 
	* @Description: TODO(what to do) 
	* @param @return  
	* @return String   
	* @throws 
	*/

	public String generatePrepareSQLStatment() throws Exception {
		
//		initialize string builder first
		if(sb.length() > 0 ) sb.delete(0, sb.length());
		
		this.generateSQLHeader();
		sb.append(" ");
		this.generateUsedFieldsStatment();
		if(false == this.getSQLKeyword().equalsIgnoreCase("UPDATE")) {
			this.generateSQLTail();
		}
		if(null != this.getWhereConditions()) {
			sb.append(" ");
			this.getWhereConditions().generateWhereConditions(sb);
		}	
		
		return sb.toString();
	}
	
	
	public String generateSimpleSQL(Object[] whereValues) throws Exception {
		
//		initialize string builder first
		if(sb.length() > 0 ) sb.delete(0, sb.length());
		this.generateSQLHeader();
		sb.append(" ");
		this.generateUsedFieldsStatment();

		if(false == this.getSQLKeyword().equalsIgnoreCase("UPDATE")) {
			this.generateSQLTail();
		}
		if(null != this.getWhereConditions()) {
			sb.append(" ");
			this.getWhereConditions().generateSimpleWhere(sb, whereValues);
		}	
		
		return sb.toString();
	}

	public String getSQLKeyword() {
		return SQLKeyword;
	}

	public void setSQLKeyword(String sQLKeyword) {
		SQLKeyword = sQLKeyword;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String[] getUsedFields() {
		return usedFields;
	}

	public void setUsedFields(String[] usedFields) {
		this.usedFields = usedFields;
	}

	public WhereDefinition getWhereConditions() {
		return whereConditions;
	}

	public void setWhereConditions(WhereDefinition whereConditions) {
		this.whereConditions = whereConditions;
	}

	public List<Object[]> getFieldsValues() {
		return fieldsValues;
	}

	public void setFieldsValues(List<Object[]> fieldsValues) {
		this.fieldsValues = fieldsValues;
	}
	public StringBuilder getSb() {
		return sb;
	}
	public void setSb(StringBuilder sb) {
		this.sb = sb;
	}
	

}
