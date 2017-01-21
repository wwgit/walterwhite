/**   
* @Title: SqlDefinitionTest.java 
* @Package store.db.sql 
* @Description: TODO(what to do) 
* @author walterwhite
* @date 2017年1月18日 下午4:53:42 
* @version V1.0   
*/
package store.db.sql;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import store.db.sql.beans.definitions.MySqlCreateSQL;
import store.db.sql.beans.definitions.SQLDefinition;
import store.db.sql.beans.definitions.CreateTableSQL;
import store.db.sql.beans.definitions.DeleteSQL;
import store.db.sql.beans.definitions.InsertSQL;
import store.db.sql.beans.definitions.SelectSQL;
import store.db.sql.beans.definitions.UpdateSQL;
import store.db.sql.beans.definitions.WhereDefinition;
import store.db.sql.beans.definitions.constraints.Constraint;
import store.db.sql.beans.definitions.constraints.ForeignKey;
import store.db.sql.beans.definitions.constraints.PrimaryKey;

/** 
 * @ClassName: SqlDefinitionTest 
 * @Description: TODO(what to do) 
 * @author walterwhite
 * @date 2017年1月18日 下午4:53:42 
 *  
 */
public class SqlDefinitionTest {
	
//	@Before
	public void setUp() throws Exception {
		
	}
	
//	@Test
	public void SelectSQLTest() throws Exception {
		
		String[] where = "field-a= or, field-b= and, field-c> or, field-d> or, field-e<>".split(",");		
		Object[] values = new Object[5];
		values[0] = "field-a_value";
		values[1] = 1;
		values[2] = 2;
		values[3] = 3;
		values[4] = 4;
		
		WhereDefinition wd = new WhereDefinition();
		wd.setWhereValues(values);
		wd.setWhereConditions(where);
		
		SQLDefinition selectSQL = new SelectSQL();
		selectSQL.setDbName("testperf");
		selectSQL.setTableName("user_base_info");
		selectSQL.setWhereConditions(wd);
		
		String usedFields = "field-a,field-b,field-c,field-d";
//		selectSQL.setUsedFields(usedFields);
		
		System.out.println(selectSQL.generatePrepareSQLStatment());
		System.out.println(selectSQL.generateSimpleSQL(values));	
		
	}
	
//	@Test
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
	
//	@Test
	public void UpdateSQLTest() throws Exception {
		
		String[] usedFields = "field-a,field-b,field-c,field-d,field-e".split(",");
		String[] where = "field-a= or, field-b= and, field-c> or, field-d> or, field-e<>".split(",");
		
		Object[] values = new Object[5];
		values[0] = "where_values";
		values[1] = 1;
		values[2] = 2;
		values[3] = 3;
		values[4] = 4;
		
		WhereDefinition wd = new WhereDefinition();
		wd.setWhereValues(values);
		wd.setWhereConditions(where);
		SQLDefinition updateSQL = new UpdateSQL();
		updateSQL.setDbName("testperf");
		updateSQL.setTableName("user_base_info");
		updateSQL.setWhereConditions(wd);
		updateSQL.setUsedFields(usedFields);
			
		Object[] fields_values = new Object[5];
		fields_values[0] = "fields_values";
		fields_values[1] = 1;
		fields_values[2] = 2;
		fields_values[3] = 3;
		fields_values[4] = 4;
		
		System.out.println(updateSQL.generatePrepareSQLStatment());
		System.out.println(
				((UpdateSQL)updateSQL).generateSimpleUpdate(fields_values)
				);
		
	}
	
//	@Test
	public void DeleteSQLTest() throws Exception {
		String[] where = "field-a= or, field-b= and, field-c> or, field-d> or, field-e<>".split(",");		
		Object[] values = new Object[5];
		values[0] = "field-a_value";
		values[1] = 1;
		values[2] = 2;
		values[3] = 3;
		values[4] = 4;
		
		WhereDefinition wd = new WhereDefinition();
		wd.setWhereValues(values);
		wd.setWhereConditions(where);
		SQLDefinition deleteSQL = new DeleteSQL();
		
		deleteSQL.setDbName("testperf");
		deleteSQL.setTableName("user_base_info");
		deleteSQL.setWhereConditions(wd);
		

		
		System.out.println(deleteSQL.generatePrepareSQLStatment());
		System.out.println(deleteSQL.generateSimpleSQL(values));
		
	}
	
//	@Test
	public void CreateTableSQLTest() throws Exception {
		
		String[] usedFields = "field1,field2,field3,field4,field5".split(",");
		String[] fieldsTypes = "INT,VARCHAR(255),VARCHAR(255),VARCHAR(255),VARCHAR(255)".split(",");
		String[] nullDescs = "YES,NO,DEFAULT,YES,YES".split(",");
		String[] autoIncrDesc = "YES,NO,NO,NO,NO".split(",");
		String[] primFields = "field-a,field-b,field-c,field-d,field-e".split(",");
		
		List<Constraint> constrs = new ArrayList<Constraint>();
		PrimaryKey pk = new PrimaryKey();
		pk.setConstrName("this_pk");
		pk.setConstrFields("field1".split(","));
		
		ForeignKey fk = new ForeignKey();
		fk.setConstrName("this_fk");
		fk.setConstrFields("field1".split(","));
		fk.setRefTableName("user_base_info3");
		fk.setRefTableFields("field1".split(","));
		
		constrs.add(pk);
		constrs.add(fk);
		
		CreateTableSQL createSql = new MySqlCreateSQL();
		createSql.setUsedFields(usedFields);
		createSql.setFieldsTypes(fieldsTypes);
		createSql.setIsFieldNull(nullDescs);
		createSql.setIsAutoIncr(autoIncrDesc);
		createSql.setDbName("testperf");
		createSql.setTableName("user_base_info4");
		createSql.setConstraints(constrs);
		
		System.out.println(createSql.generateCreateTableSQL());
		
	}
	
	
}
