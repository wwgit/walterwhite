/**   
* @Title: ForeignKey.java 
* @Package store.db.sql.beans.definitions.constraints 
* @Description: TODO(what to do) 
* @author walterwhite
* @date 2017年1月20日 上午10:50:08 
* @version V1.0   
*/
package store.db.sql.beans.definitions.constraints;

/** 
 * @ClassName: ForeignKey 
 * @Description: TODO(what to do) 
 * @author walterwhite
 * @date 2017年1月20日 上午10:50:08 
 *  
 */
public class ForeignKey extends Constraint {

	/** 
	* @Fields refTableName :  
	*/ 
	private String refTableName;
	
	/** 
	* @Fields refTableFields :  
	*/ 
	private String[] refTableFields;
	
	
	/* (non-Javadoc)
	 * @see store.db.sql.beans.definitions.constraints.Constraint#generateConstraint(java.lang.StringBuilder)
	 */
	@Override
	public void generateConstraint(StringBuilder sb) throws Exception {
		
		if(null == this.getConstrFields() || this.getConstrFields().length < 1)
			throw new Exception("DEFINING FOREIGN KEY Error: no foreign key fields !");
		if(null == this.refTableName)
			throw new Exception("DEFINING FOREIGN KEY Error: no reference table name !");
		if(null == this.refTableFields || this.refTableFields.length < 1)
			throw new Exception("DEFINING FOREIGN KEY Error: no reference table fields !");
		
		sb.append("CONSTRAINT ");
		if(null != this.getConstrName()) {
			sb.append(this.getConstrName().toLowerCase());
		} else {
			sb.append(this.getConstrFields()[0].toLowerCase() + "_fky");
		}
		sb.append(" FOREIGN KEY(");
		for(int i = 0; i < this.getConstrFields().length; i++) {
			sb.append(this.getConstrFields()[i]);
			if(i < this.getConstrFields().length - 1) {
				sb.append(",");
			}
		}
		sb.append(") REFERENCES ");
		sb.append(this.refTableName);
		sb.append("(");
		for(int j = 0; j < this.refTableFields.length; j ++) {
			sb.append(this.refTableFields[j]);
			if(j < this.refTableFields.length - 1) {
				sb.append(",");
			}
		}
		sb.append(")");
		
	}


	public String getRefTableName() {
		return refTableName;
	}


	public void setRefTableName(String refTableName) {
		this.refTableName = refTableName;
	}


	public String[] getRefTableFields() {
		return refTableFields;
	}


	public void setRefTableFields(String[] refTableFields) {
		this.refTableFields = refTableFields;
	}

}
