package store.db.sql;


import java.sql.Connection;
import java.util.Iterator;
import java.util.Vector;
import java.util.concurrent.ArrayBlockingQueue;

import store.db.sql.beans.DbConfig;
import store.db.sql.commons.SqlCommons;
import store.db.sql.interfaces.ISQLReporter;

public class DbPool extends SqlCommons {
	
	/** 
	* @Fields poolPutQueue : 
	* SQLDriver initializes this queue  
	* dbPool initializes connections to this queue 
	* while robots get connections to from this queue.
	* After completing tasks, robots return connections to this queue
	* 
	*/ 
	private ArrayBlockingQueue<Connection> connections;

	
	/*debug is done
	 * 
	 * 
	 * */
	public void initConnections(DbConfig config, ISQLReporter reporter) {
		
		ArrayBlockingQueue<Connection> conns = new ArrayBlockingQueue<Connection>(config.getPoolSize());
		Connection conn = null;
		try {
			
			Class.forName(config.getDbDriver());		
			for(int i = 0; i < config.getPoolSize(); i++) {
				conn = createConnection(config.getUrl(), config.getUserName(), config.getPassword());
				if (null == conn) continue;
				conns.add(conn);
			}

		} catch (ClassNotFoundException e) {
			reporter.reportFailure(e);
		} catch (Exception e) {
			reporter.reportFailure(e);
		}	
		this.setConnections(conns);		
	}
	
	/*debug is done
	 * 
	 * 
	 * */
	public void closeConnections(ISQLReporter reporter) {
		
		Iterator<?> it = null;
		for(it = getConnections().iterator(); it.hasNext();) {
			Connection conn = (Connection) it.next();
			closeConnection(conn, reporter);
		}
	}
	
	/*debug is done
	 * 
	 * 
	 * */
	public Connection retrieveConnection() {
		Connection conn = null;
		try {
			conn = this.getConnections().take();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			return conn;
		}
	}
	
	/*debug is done
	 * 
	 * 
	 * */
	public void returnConnection(Connection conn) {
		getConnections().add(conn);
	}

	public ArrayBlockingQueue<Connection> getConnections() {
		return connections;
	}

	public void setConnections(ArrayBlockingQueue<Connection> connections) {
		this.connections = connections;
	}
	

}
