package store.db.sql.beans;

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
	* @Fields SQLKeyword expected to support: SELECT,INSERT,UPDATE,DELETE,RIGHTJOIN,LEFTJOIN,CREATE  
	*/ 
	private String SQLKeyword;
	
	private String dbName;
	
	private String tableName;
	
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
	private String usedFields;
	
	private List<Object[]> fieldsValues;
	
	private WhereDefinition whereConditions;
	
	public abstract String generateUsedFieldsStatment();
	public abstract String generateSQLTail();
	public abstract String generateSimpleSQL();
	
	/** 
	* @Title: generateSQLHeader 
	* @Description: TODO(what to do)
	* UPDATE dbName.tableName SET
	* DELETE
	* SELECT
	* INSERT INTO dbName.tableName
	* INSERT INTO dbName.tableName VALUES
	* CREATE TABLE dbName.tableName ( 
	* @param @return  
	* @return String   
	* @throws 
	*/
	public abstract String generateSQLHeader();
//	public abstract String generateSQLStatment();
	
	/** 
	* @Title: generateSQLStatment 
	* @Description: TODO(what to do) 
	* @param @return  
	* @return String   
	* @throws 
	*/
	public String generatePrepareSQLStatment() {
		
		StringBuilder sb = new StringBuilder();
		
		sb.append(this.generateSQLHeader());	
		sb.append(" ");
		sb.append(this.generateUsedFieldsStatment());
		if(false == this.getSQLKeyword().equalsIgnoreCase("UPDATE")) {
			sb.append(" ");
			sb.append(this.generateSQLTail());
		}
		if(null != this.getWhereConditions()) {
			sb.append(" ");
			sb.append(this.getWhereConditions().generateWhereConditions());
		}	
		
		return sb.toString();
	}
	
	
	public String generateSimpleSQL(Object[] whereValues) throws Exception {
		
		StringBuilder sb = new StringBuilder();
		
		sb.append(this.generateSQLHeader());
		sb.append(" ");
		sb.append(this.generateUsedFieldsStatment());

		if(false == this.getSQLKeyword().equalsIgnoreCase("UPDATE")) {
			sb.append(" ");
			sb.append(this.generateSQLTail());
		}
		if(null != this.getWhereConditions()) {
			sb.append(" ");
			sb.append(this.getWhereConditions().generateSimpleWhere(whereValues));
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

	public String getUsedFields() {
		return usedFields;
	}

	public void setUsedFields(String usedFields) {
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

	
	

}
