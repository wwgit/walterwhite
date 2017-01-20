/**   
* @Title: WhereDefinition.java 
* @Package store.db.sql.beans 
* @Description: TODO(what to do) 
* @author walterwhite
* @date 2017年1月17日 上午10:52:40 
* @version V1.0   
*/
package store.db.sql.beans.definitions;

/** 
 * @ClassName: WhereDefinition 
 * @Description: TODO(what to do) 
 * @author walterwhite
 * @date 2017年1月17日 上午10:52:40 
 *  
 */
public class WhereDefinition {
	
	/** 
	* @Fields whereConditions :  where field-a= and/or, field-b<> and/or, field-c< and/or, field-e=
	*/ 
	private String[] whereConditions;
	
	private Object[] whereValues;
	
	public String[] getWhereConditions() {
		return whereConditions;
	}

	public void setWhereConditions(String[] whereConditions) {
		this.whereConditions = whereConditions;
	}
	

	/** 
	* @Title: generateWhereConditions 
	* @Description: TODO(what to do) 
	* @param @return  
	* @return String   
	* @throws 
	*/
	public void generateWhereConditions(StringBuilder sb) {
		
//		String[] conds = whereConditions.split(",");
		sb.append("where ");
		
		for(int i = 0; i < whereConditions.length; i++) {
			String[] singleCond = whereConditions[i].trim().split(" ");
			sb.append(singleCond[0]);
			sb.append("?");
			if(singleCond.length > 1) {
				sb.append(" ");
				sb.append(singleCond[1]);
				sb.append(" ");
			}
		}	
	}
	
	/**
	 * @throws Exception  
	* @Title: generateSimpleWhere 
	* @Description: TODO(what to do) 
	* @param @param values
	* @param @return  
	* @return String   
	* @throws 
	*/
	public void generateSimpleWhere(StringBuilder sb) throws Exception {
		
//		String[] conds = whereConditions.split(",");
		if(this.whereValues.length != whereConditions.length) 
			throw new Exception (" input param number does Not match the conds !");
		sb.append("where ");
		
		for(int i = 0; i < whereConditions.length; i++) {
			String[] singleCond = whereConditions[i].trim().split(" ");
			sb.append(singleCond[0]);
			sb.append(this.whereValues[i]);
			if(singleCond.length > 1) {
				sb.append(" ");
				sb.append(singleCond[1]);
				sb.append(" ");
			}
		}	
		
	}

	public Object[] getWhereValues() {
		return whereValues;
	}

	public void setWhereValues(Object[] whereValues) {
		this.whereValues = whereValues;
	}
	
}
