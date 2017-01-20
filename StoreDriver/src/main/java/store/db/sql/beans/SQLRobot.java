/**   
* @Title: SQLRobot.java 
* @Package store.db.sql.beans 
* @Description: TODO(what to do) 
* @author walterwhite
* @date 2017年1月20日 下午5:23:54 
* @version V1.0   
*/
package store.db.sql.beans;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import store.db.sql.interfaces.SqlKnowledge;

/** 
 * @ClassName: SQLRobot 
 * @Description: TODO(what to do) 
 * @author walterwhite
 * @date 2017年1月20日 下午5:23:54 
 *  
 */
public class SQLRobot extends SqlKnowledge {

	
	
	
	
	
	
	/* (non-Javadoc)
	 * @see store.db.sql.interfaces.SqlKnowledge#reportFailure(java.lang.Exception)
	 */
	@Override
	public void reportFailure(Exception e) {
	}

	/* (non-Javadoc)
	 * @see store.db.sql.interfaces.SqlKnowledge#reportExecuteProcess(java.lang.String)
	 */
	@Override
	public void reportExecuteProcess(String info) {
	}

	/* (non-Javadoc)
	 * @see store.db.sql.interfaces.SqlKnowledge#reportResults(java.sql.ResultSet)
	 */
	@Override
	public void reportResults(ResultSet result) {
	}

	/* (non-Javadoc)
	 * @see store.db.sql.interfaces.SqlKnowledge#reportResults(int)
	 */
	@Override
	public void reportResults(int doneCnt) {
	}

	/* (non-Javadoc)
	 * @see store.db.sql.interfaces.SqlKnowledge#returnResources(java.sql.Connection, java.sql.PreparedStatement)
	 */
	@Override
	public void returnResources(Connection conn, PreparedStatement statement) {
		
	}

	/* (non-Javadoc)
	 * @see store.db.sql.interfaces.SqlKnowledge#returnResources(java.sql.Connection, java.sql.Statement)
	 */
	@Override
	public void returnResources(Connection conn, Statement statement) {
		
	}

}
