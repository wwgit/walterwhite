/**   
* @Title: PrimaryKey.java 
* @Package store.db.sql.beans.definitions.constraints 
* @Description: TODO(what to do) 
* @author walterwhite
* @date 2017年1月20日 上午10:21:34 
* @version V1.0   
*/
package store.db.sql.beans.definitions.constraints;

/** 
 * @ClassName: PrimaryKey 
 * @Description: TODO(what to do) 
 * @author walterwhite
 * @date 2017年1月20日 上午10:21:34 
 *  
 */
public class PrimaryKey extends Constraint {

	
	
	
	/* (non-Javadoc)
	 * @see store.db.sql.beans.definitions.constraints.Constraint#generateConstraint(java.lang.StringBuilder)
	 */
	@Override
	public void generateConstraint(StringBuilder sb) throws Exception {
		
		if(null == this.getConstrFields() || this.getConstrFields().length < 1)
			throw new Exception("DEFINING PRIMARY KEY Error: no primary key fields !");
		
		sb.append("CONSTRAINT ");
		if(null != this.getConstrName()) {
			sb.append(this.getConstrName().toLowerCase());
		} else {
			sb.append(this.getConstrFields()[0].toLowerCase() + "_pky");
		}
		sb.append(" PRIMARY KEY(");
		for(int i = 0; i < this.getConstrFields().length; i++) {
			sb.append(this.getConstrFields()[i]);
			if(i < this.getConstrFields().length-1) {
				sb.append(",");
			}
		}
		sb.append(")");
	}
	
	

}
