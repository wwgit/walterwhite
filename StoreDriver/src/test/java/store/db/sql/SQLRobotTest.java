package store.db.sql;

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
import store.db.sql.beans.definitions.InsertSQL;
import store.db.sql.beans.definitions.SelectSQL;
import store.db.sql.beans.definitions.UpdateSQL;
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
		
		String[] whereFields = "id=".split(",");		
		Object[] whereObjs = new Object[1];
		whereObjs[0] = "4000002";
//		whereObjs[1] = "update";
		
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
		
		String[] where = "id=".split(",");		
		Object[] whereObjects = new Object[1];
		whereObjects[0] = 2;
		
		WhereDefinition whereDefines = new WhereDefinition();
		whereDefines.setWhereConditions(where);
		whereDefines.setWhereValues(whereObjects);
//		selectSQL.setUsedFields(this.usedFields);
		selectSQL.setWhereConditions(whereDefines);
		
		this.robot.Query(selectSQL);
		
//		this.printReports();
	}
	
//	@Test
	public void mySqlInsert() {
		
		InsertSQL insertSQL = new InsertSQL();
		insertSQL.setTableName(this.tableName);
		insertSQL.setUsedFields(this.usedFields);
		
		Object[] dataTemplate = new Object[5];
		dataTemplate[0] = 1;
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
		
		UpdateSQL updateSQL = new UpdateSQL();
		updateSQL.setTableName(this.tableName);
		
		this.setUsedFields("acct_name,phone,mail,pwd".split(","));
		updateSQL.setUsedFields(this.getUsedFields());
		
		String[] whereFields = "id= AND,phone=".split(",");		
		Object[] whereObjs = new Object[2];
		whereObjs[0] = 3;
		whereObjs[1] = "insert2";
		this.whereDefine.setWhereConditions(whereFields);
		this.whereDefine.setWhereValues(whereObjs);
		updateSQL.setWhereConditions(this.whereDefine);
		
		Object[] dataTemplate = new Object[4];
		dataTemplate[0] = "update";
		dataTemplate[1] = "update";
		dataTemplate[2] = "update";
		dataTemplate[3] = "update";
		
		updateSQL.setSetFieldValues(dataTemplate);
		
		this.robot.Update(updateSQL);
		
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
