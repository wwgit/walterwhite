package store.db.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import org.junit.Before;
/**   
 * @Title: SQLRobotTest.java 
 * @Package  
 * @Description: TODO(what to do) 
 * @author walterwhite
 * @date 2017年1月21日 上午10:35:57 
 * @version V1.0   
 */
import org.junit.Test;

import store.db.sql.beans.MySQLRobot;
import store.db.sql.beans.SQLReporter;
import store.db.sql.beans.definitions.SelectSQL;
import store.db.sql.beans.definitions.WhereDefinition;

/** 
 * @ClassName: SQLRobotTest 
 * @Description: TODO(what to do) 
 * @author walterwhite
 * @date 2017年1月21日 上午10:35:57 
 *  
 */
public class SQLRobotTest extends TestCaseAbstract {
	
	private String tableName;
	private String dbName;
	private String[] usedFields;
	private WhereDefinition whereDefine;

	
	@Before
	public void setUp() throws Exception {
		
		
		
		this.dbName = "testperf";
		this.tableName = "user_base_info";
		this.setUsedFields("id,acct_name,phone,mail,pwd".split(","));
		
		String[] whereFields = "phone=,mail=".split(",");		
		Object[] whereObjs = new Object[2];
		whereObjs[0] = "123456789";
		whereObjs[1] = "mail_address";
		
		this.whereDefine = new WhereDefinition();
		this.whereDefine.setWhereConditions(whereFields);
		this.whereDefine.setWhereValues(whereObjs);

	}
	
	@Test
	public void mySqlQuery() {
		
		SelectSQL selectSQL = new SelectSQL();
		selectSQL.setTableName(this.tableName);
		selectSQL.setUsedFields(this.usedFields);
		selectSQL.setWhereConditions(this.whereDefine);
		
		MySQLRobot robot = new MySQLRobot();
		robot.setPoolQueue(this.getPool().getConnections());
		robot.setReporterQueue(this.getReporter().getReporterQueue());
		robot.Query(selectSQL);
		
		this.printReports();
	}


	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public String[] getUsedFields() {
		return usedFields;
	}

	public void setUsedFields(String[] usedFields) {
		this.usedFields = usedFields;
	}

	public WhereDefinition getWhereDefine() {
		return whereDefine;
	}

	public void setWhereDefine(WhereDefinition whereDefine) {
		this.whereDefine = whereDefine;
	}





}
