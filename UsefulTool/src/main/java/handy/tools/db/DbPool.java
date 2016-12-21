package handy.tools.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

import handy.tools.helpers.DbHelper;
import handy.tools.helpers.TypeHelper;

public class DbPool {
	
	private Vector<Connection> connetions;
	
	public DbPool(DbConfig config) {
		setConnetions(initConnections(config));		
	}
	

	public Vector<Connection> getConnetions() {
		return connetions;
	}

	public void setConnetions(Vector<Connection> connetions) {
		this.connetions = connetions;
	}
	
	public Vector<Connection> initConnections(DbConfig config) {
		
		Vector<Connection> conns = null;
		Connection conn = null;
		try {
			Class.forName(config.getDbDriver());
			for(int i = 0; i < config.getDbSize(); i++) {
				DbHelper.createConnection(config.getUrl(), config.getUserName(), config.getPassword());
				conns.add(i, conn);
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	

		return conns;		
	}
	
	public void closeConnections() {
		
		@SuppressWarnings("rawtypes")
		Iterator it = null;
		for(it = getConnetions().iterator(); it.hasNext();) {
			Connection conn = (Connection) it.next();
			DbHelper.closeConnection(conn);
		}
	}
	
	public Connection retrieveConnection() {
		Connection conn = null;
		if(!getConnetions().isEmpty()) {
			conn = getConnetions().firstElement();
			getConnetions().remove(0);
		}
		return conn;
	}
	
	public void returnConnection(Connection conn) {
		getConnetions().add(conn);
	}
	
	public void doInserts(String tableName, List<Map> batchData) {
		
		Connection conn = retrieveConnection();
		String[] keys = (String[]) batchData.get(0).keySet().toArray();
		String sql =  DbHelper.prepareInsertSql(keys,tableName);
		int cnt = batchData.size();
		int[] dataTypes = TypeHelper.getDataTypes(batchData.get(0));
			
		System.out.println(sql);
		try {
			conn.setAutoCommit(false);
			PreparedStatement statement = conn.prepareStatement(sql);
			for(int i = 0; i < cnt; i++) {
				DbHelper.doOneInsert(conn, statement, batchData.get(i), keys, dataTypes);
				if(i%2000 == 0) {
					statement.executeBatch();
				}
		    }
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			returnConnection(conn);
		}
						
	}

	public Map doQuery(String sql) {
	
		Connection conn = retrieveConnection();
		
		PreparedStatement statement = null;
		Map<Long,String> result = new TreeMap<Long,String>();
		
		ResultSet sqlRet;
		try {
			statement = conn.prepareStatement(sql);
			sqlRet = statement.executeQuery(sql);
			for(int i = 0; i < sqlRet.getFetchSize(); i++) {
				//result.set(i, sqlRet.getString(i));
				result.put(Long.valueOf(i), sqlRet.getString(i));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			returnConnection(conn);
		}
					
		return result;
	
	}
	

}
