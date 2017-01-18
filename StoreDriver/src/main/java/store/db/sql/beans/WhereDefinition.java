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
	* @Fields whereConditions :  where field-a= and/or, field-b<> and/or, field-c< and/or, field-e=
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
		
		String[] conds = whereConditions.split(",");
		StringBuilder sb = new StringBuilder();
		sb.append("where ");
		
		for(int i = 0; i < conds.length; i++) {
			String[] singleCond = conds[i].trim().split(" ");
			sb.append(singleCond[0]);
			sb.append("?");
			if(singleCond.length > 1) {
				sb.append(" ");
				sb.append(singleCond[1]);
				sb.append(" ");
			}
		}	
		return sb.toString();		
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
	public String generateSimpleWhere(Object[] values) throws Exception {
		
		String[] conds = whereConditions.split(",");
		if(values.length != conds.length) throw new Exception (" input param number does Not match the conds !");
		StringBuilder sb = new StringBuilder();
		sb.append("where ");
		
		for(int i = 0; i < conds.length; i++) {
			String[] singleCond = conds[i].trim().split(" ");
			sb.append(singleCond[0]);
			sb.append(values[i]);
			if(singleCond.length > 1) {
				sb.append(" ");
				sb.append(singleCond[1]);
				sb.append(" ");
			}
		}	
		
		return sb.toString();
	}
	
}
