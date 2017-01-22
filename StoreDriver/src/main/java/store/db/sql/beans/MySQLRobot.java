/**   
* @Title: SQLRobot.java 
* @Package store.db.sql.beans 
* @Description: TODO(what to do) 
* @author walterwhite
* @date 2017年1月20日 下午5:23:54 
* @version V1.0   
*/
package store.db.sql.beans;

import handy.tools.helpers.TypeHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import store.db.sql.beans.definitions.CreateTableSQL;
import store.db.sql.beans.definitions.DeleteSQL;
import store.db.sql.beans.definitions.InsertSQL;
import store.db.sql.beans.definitions.MySqlCreateSQL;
import store.db.sql.beans.definitions.SelectSQL;
import store.db.sql.beans.definitions.UpdateSQL;
import store.db.sql.interfaces.ISQLRobot;

/** 
 * @ClassName: SQLRobot 
 * @Description: TODO(what to do) 
 * @author walterwhite
 * @date 2017年1月20日 下午5:23:54 
 *  
 */
public class MySQLRobot extends SQLRobot implements ISQLRobot {
	
	/** 
	* @Title: doMySqlPrepareQuery 
	* @Description: TODO(query using prepareStatement using mysql stream mode) 
	* @param @param conn
	* @param @param completeSql
	* @param @param condValues  
	* @return void   
	* @throws 
	*/
	private void doMySqlPrepareQuery(Connection conn, String completeSql, Object[] condValues) {
		
		PreparedStatement statement = null;		
		reportExecuteProcess("ready to execute sql:" + completeSql);
		
		ResultSet sqlResult = null;
		try {
			statement = conn.prepareStatement(completeSql,ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			statement.setFetchSize(Integer.MIN_VALUE);
			statement.setFetchDirection(ResultSet.FETCH_REVERSE);
			
			if(null != condValues) {
				int[] dataTypes = TypeHelper.getDataTypes(condValues);
				setValuesForSql(statement, condValues, dataTypes);
			}
			sqlResult = statement.executeQuery();
			reportResults(sqlResult);
			 
		} catch (SQLException e) {
			reportFailure(e);
		} finally {
			returnResources(conn,statement);
		}

	}

	/* (non-Javadoc)
	 * @see store.db.sql.interfaces.ISQLRobot#Insert(store.db.sql.beans.definitions.InsertSQL)
	 */
	public void Insert(InsertSQL insertSQL) {
		
		Connection conn = getConnectionFrmQueue();
		try {
			this.doInsert(conn, insertSQL.generatePrepareSQLStatment(), insertSQL.getFieldsValues());
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
			doMySqlPrepareQuery(conn,selectSQL.generatePrepareSQLStatment(),whereValues);
//			doPrepareQuery(conn,selectSQL.generatePrepareSQLStatment(),whereValues);
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
	 * @see store.db.sql.interfaces.ISQLRobot#CreateTable(store.db.sql.beans.definitions.CreateTableSQL)
	 */
	public void CreateTable(CreateTableSQL createSQL) {
	
		Connection conn = null;
		conn = getConnectionFrmQueue();
		
		try {
			if(false == createSQL instanceof MySqlCreateSQL) {
				throw new Exception("SQL is Not MySqlCreateSQL !");
			}
			doPrepareSql(conn, ((MySqlCreateSQL)createSQL).generateCreateTableSQL(), null);
		} catch (Exception e) {
			reportFailure(e);
		}	
	}	

}
