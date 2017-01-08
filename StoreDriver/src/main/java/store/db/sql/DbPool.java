package store.db.sql;


import java.sql.Connection;
import java.util.Iterator;
import java.util.Vector;

import store.db.sql.beans.DbConfig;
import store.db.sql.commons.SqlCommons;
import store.db.sql.interfaces.ISQLReporter;

public class DbPool extends SqlCommons {
	
	private Vector<Connection> connetions;

	public Vector<Connection> getConnetions() {
		return connetions;
	}

	public void setConnetions(Vector<Connection> connetions) {
		this.connetions = connetions;
	}
	
	/*debug is done
	 * 
	 * 
	 * */
	public void initConnections(DbConfig config, ISQLReporter reporter) {
		
		Vector<Connection> conns = new Vector<Connection>();
		Connection conn = null;
		try {
			
			Class.forName(config.getDbDriver());		
			for(int i = 0; i < config.getPoolSize(); i++) {
				conn = createConnection(config.getUrl(), config.getUserName(), config.getPassword());
				if (null == conn) continue;
				conns.add(i, conn);
			}

		} catch (ClassNotFoundException e) {
			reporter.reportFailure(e);
		} catch (Exception e) {
			reporter.reportFailure(e);
		}	
		this.setConnetions(conns);		
	}
	
	/*debug is done
	 * 
	 * 
	 * */
	public void closeConnections(ISQLReporter reporter) {
		
		Iterator<?> it = null;
		for(it = getConnetions().iterator(); it.hasNext();) {
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
		if(!getConnetions().isEmpty()) {
			conn = getConnetions().firstElement();
			getConnetions().remove(0);
			System.out.println("pool size: " + this.getConnetions().size());
		}
		return conn;
	}
	
	/*debug is done
	 * 
	 * 
	 * */
	public void returnConnection(Connection conn) {
		getConnetions().add(conn);
	}
	

}
