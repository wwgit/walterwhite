/**   
* @Title: CreateSQL.java 
* @Package store.db.sql.beans 
* @Description: TODO(what to do) 
* @author walterwhite
* @date 2017年1月19日 下午2:04:09 
* @version V1.0   
*/
package store.db.sql.beans.definitions;

import java.util.List;

import store.db.sql.beans.definitions.constraints.Constraint;
import store.db.sql.beans.definitions.constraints.PrimaryKey;


/** 
* @ClassName: CreateTableSQL 
* @Description: TODO(what to do) 
* @author walterwhite
* @date 2017年1月19日 下午3:52:06 
*  
*/
public abstract class CreateTableSQL extends SQLDefinition {

	
	/** 
	* @Fields fieldsTypes : INT,VARCHAR(32),VARCHAR(255),DATE.... 
	*/ 
	private String[] fieldsTypes;	
	
	/** 
	* @Fields isFieldNull : YES,NO,DEFAULT,YES,NO,DEFAULT... 
	*/ 
	private String[] isFieldNull;
	
	/** 
	* @Fields isAutoIncr : YES,NO,DEFAULT,YES,NO,DEFAULT... 
	*/ 
	private String[] isAutoIncr;
	
	/** 
	* @Fields constraints : PRIMARY KEY, FOREIGN KEY 
	*/ 
	private List<Constraint> constraints;
	
	
	public void setSQLKeyword() {
		this.setSQLKeyword("CREATE TABLE");
	}
	
	public CreateTableSQL() {
		this.setSQLKeyword();
	}
	
	public abstract void setAutoIncr(StringBuilder sb);
	
	
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
		if(null == this.constraints || this.constraints.size() < 1) 
			throw new Exception("CREATE TABLE SQL statement creating error\n primary key description missing !");
		
		for(int i = 0; i < this.getUsedFields().length; i++) {
			
			this.getSb().append(this.getUsedFields()[i]);
			this.getSb().append(" ");
			this.getSb().append(this.getFieldsTypes()[i]);
			
//			NULL/NOT NULL DESCRIPTION Handling
			if(this.getIsFieldNull()[i].equalsIgnoreCase("YES")) {
				this.getSb().append(" NULL");
			} else if(this.getIsFieldNull()[i].equalsIgnoreCase("NO")) {
				this.getSb().append(" NOT NULL");
			}
			
//			AUTO INCREMENT DESCRIPTION Handling
			if(this.getIsAutoIncr()[i].equalsIgnoreCase("YES")) {
				this.getSb().append(" ");
				setAutoIncr(this.getSb());
			}
			if(i < this.getUsedFields().length-1) {
				this.getSb().append(",");
			}
			
		}
		
		this.getSb().append(",");
		
//		handling constraints
		if(null == this.constraints || this.constraints.size() < 1) {
			this.getSb().append(this.getTableName().toLowerCase());
			this.getSb().append("_pky_id");
			this.getSb().append(" INT ");
			setAutoIncr(this.getSb());
			this.getSb().append(" PRIMARY KEY");
		}
		for(int i = 0; i < this.constraints.size(); i++) {
			if(this.constraints.get(i) instanceof PrimaryKey) {
				
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

	public String[] getIsAutoIncr() {
		return isAutoIncr;
	}

	public void setIsAutoIncr(String[] isAutoIncr) {
		this.isAutoIncr = isAutoIncr;
	}

	public List<Constraint> getConstraints() {
		return constraints;
	}

	public void setConstraints(List<Constraint> constraints) {
		this.constraints = constraints;
	}

}
