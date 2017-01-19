/**   
* @Title: CreateSQL.java 
* @Package store.db.sql.beans 
* @Description: TODO(what to do) 
* @author walterwhite
* @date 2017年1月19日 下午2:04:09 
* @version V1.0   
*/
package store.db.sql.beans;

import java.util.List;

import store.db.sql.interfaces.SQLFieldsDesc;


/** 
* @ClassName: CreateTableSQL 
* @Description: TODO(what to do) 
* @author walterwhite
* @date 2017年1月19日 下午3:52:06 
*  
*/
public class CreateTableSQL extends SQLDefinition {

	
	/** 
	* @Fields fieldsTypes : INT,VARCHAR(32),VARCHAR(255),DATE.... 
	*/ 
	private String[] fieldsTypes;	
	
	/** 
	* @Fields isFieldNull : YES,NO,DEFAULT,YES,NO,DEFAULT... 
	*/ 
	private String[] isFieldNull;
	
	private String primaryKeyName;
	
	/** 
	* @Fields primaryFields : primaryField1,primaryField2.... 
	*/ 
	private String[] primaryFields;
	
	private String[] foreignKeyNames;
	
	/** 
	* @Fields foreignFields : (foreignField1,foreignField2),(foreignField3,foreignField4).... 
	*/ 
	private List<String[]> foreignFields;
	
	private List<String[]> foreignRefTables;
	
	private List<String[]> refTableFields;
	
	/** 
	* @Fields isAutoIncr : YES,NO,DEFAULT,YES,NO,DEFAULT... 
	*/ 
	private String[] isAutoIncr;
	
	private SQLFieldsDesc sqlFieldsDesc;
	
	
	public void setSQLKeyword() {
		this.setSQLKeyword("CREATE TABLE");
	}
	
	public CreateTableSQL() {
		this.setSQLKeyword();
	}
	
	
	/* (non-Javadoc)
	 * @see store.db.sql.beans.SQLDefinition#generateUsedFieldsStatment()
	 */
	@Override
	protected void generateUsedFieldsStatment() throws Exception {
		
		if(null == this.getUsedFields() || this.getUsedFields().length < 1)
			throw new Exception("CREATE TABLE SQL statement creating error\n at least table should have one field !");
		
		if(this.getUsedFields().length != this.getFieldsTypes().length) 
			throw new Exception("CREATE TABLE SQL statement creating error\n some field type missing !");
		if(this.getUsedFields().length != this.getIsAutoIncr().length) 
			throw new Exception("CREATE TABLE SQL statement creating error\n some field description missing !");
		if(this.getUsedFields().length != this.getIsFieldNull().length) 
			throw new Exception("CREATE TABLE SQL statement creating error\n some field description missing !");
		
		for(int i = 0; i < this.getUsedFields().length; i++) {
			
			this.getSb().append(this.getUsedFields()[i]);
			this.getSb().append(" ");
			this.getSb().append(this.getFieldsTypes()[i]);
			
//			NULL/NOT NULL DESCRIPTION Handling
			if(this.getIsFieldNull()[i].equalsIgnoreCase("DEFAULT")) {
				this.getSqlFieldsDesc().setNullAsDefault(this.getSb());
			} else if(this.getIsFieldNull()[i].equalsIgnoreCase("YES")) {
				this.getSb().append(" ");
				this.getSqlFieldsDesc().setNull(this.getSb());
			} else if(this.getIsFieldNull()[i].equalsIgnoreCase("NO")) {
				this.getSb().append(" ");
				this.getSqlFieldsDesc().setNotNull(this.getSb());
			} else {
				this.getSqlFieldsDesc().setNullAsDefault(this.getSb());
			}
			
//			AUTO INCREMENT DESCRIPTION Handling
			if(this.getIsAutoIncr()[i].equalsIgnoreCase("YES")) {
				this.getSb().append(" ");
				this.getSqlFieldsDesc().setAutoIncr(this.getSb());
			} 
			if(i < this.getUsedFields().length-1) {
				this.getSb().append(",");
			}
			
		}
		
		this.getSb().append(",");
		if(null == this.getPrimaryFields() || this.getPrimaryFields().length < 1) {
			
			this.getSb().append("primary_id INT ");
			this.getSqlFieldsDesc().setAutoIncr(this.getSb());
			this.getSb().append(" ");
			this.getSqlFieldsDesc().setPrimaryKey(this.getSb());
		}
		if(this.primaryKeyName == null) {
			this.getSqlFieldsDesc().setTablePrimaryKey(this.getSb(), this.getPrimaryFields());
		} else {
			this.getSqlFieldsDesc().setTablePrimaryKey(this.getSb(), this.getPrimaryFields(),this.primaryKeyName);
		}
		
		if(this.foreignFields.size() >= 1) {
			if(this.foreignFields.size() != this.foreignRefTables.size()) 
				throw new Exception("CREATE TABLE SQL statement creating error\n some constraints description missing !");
			if(this.refTableFields.size() != this.foreignRefTables.size()) 
				throw new Exception("CREATE TABLE SQL statement creating error\n some constraints description missing !");
			
			this.getSb().append(",");
			
			for(int i = 0; i < this.foreignFields.size(); i++) {
				
			}
		}
		
	}

