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
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
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
		
		Map<Object, List<List<Object>>> queryResults = null;
		if(null != result) {
			try {
				queryResults = parseQueryResult(result);
			} catch (SQLException e) {
				reportFailure(e);
			}
		}
		try {
			int cnt = result.getMetaData().getColumnCount();
			while(result.next()) {
				for(int i = 0; i < cnt; i++) {
					System.out.println(result.getObject(i));
				}				
			}
		} catch (SQLException e) {
			reportFailure(e);
		}
		
		this.reporterQueue.add(queryResults);
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
	

	/** 
	* @Title: parseQueryResult 
	* @Description: TODO(what to do) 
	* debug has been done before
	* TreeMap returned: key is the first column value returned
	* returned Map is ordered by key by default
	* 
	* @param @param sqlRet
	* @param @return
	* @param @throws SQLException  
	* @return Map<Object,List<List<Object>>>   
	* @throws 
	*/
	private Map<Object, List<List<Object>>> parseQueryResult(ResultSet sqlRet) throws SQLException {
		
		List<Object> rowData = null;
		List<List<Object>> rows = null;
		Map<Object, List<List<Object>>> result = null;
		Object key = null;
		
		int colCnt = sqlRet.getMetaData().getColumnCount();
		rows = new LinkedList<List<Object>>();
		result = new TreeMap<Object, List<List<Object>>>();
		
		while(sqlRet.next()) {
			//first field value as key by default
			key = sqlRet.getObject(1);
			//System.out.println("key = " + key);
			rowData = new LinkedList<Object>();
			for(int i = 1; i <= colCnt; i++) {
				rowData.add(sqlRet.getString(i));
			}
			
			if(result.containsKey(key)) {
				//System.out.println("key found " + key);
				result.get(key).add(rowData);
			} else {
				//System.out.println("key not found " + key);
				rows = new LinkedList<List<Object>>();
				rows.add(rowData);
				result.put(key, rows);
			}
		}
		
		return result;		
	}
	

}
