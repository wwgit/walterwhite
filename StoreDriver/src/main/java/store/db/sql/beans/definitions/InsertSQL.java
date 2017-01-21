/**   
* @Title: InsertSQL.java 
* @Package store.db.sql.beans 
* @Description: TODO(what to do) 
* @author walterwhite
* @date 2017年1月18日 下午6:11:11 
* @version V1.0   
*/
package store.db.sql.beans.definitions;

import java.util.List;

/** 
 * @ClassName: InsertSQL 
 * @Description: TODO(what to do) 
 * @author walterwhite
 * @date 2017年1月18日 下午6:11:11 
 *  
 */
public class InsertSQL extends SQLDefinition {

	private int howManyFields;	
	
	private List<Object[]> fieldsValues;
	
	private void setSQLKeyword() {
		this.setSQLKeyword("INSERT");
	}
	
	public InsertSQL() {
		this.setSQLKeyword();
	}
		
	/* (non-Javadoc)
	 * @see store.db.sql.beans.SQLDefinition#generateUsedFieldsStatment()
	 */
	@Override
	protected void generateUsedFieldsStatment() {

		if(null != this.getUsedFields() && this.getUsedFields().length >= 1) {
			this.getSb().append("(");
			for(int i = 0; i < this.getUsedFields().length; i++) {
				this.getSb().append(this.getUsedFields()[i]);
				if(i < this.getUsedFields().length - 1) {
					this.getSb().append(",");
				}
			}
			this.getSb().append(") VALUES");
		} else {
			this.getSb().append("VALUES");
		}
		this.getSb().append(" ");
	}

	/* (non-Javadoc)
	 * @see store.db.sql.beans.SQLDefinition#generateSQLTail()
	 */
	@Override
	protected void generateSQLTail() {
		
		int fieldsCnt = 0;
		if(null != this.getUsedFields()) {
			fieldsCnt = this.getUsedFields().length;
		} else {
			fieldsCnt = howManyFields;
		}
		
		this.getSb().append("(");
		for(int i = 0; i < fieldsCnt; i++) {
			this.getSb().append("?");
			if(i < fieldsCnt-1) {
				this.getSb().append(",");
			}
		}
		this.getSb().append(")");
	}
	
	private void generateSimpleTail(Object[] values) {
		
		int fieldsCnt = 0;
		if(null != this.getUsedFields()) {
			fieldsCnt = this.getUsedFields().length;
		} else {
			fieldsCnt = howManyFields;
		}
		
		this.getSb().append("(");
		for(int i = 0; i < fieldsCnt; i++) {
			this.getSb().append(values[i]);
			if(i < fieldsCnt-1) {
				this.getSb().append(",");
			}
		}
		this.getSb().append(")");
		
	}

	/* (non-Javadoc)
	 * @see store.db.sql.beans.SQLDefinition#generateSimpleSQL()
	 */
	public String generateSimpleSQL(Object[] values) {
		
		if(this.getSb().length() > 0 ) this.getSb().delete(0, this.getSb().length());
		
		this.generateSQLHeader();
		this.getSb().append(" ");
		this.generateUsedFieldsStatment();
		generateSimpleTail(values);
		
		return this.getSb().toString();
	}

	/* (non-Javadoc)
	 * @see store.db.sql.beans.SQLDefinition#generateSQLHeader()
	 */
	@Override
	public void generateSQLHeader() {
		
		this.getSb().append(this.getSQLKeyword());
		this.getSb().append(" INTO ");
		if(null != this.getDbName()) {
			this.getSb().append(this.getDbName());
			this.getSb().append(".");
		}
		this.getSb().append(this.getTableName());
		
	}

	public int getHowManyFields() {
		return howManyFields;
	}

	public void setHowManyFields(int howManyFields) {
		this.howManyFields = howManyFields;
	}
	
	public List<Object[]> getFieldsValues() {
		return fieldsValues;
	}

	public void setFieldsValues(List<Object[]> fieldsValues) {
		this.fieldsValues = fieldsValues;
	}

}
