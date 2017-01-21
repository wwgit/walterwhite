package store.db.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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
import store.db.sql.beans.definitions.InsertSQL;
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
	private MySQLRobot robot;

	
	@Before
	public void setUp() throws Exception {
			
		this.dbName = "testperf";
		this.tableName = "user_base_info";
		this.setUsedFields("id,acct_name,phone,mail,pwd".split(","));
		
		String[] whereFields = "id= AND,phone=".split(",");		
		Object[] whereObjs = new Object[2];
		whereObjs[0] = 1200001;
		whereObjs[1] = "autoDataphone";
		
		this.whereDefine = new WhereDefinition();
		this.whereDefine.setWhereConditions(whereFields);
		this.whereDefine.setWhereValues(whereObjs);
		
		this.robot = new MySQLRobot();
		this.robot.setPoolQueue(this.getPool().getConnections());
		this.robot.setReporterQueue(this.getReporter().getReporterQueue());

	}
	
	@Test
	public void mySqlQuery() {
		
		SelectSQL selectSQL = new SelectSQL();
		selectSQL.setTableName(this.tableName);
//		selectSQL.setUsedFields(this.usedFields);
		selectSQL.setWhereConditions(this.whereDefine);
		
		this.robot.Query(selectSQL);
		
//		this.printReports();
	}
	
//	@Test
	public void mySqlInsert() {
		
		InsertSQL insertSQL = new InsertSQL();
		insertSQL.setTableName(this.tableName);
		insertSQL.setUsedFields(this.usedFields);
		
		Object[] dataTemplate = new Object[5];
		dataTemplate[0] = 5600000;
		dataTemplate[1] = "insert";
		dataTemplate[2] = "insert";
		dataTemplate[3] = "insert";
		dataTemplate[4] = "insert";
		
		List<Object[]> testData = this.robot.CreateData(dataTemplate, 1000000);
		insertSQL.setFieldsValues(testData);
		
		long startTime = System.currentTimeMillis();
		this.robot.Insert(insertSQL);
		long endTime = System.currentTimeMillis();
		System.out.println("time:" + (endTime - startTime)/1000 + "s");
		
	}
	
	@Test
	public void mySqlUpdate() {
		
		
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

	public MySQLRobot getRobot() {
		return robot;
	}

	public void setRobot(MySQLRobot robot) {
		this.robot = robot;
	}


}
