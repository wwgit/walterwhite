package handy.tools.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Vector;

import handy.tools.interfaces.DbHelper;

public class DbPool extends DbHelper {
	
	private int dbSize;
	private Vector<Connection> connetions;
	
	public DbPool(int size, DbConfig config) {
		setDbSize(size);
		try {
			setConnetions(initConnections(config.getUrl(),config.getDbClazz(),
											config.getUserName(),config.getPassword(),getDbSize()));
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public int getDbSize() {
		return dbSize;
	}
	public void setDbSize(int dbSize) {
		this.dbSize = dbSize;
	}

	public Vector<Connection> getConnetions() {
		return connetions;
	}

	public void setConnetions(Vector<Connection> connetions) {
		this.connetions = connetions;
	}
	
	
	

}
