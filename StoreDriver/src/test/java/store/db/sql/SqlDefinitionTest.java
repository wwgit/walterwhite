/**   
* @Title: SqlDefinitionTest.java 
* @Package store.db.sql 
* @Description: TODO(what to do) 
* @author walterwhite
* @date 2017年1月18日 下午4:53:42 
* @version V1.0   
*/
package store.db.sql;

import org.junit.Before;
import org.junit.Test;

import store.db.sql.beans.MySqlFieldsDesc;
import store.db.sql.beans.definitions.SQLDefinition;
import store.db.sql.beans.definitions.CreateTableSQL;
import store.db.sql.beans.definitions.DeleteSQL;
import store.db.sql.beans.definitions.InsertSQL;
import store.db.sql.beans.definitions.SelectSQL;
import store.db.sql.beans.definitions.UpdateSQL;
import store.db.sql.beans.definitions.WhereDefinition;

/** 
 * @ClassName: SqlDefinitionTest 
 * @Description: TODO(what to do) 
 * @author walterwhite
 * @date 2017年1月18日 下午4:53:42 
 *  
 */
public class SqlDefinitionTest {
	
	@Before
	public void setUp() throws Exception {
		
	}
	
	@Test
	public void SelectSQLTest() throws Exception {
		
		String[] where = "field-a= or, field-b= and, field-c> or, field-d> or, field-e<>".split(",");		
		WhereDefinition wd = new WhereDefinition();
		wd.setWhereConditions(where);
		
		Object[] values = new Object[5];
		values[0] = "field-a_value";
		values[1] = 1;
		values[2] = 2;
		values[3] = 3;
		values[4] = 4;
		
		SQLDefinition selectSQL = new SelectSQL();
		selectSQL.setDbName("testperf");
		selectSQL.setTableName("user_base_info");
		selectSQL.setWhereConditions(wd);
		
		String usedFields = "field-a,field-b,field-c,field-d";
//		selectSQL.setUsedFields(usedFields);
		
		System.out.println(selectSQL.generatePrepareSQLStatment());
		System.out.println(selectSQL.generateSimpleSQL(values));	
		
	}
	
	@Test
	public void InsertSQLTest() throws Exception {
		String[] usedFields = "field-a,field-b,field-c,field-d,field-e".split(",");
		
		SQLDefinition insertSQL = new InsertSQL();
		insertSQL.setDbName("testperf");
		insertSQL.setTableName("user_base_info");
		insertSQL.setUsedFields(usedFields);
		((InsertSQL)insertSQL).setHowManyFields(5);
		
		Object[] values = new Object[5];
		values[0] = "field-a_value";
		values[1] = 1;
		values[2] = 2;
		values[3] = 3;
		values[4] = 4;
		
		System.out.println(insertSQL.generatePrepareSQLStatment());
		System.out.println(insertSQL.generateSimpleSQL(values));
		
	}
	
	@Test
	public void UpdateSQLTest() throws Exception {
		
		String[] usedFields = "field-a,field-b,field-c,field-d,field-e".split(",");
		String[] where = "field-a= or, field-b= and, field-c> or, field-d> or, field-e<>".split(",");
		
		WhereDefinition wd = new WhereDefinition();
		wd.setWhereConditions(where);
		SQLDefinition updateSQL = new UpdateSQL();
		updateSQL.setDbName("testperf");
		updateSQL.setTableName("user_base_info");
		updateSQL.setWhereConditions(wd);
		updateSQL.setUsedFields(usedFields);
		
		Object[] values = new Object[5];
		values[0] = "where_values";
		values[1] = 1;
		values[2] = 2;
		values[3] = 3;
		values[4] = 4;
		
		Object[] fields_values = new Object[5];
		fields_values[0] = "fields_values";
		fields_values[1] = 1;
		fields_values[2] = 2;
		fields_values[3] = 3;
		fields_values[4] = 4;
		
		System.out.println(updateSQL.generatePrepareSQLStatment());
		System.out.println(
				((UpdateSQL)updateSQL).generateSimpleUpdate(fields_values, values)
				);
		
	}
	
	@Test
	public void DeleteSQLTest() throws Exception {
		String[] where = "field-a= or, field-b= and, field-c> or, field-d> or, field-e<>".split(",");		
		WhereDefinition wd = new WhereDefinition();
		wd.setWhereConditions(where);
		SQLDefinition deleteSQL = new DeleteSQL();
		
		deleteSQL.setDbName("testperf");
		deleteSQL.setTableName("user_base_info");
		deleteSQL.setWhereConditions(wd);
		
		Object[] values = new Object[5];
		values[0] = "field-a_value";
		values[1] = 1;
		values[2] = 2;
		values[3] = 3;
		values[4] = 4;
		
		System.out.println(deleteSQL.generatePrepareSQLStatment());
		System.out.println(deleteSQL.generateSimpleSQL(values));
		
	}
	
	@Test
	public void CreateTableSQLTest() throws Exception {
		
		String[] usedFields = "field-a,field-b,field-c,field-d,field-e".split(",");
		String[] fieldsTypes = "INT,VARCHAR(255),VARCHAR(255),VARCHAR(255),VARCHAR(255)".split(",");
		String[] nullDescs = "YES,NO,DEFAULT,YES,YES".split(",");
		String[] autoIncrDesc = "YES,NO,NO,NO,NO".split(",");
		String[] primFields = "field-a,field-b,field-c,field-d,field-e".split(",");
		
		CreateTableSQL createSql = new CreateTableSQL();
		createSql.setSqlFieldsDesc(new MySqlFieldsDesc());
		createSql.setUsedFields(usedFields);
		createSql.setFieldsTypes(fieldsTypes);
		createSql.setIsFieldNull(nullDescs);
		createSql.setIsAutoIncr(autoIncrDesc);
		createSql.setDbName("testperf");
		createSql.setTableName("user_base_info");
		createSql.setPrimaryFields(primFields);
		
		
		System.out.println(createSql.generateCreateTableSQL());
		
	}
	
	
}
