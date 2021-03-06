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
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import store.db.sql.beans.definitions.DeleteSQL;
import store.db.sql.beans.definitions.InsertSQL;
import store.db.sql.beans.definitions.SelectSQL;
import store.db.sql.beans.definitions.UpdateSQL;
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
		System.out.println(e);
		this.reporterQueue.add(e);
	}

	/* (non-Javadoc)
	 * @see store.db.sql.interfaces.SqlKnowledge#reportExecuteProcess(java.lang.String)
	 */
	@Override
	protected void reportExecuteProcess(String info) {
		System.out.println(info);
//		this.reporterQueue.add(info);
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
				System.out.println(queryResults);
			} catch (SQLException e) {
				reportFailure(e);
			}
		}
		
		this.reporterQueue.add(queryResults);
	}

	/* (non-Javadoc)
	 * @see store.db.sql.interfaces.SqlKnowledge#reportResults(int)
	 */
	@Override
	protected void reportResults(int doneCnt) {
		System.out.println(doneCnt);
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

	protected ArrayBlockingQueue<Connection> getPoolQueue() {
		return poolQueue;
	}

	public void setPoolQueue(ArrayBlockingQueue<Connection> poolQueue) {
		this.poolQueue = poolQueue;
	}

	protected LinkedBlockingQueue<Object> getReporterQueue() {
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
	
	/* (non-Javadoc)
	 * @see store.db.sql.interfaces.ISQLRobot#CreateData(java.lang.Class[])
	 */
	@SuppressWarnings("null")
	public List<Object[]> CreateData(Object[] dataTemplate, int totalCnt) {
		
		List<Object[]> allData = new ArrayList<Object[]>();
		StringBuilder sb = new StringBuilder();
		int cnt = 1;
		
		for(int i = 0; i < totalCnt; i++) {
			Object[] rowData = CreateRowData(dataTemplate,cnt,sb);
			allData.add(rowData);
			cnt += 1;
		}	
		
		return allData;
	}
	
	private Object[] CreateRowData(Object[] dataTemplate, int cnt, StringBuilder sb) {
		
		Object[] rowData = new Object[dataTemplate.length];

		for(int i = 0; i < dataTemplate.length; i++) {
			if(dataTemplate[i] instanceof Integer) {
				rowData[i] = ((Integer)dataTemplate[i]).intValue() + cnt;
				continue;
			}
			if(dataTemplate[i] instanceof Long) {
				rowData[i] = ((Long)dataTemplate[i]).longValue() + cnt;
				continue;
			}
			if(dataTemplate[i] instanceof String) {
				sb.append(dataTemplate[i]);
				sb.append(String.valueOf(cnt));
				rowData[i] = sb.toString();
				sb.delete(0, sb.length());
				continue;
			}
			rowData[i] = dataTemplate[i];
		}	
		return rowData;
	}
	
	public void SimpleSql(String sql) {
		Connection conn = getConnectionFrmQueue();
		doSimpleSql(conn, sql);	
	}
	
	public void SimpleQuery(String sql) {
		Connection conn = getConnectionFrmQueue();
		doSimpleQuery(conn, sql);
	}
	
	/* (non-Javadoc)
	 * @see store.db.sql.interfaces.ISQLRobot#Insert(store.db.sql.beans.definitions.InsertSQL)
	 */
	public void Insert(InsertSQL insertSQL) {
		
		Connection conn = getConnectionFrmQueue();
		try {
			doInsert(conn, insertSQL.generatePrepareSQLStatment(), insertSQL.getFieldsValues());
		} catch (Exception e) {
			reportFailure(e);
		}
	}
	
	/* (non-Javadoc)
	 * @see store.db.sql.interfaces.ISQLRobot#Update(store.db.sql.beans.definitions.UpdateSQL)
	 */
	public void Update(UpdateSQL updateSQL) {
		
		Object[] whereValues = null; Connection conn = null;
		Object[] fieldValues = null;
		conn = getConnectionFrmQueue();
		
		if(null != updateSQL.getWhereConditions() && 
				   updateSQL.getWhereConditions().getWhereConditions().length >= 1) {
			whereValues = updateSQL.getWhereConditions().getWhereValues();
		}
		if(null != updateSQL.getSetFieldValues() &&
				   updateSQL.getSetFieldValues().length >=1 ) {
			fieldValues = updateSQL.getSetFieldValues();
		}
		Object[] allValues = new Object[fieldValues.length + whereValues.length];
		System.arraycopy(fieldValues, 0, allValues, 0, fieldValues.length);
		System.arraycopy(whereValues, 0, allValues, fieldValues.length, whereValues.length);
		
		try {
			this.doPrepareSql(conn, updateSQL.generatePrepareSQLStatment(), allValues);
		} catch (Exception e) {
			reportFailure(e);
		}
		
	}
	
	/* (non-Javadoc)
	 * @see store.db.sql.interfaces.ISQLRobot#Delete(store.db.sql.beans.definitions.DeleteSQL)
	 */
	public void Delete(DeleteSQL deleteSQL) {
		
		Connection conn = null;
		conn = getConnectionFrmQueue();
		
		Object[] whereValues = null;
		if(null != deleteSQL.getWhereConditions() && 
				deleteSQL.getWhereConditions().getWhereConditions().length >= 1) {
			whereValues = deleteSQL.getWhereConditions().getWhereValues();
		}
		
		try {
			doPrepareSql(conn, deleteSQL.generatePrepareSQLStatment(), whereValues);
		} catch (Exception e) {
			reportFailure(e);
		}	
		
	}
	
	/* (non-Javadoc)
	 * @see store.db.sql.interfaces.ISQLRobot#Select(store.db.sql.beans.definitions.SelectSQL)
	 */
	public void Query(SelectSQL selectSQL) {
		
		Object[] whereValues = null; Connection conn = null;	
		conn = getConnectionFrmQueue();
		
		if(null != selectSQL.getWhereConditions() && 
		   selectSQL.getWhereConditions().getWhereConditions().length >= 1) {
		   whereValues = selectSQL.getWhereConditions().getWhereValues();
		}
		
		try {
			doPrepareQuery(conn,selectSQL.generatePrepareSQLStatment(),whereValues);
		} catch (Exception e) {
			reportFailure(e);
		}
	}
	
	

}
