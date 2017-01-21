/**   
* @Title: SQLRobot.java 
* @Package store.db.sql.beans 
* @Description: TODO(what to do) 
* @author walterwhite
* @date 2017年1月21日 上午9:47:57 
* @version V1.0   
*/
package store.db.sql.beans;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import store.db.sql.interfaces.SqlKnowledge;

/** 
 * @ClassName: SQLRobot 
 * @Description: TODO(what to do) 
 * @author walterwhite
 * @date 2017年1月21日 上午9:47:57 
 *  
 */
public abstract class SQLRobot extends SqlKnowledge {
	
	
	/** 
	* @Fields poolPutQueue : 
	* SQLDriver initializes this queue  
	* dbPool initializes connections to this queue 
	* while robots get connections to from this queue.
	* After completing tasks, robots return connections to this queue
	* 
	*/ 
	private ArrayBlockingQueue<Connection> poolQueue;

	
	/** 
	* @Fields reporterGetQueue : 
	* SQLDriver initializes this queue 
	* Robots write reports and SQL results to this queue.
	* SQLReporter read reports and SQL results from this queue
	*/ 
	private LinkedBlockingQueue<Object> reporterQueue;
	
	
	protected Connection getConnectionFrmQueue() {
		Connection conn = null;
		try {
			conn = this.getPoolQueue().take();
		} catch (InterruptedException e) {
			reportFailure(e);
		}
		return conn;
	}
	
	
	
	/* (non-Javadoc)
	 * @see store.db.sql.interfaces.SqlKnowledge#reportFailure(java.lang.Exception)
	 */
	@Override
	protected void reportFailure(Exception e) {
		this.reporterQueue.add(e);
	}

	/* (non-Javadoc)
	 * @see store.db.sql.interfaces.SqlKnowledge#reportExecuteProcess(java.lang.String)
	 */
	@Override
	protected void reportExecuteProcess(String info) {
		this.reporterQueue.add(info);
	}

	/* (non-Javadoc)
	 * @see store.db.sql.interfaces.SqlKnowledge#reportResults(java.sql.ResultSet)
	 */
	@Override
	protected void reportResults(ResultSet result) {
		this.reporterQueue.add(result);
	}

	/* (non-Javadoc)
	 * @see store.db.sql.interfaces.SqlKnowledge#reportResults(int)
	 */
	@Override
	protected void reportResults(int doneCnt) {
		this.reporterQueue.add(doneCnt);
	}

	/* (non-Javadoc)
	 * @see store.db.sql.interfaces.SqlKnowledge#returnResources(java.sql.Connection, java.sql.PreparedStatement)
	 */
	@Override
	protected void returnResources(Connection conn, PreparedStatement statement) {
		try {
			statement.close();
			if(conn.isClosed() == false)
				this.poolQueue.add(conn);
		} catch (SQLException e) {
			reportFailure(e);
		} 
	}

	/* (non-Javadoc)
	 * @see store.db.sql.interfaces.SqlKnowledge#returnResources(java.sql.Connection, java.sql.Statement)
	 */
	@Override
	protected void returnResources(Connection conn, Statement statement) {
		try {
			statement.close();
			if(conn.isClosed() == false)
				this.poolQueue.add(conn);
		} catch (SQLException e) {
			reportFailure(e);
		} 
	}

	public ArrayBlockingQueue<Connection> getPoolQueue() {
		return poolQueue;
	}

	public void setPoolQueue(ArrayBlockingQueue<Connection> poolQueue) {
		this.poolQueue = poolQueue;
	}

	public LinkedBlockingQueue<Object> getReporterQueue() {
		return reporterQueue;
	}

	public void setReporterQueue(LinkedBlockingQueue<Object> reporterQueue) {
		this.reporterQueue = reporterQueue;
	}
	

}
