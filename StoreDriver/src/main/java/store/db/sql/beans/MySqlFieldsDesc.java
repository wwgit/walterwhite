/**   
* @Title: FieldsDescription.java 
* @Package store.db.sql.beans 
* @Description: TODO(what to do) 
* @author walterwhite
* @date 2017年1月19日 下午2:54:06 
* @version V1.0   
*/
package store.db.sql.beans;

import store.db.sql.interfaces.SQLFieldsDesc;

/** 
 * @ClassName: FieldsDescription 
 * @Description: TODO(what to do) 
 * @author walterwhite
 * @date 2017年1月19日 下午2:54:06 
 *  
 */
public class MySqlFieldsDesc extends SQLFieldsDesc {
	

	/* (non-Javadoc)
	 * @see store.db.sql.interfaces.SQLFieldsDesc#setNullAsDefault()
	 */
	@Override
	public String setNullAsDefault() {	
		return "";
	}

	/* (non-Javadoc)
	 * @see store.db.sql.interfaces.SQLFieldsDesc#setAutoIncr()
	 */
	@Override
	public String setAutoIncr() {	
		return "AUTO_INCREMENT";
	}	

}
