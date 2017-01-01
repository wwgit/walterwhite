package handy.tools.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import handy.tools.helpers.DbHelper;
import handy.tools.helpers.TypeHelper;

public class DbPool {
	
	private Vector<Connection> connetions;
	
	public DbPool(DbConfig config) {
		setConnetions(initConnections(config));		
	}
	
	public DbPool() {
		
	}
	

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
				conn = DbHelper.createConnection(config.getUrl(), config.getUserName(), config.getPassword());
				conns.add(i, conn);
			}

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
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
		
		@SuppressWarnings("rawtypes")
		Iterator it = null;
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
	
	/*debug is done
	 * 
	 * 
	 * */
	public void doInserts(Connection conn, String tableName, List<Map> batchData) {
		
		//Connection conn = retrieveConnection();
		Iterator it = batchData.get(0).keySet().iterator();
		String[] keys = new String[batchData.get(0).size()];
		for(int i = 0; it.hasNext(); i++) {
			keys[i] = (String) it.next();
		}
		String sql =  DbHelper.prepareInsertSql(keys,tableName);
		int cnt = batchData.size();
		int[] dataTypes = TypeHelper.getDataTypes(batchData.get(0));
			
		System.out.println(sql);
		try {
			conn.setAutoCommit(false);		
			PreparedStatement statement = conn.prepareStatement(sql);
			for(int i = 0; i < cnt; i++) {
				
				DbHelper.setValuesForSql(statement, batchData.get(i), keys, dataTypes);
				statement.addBatch();
				//System.out.println(batchData.get(i));
				if(i%2000 == 0) {
					System.out.println("commiting data: " + batchData.get(i));
					statement.executeBatch();
					conn.commit();
				}
		    }
			System.out.println("commiting rest of data");
			statement.executeBatch();
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			returnConnection(conn);
		}
						
	}
	
	public void doUpdates(Connection conn, String tableName, List<Map> batchData, String whereCondSql) {
		

		Iterator it = batchData.get(0).keySet().iterator();
		String[] keys = new String[batchData.get(0).size()];
		for(int i = 0; it.hasNext(); i++) {
			keys[i] = (String) it.next();
		}
		String sql =  DbHelper.prepareUpdateSql(keys,tableName);
		if(null != whereCondSql) {
			sql = sql + " " + whereCondSql;
		}
		int cnt = batchData.size();
		int[] dataTypes = TypeHelper.getDataTypes(batchData.get(0));
			
		System.out.println(sql);
		try {
			conn.setAutoCommit(true);
			
			PreparedStatement statement = conn.prepareStatement(sql);
			for(int i = 0; i < cnt; i++) {
				
				DbHelper.setValuesForSql(statement, batchData.get(i), keys, dataTypes);
				
				System.out.println("commiting data: " + batchData.get(i));
				statement.executeUpdate();
				//conn.commit();
		    }
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			returnConnection(conn);
		}
						
	}

	/*Debug has been done
	 * 
	 * 
	 * */
	public Map doQuery(Connection conn, String baseSql, String[] conditions, String[] andOr, Object[] condValues) {
		
		System.out.println("do query start !");
		//Connection conn = retrieveConnection();

		PreparedStatement statement = null;		
		String sql = baseSql;
		String where_sql = DbHelper.prepareSimpleSqlConditions(conditions, andOr);
		
		if(null != where_sql) {
			sql = sql + where_sql;
		}	
		
		System.out.println("sql string: " + sql);
		ResultSet sqlRet = null;
		Map<String,List<List<Object>>> Result = null;
		try {
			//System.out.println("do prepare statement start !");
			statement = conn.prepareStatement(sql);
			DbHelper.setValuesForSql(statement, condValues);
			sqlRet = statement.executeQuery();		
			Result = DbHelper.parseQueryResult(sqlRet);
			 
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			returnConnection(conn);
		}
					
		return Result;
	
	}
	
	/*not finished yet
	 * 
	 * */
	public Map doBatchQuery(Connection conn, String baseSql, String[] conditions, String[] andOr, Object[] values) {
		
		System.out.println("do query start !");
		//Connection conn = retrieveConnection();

		PreparedStatement statement = null;		
		String sql = baseSql;
		String where_sql = DbHelper.prepareSimpleSqlConditions(conditions, andOr);
		
		if(null != where_sql) {
			sql = sql + where_sql;
		}	
		
		System.out.println("sql string: " + sql);
		ResultSet sqlRet = null;
		Map<String,List<List<Object>>> Result = null;
		try {

			//parepare statement for tons of data coming ~
			statement = conn.prepareStatement(sql,ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			statement.setFetchSize(Integer.MIN_VALUE);
			statement.setFetchDirection(ResultSet.FETCH_REVERSE);
			
			DbHelper.setValuesForSql(statement, values);
			sqlRet = statement.executeQuery();		
			Result = DbHelper.parseQueryResult(sqlRet);
			 
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			returnConnection(conn);
		}
					
		return Result;
	
	}
	

	public void doDelete(Connection conn, String delSql, String[] conditions, String[] andOr, Object[] condValues) {
		
		System.out.println("do query start !");

		PreparedStatement statement = null;		
		String sql = delSql;
		String where_sql = DbHelper.prepareSimpleSqlConditions(conditions, andOr);
		
		if(null != where_sql) {
			sql = sql + where_sql;
		}	
		
		System.out.println("sql string: " + sql);
		try {
			//System.out.println("do prepare statement start !");
			statement = conn.prepareStatement(sql);
			DbHelper.setValuesForSql(statement, condValues);
			statement.executeUpdate();		
			 
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			returnConnection(conn);
		}
	
	}

}
