/**   
* @Title: Constraint.java 
* @Package store.db.sql.beans 
* @Description: TODO(what to do) 
* @author walterwhite
* @date 2017年1月20日 上午9:56:38 
* @version V1.0   
*/
package store.db.sql.beans.definitions.constraints;

/** 
 * @ClassName: Constraint 
 * @Description: TODO(what to do) 
 * @author walterwhite
 * @date 2017年1月20日 上午9:56:38 
 *  
 */
public abstract class Constraint {
	
	private String constrName;
	
	/** 
	* @Fields constrFields :  
	* PRIMARY KEY(field1,field2,field3...)-> constrFields = field1,field2,field3...
	* UNIQUE(field1,field2,field3...)-> constrFields = field1,field2,field3...
	* FOREIGN KEY(field1,field2,field3...) REFERENCES ref_tableName(....)
	* -> constrFields = field1,field2,field3...
	*/ 
	private String[] constrFields;
	
	public abstract void generateConstraint(StringBuilder sb) throws Exception;

	public String getConstrName() {
		return constrName;
	}

	public void setConstrName(String constrName) {
		this.constrName = constrName;
	}

	public String[] getConstrFields() {
		return constrFields;
	}

	public void setConstrFields(String[] constrFields) {
		this.constrFields = constrFields;
	}
	

}
