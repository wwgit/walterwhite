/**   
* @Title: ISQLRobot.java 
* @Package store.db.sql.interfaces 
* @Description: TODO(what to do) 
* @author walterwhite
* @date 2017年1月21日 上午10:13:06 
* @version V1.0   
*/
package store.db.sql.interfaces;

import java.util.List;

import store.db.sql.beans.definitions.CreateTableSQL;
import store.db.sql.beans.definitions.DeleteSQL;
import store.db.sql.beans.definitions.InsertSQL;
import store.db.sql.beans.definitions.SelectSQL;
import store.db.sql.beans.definitions.UpdateSQL;

/** 
 * @ClassName: ISQLRobot 
 * @Description: TODO(what to do) 
 * @author walterwhite
 * @date 2017年1月21日 上午10:13:06 
 *  
 */
public interface ISQLRobot {
	
	public void Insert(InsertSQL insertSQL);
	public void Query(SelectSQL selectSQL);
	public void Update(UpdateSQL updateSQL);
	public void Delete(DeleteSQL deleteSQL);
	public void CreateTable(CreateTableSQL createSQL);
	public List<Object[]> CreateData(Object[] dataTemplate, int totalCnt);

}
