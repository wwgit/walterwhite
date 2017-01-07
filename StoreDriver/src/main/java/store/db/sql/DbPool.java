package store.db.sql;

import handy.tools.db.DbConfig;
import handy.tools.helpers.DbHelper;

import java.sql.Connection;
import java.util.Iterator;
import java.util.Vector;

import store.db.sql.commons.SqlCommons;

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
	public Vector<Connection> initConnections(DbConfig config) {
		
		Vector<Connection> conns = new Vector<Connection>();
		Connection conn = null;
		try {
			
			Class.forName(config.getDbDriver());		
			for(int i = 0; i < config.getDbSize(); i++) {
				conn = createConnection(config.getUrl(), config.getUserName(), config.getPassword());
				conns.add(i, conn);
			}

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	

		return conns;		
	}
	
	/*debug is done
	 * 
	 * 
	 * */
	public void closeConnections() {
		
		Iterator<?> it = null;
		for(it = getConnetions().iterator(); it.hasNext();) {
			Connection conn = (Connection) it.next();
			DbHelper.closeConnection(conn);
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
