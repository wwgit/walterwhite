package store.db.interfaces;

import handy.tools.helpers.DbHelper;
import handy.tools.helpers.TypeHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import store.db.commons.SqlCommons;

public abstract class SqlKnowledge extends SqlCommons {

	
	public abstract void returnConnection(Connection conn);
	
	/*debug is done
	 * 
	 * 
	 * */
	public void doInsert(Connection conn, String tableName, List<Map<String,Object>> rowsData, int[] DataTypes) {
		
		//Connection conn = retrieveConnection();
		Iterator it = rowsData.get(0).keySet().iterator();
		String[] keys = new String[rowsData.get(0).size()];
		for(int i = 0; it.hasNext(); i++) {
			keys[i] = (String) it.next();
		}
		String sql =  prepareInsertSql(keys,tableName);
		int cnt = rowsData.size();
		int[] dataTypes = TypeHelper.getDataTypes(rowsData.get(0));
			
		System.out.println(sql);
		try {
			conn.setAutoCommit(false);		
			PreparedStatement statement = conn.prepareStatement(sql);
			for(int i = 0; i < cnt; i++) {
				
				setValuesForSql(statement, rowsData.get(i), keys, dataTypes);
				statement.addBatch();
				//System.out.println(batchData.get(i));
				if(i%2000 == 0) {
					System.out.println("commiting data: " + rowsData.get(i));
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
	
public void doUpdate(Connection conn, String tableName, List<Map> batchData, String[] whereColPlusOper, String[] whereAndOr) {
		

		Iterator it = batchData.get(0).keySet().iterator();
		String[] keys = new String[batchData.get(0).size()];
		for(int i = 0; it.hasNext(); i++) {
			keys[i] = (String) it.next();
		}
		String sql =  prepareUpdateSql(keys,tableName, whereColPlusOper, whereAndOr);
		int cnt = batchData.size();
		int[] dataTypes = TypeHelper.getDataTypes(batchData.get(0));
			
		System.out.println(sql);
		try {
			conn.setAutoCommit(true);
			
			PreparedStatement statement = conn.prepareStatement(sql);
			for(int i = 0; i < cnt; i++) {
				
				setValuesForSql(statement, batchData.get(i), keys, dataTypes);
				
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
	
}