	/* (non-Javadoc)
	 * @see store.db.sql.beans.SQLDefinition#generateSQLTail()
	 */
	@Override
	protected void generateSQLTail() {
	}

	/* (non-Javadoc)
	 * @see store.db.sql.beans.SQLDefinition#generateSQLHeader()
	 */
	@Override
	protected void generateSQLHeader() {
		
		this.getSb().append(this.getSQLKeyword());
		this.getSb().append(" ");
		this.getSb().append("IF NOT EXISTS ");
		if(null != this.getDbName()) {
			this.getSb().append(this.getDbName());
			this.getSb().append(".");
		} 
		this.getSb().append(this.getTableName());
		this.getSb().append(" (");
		
	}
	
	public String generateCreateTableSQL() throws Exception {
		
		if(this.getSb().length() > 0) this.getSb().delete(0, this.getSb().length());
		
		generateSQLHeader();
		generateUsedFieldsStatment();
		generateSQLTail();
		
		return this.getSb().toString();
	}

	public String[] getFieldsTypes() {
		return fieldsTypes;
	}

	public void setFieldsTypes(String[] fieldsTypes) {
		this.fieldsTypes = fieldsTypes;
	}

	public String[] getIsFieldNull() {
		return isFieldNull;
	}

	public void setIsFieldNull(String[] isFieldNull) {
		this.isFieldNull = isFieldNull;
	}

	public SQLFieldsDesc getSqlFieldsDesc() {
		return sqlFieldsDesc;
	}

	public void setSqlFieldsDesc(SQLFieldsDesc sqlFieldsDesc) {
		this.sqlFieldsDesc = sqlFieldsDesc;
	}

	public String[] getPrimaryFields() {
		return primaryFields;
	}

	public void setPrimaryFields(String[] primaryFields) {
		this.primaryFields = primaryFields;
	}

	public String[] getIsAutoIncr() {
		return isAutoIncr;
	}

	public void setIsAutoIncr(String[] isAutoIncr) {
		this.isAutoIncr = isAutoIncr;
	}

	public String getPrimaryKeyName() {
		return primaryKeyName;
	}

	public void setPrimaryKeyName(String primaryKeyName) {
		this.primaryKeyName = primaryKeyName;
	}

	public String[] getForeignKeyNames() {
		return foreignKeyNames;
	}

	public void setForeignKeyNames(String[] foreignKeyNames) {
		this.foreignKeyNames = foreignKeyNames;
	}

	public List<String[]> getForeignFields() {
		return foreignFields;
	}

	public void setForeignFields(List<String[]> foreignFields) {
		this.foreignFields = foreignFields;
	}

	public List<String[]> getForeignRefTables() {
		return foreignRefTables;
	}

	public void setForeignRefTables(List<String[]> foreignRefTables) {
		this.foreignRefTables = foreignRefTables;
	}

	public List<String[]> getRefTableFields() {
		return refTableFields;
	}

	public void setRefTableFields(List<String[]> refTableFields) {
		this.refTableFields = refTableFields;
	}

}
