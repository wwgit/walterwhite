/**   
* @Title: SqlDefinitionTest.java 
* @Package store.db.sql 
* @Description: TODO(what to do) 
* @author walterwhite
* @date 2017年1月18日 下午4:53:42 
* @version V1.0   
*/
package store.db.sql;

import handy.tools.factorties.PropertiesBeanFactory;

import org.junit.Before;
import org.junit.Test;

import store.db.sql.beans.DbConfig;
import store.db.sql.beans.InsertSQL;
import store.db.sql.beans.SQLDefinition;
import store.db.sql.beans.SelectSQL;
import store.db.sql.beans.UpdateSQL;
import store.db.sql.beans.WhereDefinition;

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
		String where = "field-a= or, field-b= and, field-c> or, field-d> or, field-e<>";
		
		WhereDefinition wd = new WhereDefinition();
		wd.setWhereConditions(where);
		Object[] values = new Object[5];
		values[0] = "field-a_value";
		values[1] = 1;
		values[2] = 2;
		values[3] = 3;
		values[4] = 4;
		
		SQLDefinition selectSQL = new SelectSQL();
//		selectSQL.setDbName("testperf");
		selectSQL.setTableName("user_base_info");
		selectSQL.setWhereConditions(wd);
		
		String usedFields = "field-a,field-b,field-c,field-d";
		selectSQL.setUsedFields(usedFields);
		
		System.out.println("select SQL test: " + selectSQL.generatePrepareSQLStatment());
		System.out.println("select SQL test: " + selectSQL.generateSimpleSQL(values));	
		
	}
	
	@Test
	public void InsertSQLTest() throws Exception {
		String usedFields = "field-a,field-b,field-c,field-d,field-e";
		
		SQLDefinition insertSQL = new InsertSQL();
//		insertSQL.setDbName("testperf");
		insertSQL.setTableName("user_base_info");
		insertSQL.setUsedFields(usedFields);
		((InsertSQL)insertSQL).setHowManyFields(5);
		
		Object[] values = new Object[5];
		values[0] = "field-a_value";
		values[1] = 1;
		values[2] = 2;
		values[3] = 3;
		values[4] = 4;
		
//		System.out.println(insertSQL.generatePrepareSQLStatment());
		System.out.println(insertSQL.generateSimpleSQL(values));
		
	}
	
	@Test
	public void UpdateSQLTest() throws Exception {
		
		String usedFields = "field-a,field-b,field-c,field-d,field-e";
		String where = "field-a= or, field-b= and, field-c> or, field-d> or, field-e<>";
		
		WhereDefinition wd = new WhereDefinition();
		wd.setWhereConditions(where);
		SQLDefinition updateSQL = new UpdateSQL();
//		updateSQL.setDbName("testperf");
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
	

}
