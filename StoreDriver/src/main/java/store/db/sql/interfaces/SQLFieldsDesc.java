/**   
* @Title: SqlFieldDesc.java 
* @Package store.db.sql.interfaces 
* @Description: TODO(what to do) 
* @author walterwhite
* @date 2017年1月19日 下午3:29:57 
* @version V1.0   
*/
package store.db.sql.interfaces;

/** 
 * @ClassName: SqlFieldDesc 
 * @Description: TODO(what to do) 
 * @author walterwhite
 * @date 2017年1月19日 下午3:29:57 
 *  
 */
public abstract class SQLFieldsDesc implements ISQLFieldsDesc {
	
	
	public abstract String setNullAsDefault();
	public abstract String setAutoIncr();
	
	public void setPrimaryKey(StringBuilder sb) {		
		sb.append("PRIMARY KEY");
	}
	
	public void setTablePrimaryKey(StringBuilder sb, String[] tableFields) {		
		
		String keyName = tableFields[0] + "_pky";
		setTablePrimaryKey(sb, tableFields, keyName);
	}
	
	public void setTablePrimaryKey(StringBuilder sb, String[] tableFields, String keyName) {		
		sb.append("CONSTRAINT ");
		sb.append(keyName.toLowerCase());
		sb.append(" PRIMARY KEY ( ");
		for(int i = 0; i < tableFields.length; i++) {
			sb.append(tableFields[i]);
			if(i < tableFields.length-1) sb.append(",");
		}
		sb.append(" )");
	}
	
	public void setTableForeignKey(StringBuilder sb, String tableName,String[] tableFields, String[] refTablefields) {
		String keyName = tableName + "_fky";
		setTableForeignKey(sb, tableName, tableFields, refTablefields, keyName);
	}
	
	public void setTableForeignKey(StringBuilder sb, String tableName, String[] tableFields,
								   String[] refTablefields, String keyName) {
		sb.append("CONSTRAINT ");
		sb.append(keyName.toLowerCase());
		sb.append(" FOREIGN KEY ( ");
		for(int i = 0; i < tableFields.length; i++) {
			sb.append(tableFields[i]);
			if(i < tableFields.length-1) sb.append(",");
		}
		sb.append(" ) ");
		sb.append("REFERENCES ");
		sb.append(tableName);
		sb.append(" ( ");
		for(int i = 0; i < refTablefields.length; i++) {
			sb.append(refTablefields[i]);
			if(i < refTablefields.length-1) sb.append(",");
		}
		sb.append(" )");
		
	}
	
	public void setNull(StringBuilder sb) {
		sb.append("NULL");
	}
	
	public void setNotNull(StringBuilder sb) {
		sb.append("NOT NULL");
	}

	/* (non-Javadoc)
	 * @see store.db.sql.interfaces.ISQLFieldsDesc#setNullAsDefault(java.lang.StringBuilder)
	 */
	public void setNullAsDefault(StringBuilder sb) {
		sb.append(this.setNullAsDefault());
	}

	/* (non-Javadoc)
	 * @see store.db.sql.interfaces.ISQLFieldsDesc#setAutoIncr(java.lang.StringBuilder)
	 */
	public void setAutoIncr(StringBuilder sb) {
		sb.append(this.setAutoIncr());
	}
	
}
