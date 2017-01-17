/**   
* @Title: WhereDefinition.java 
* @Package store.db.sql.beans 
* @Description: TODO(what to do) 
* @author walterwhite
* @date 2017年1月17日 上午10:52:40 
* @version V1.0   
*/
package store.db.sql.beans;

/** 
 * @ClassName: WhereDefinition 
 * @Description: TODO(what to do) 
 * @author walterwhite
 * @date 2017年1月17日 上午10:52:40 
 *  
 */
public class WhereDefinition {
	
	/** 
	* @Fields whereConditions :  where field-a= and/or, field-b<> and/or, field-c< and/or, field-d in, field-e=
	*/ 
	private String whereConditions;
	
	
	
	public String getWhereConditions() {
		return whereConditions;
	}

	public void setWhereConditions(String whereConditions) {
		this.whereConditions = whereConditions;
	}
	

	/** 
	* @Title: generateWhereConditions 
	* @Description: TODO(what to do) 
	* @param @return  
	* @return String   
	* @throws 
	*/
	public String generateWhereConditions() {
		
		return whereConditions;
		
	}
	
	/** 
	* @Title: generateSimpleWhere 
	* @Description: TODO(what to do) 
	* @param @param values
	* @param @return  
	* @return String   
	* @throws 
	*/
	public String generateSimpleWhere(Object[] values) {
		
		return whereConditions;
	}
	
}
