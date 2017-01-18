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
import store.db.sql.beans.SQLDefinition;
import store.db.sql.beans.SelectSQL;
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
	public void whereDefineTest() {
		String where = "field-a= or, field-b= and, field-c> or, field-d> or, field-e<>";
		WhereDefinition wd = new WhereDefinition();
		wd.setWhereConditions(where);
		System.out.println(wd.generateWhereConditions());
	}
	
	@Test
	public void whereDefineSimpleSqlTest() throws Exception {
		String where = "field-a= or, field-b= and, field-c> or, field-d> or, field-e<>";
		Object[] values = new Object[5];
		values[0] = "field-a_value";
		values[1] = 1;
		values[2] = 2;
		values[3] = 3;
		values[4] = 4;
		
		WhereDefinition wd = new WhereDefinition();
		wd.setWhereConditions(where);
		
		System.out.println(wd.generateSimpleWhere(values));
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
//		selectSQL.setUsedFields(usedFields);
		
		System.out.println("select SQL test: " + selectSQL.generatePrepareSQLStatment());
		System.out.println("select SQL test: " + selectSQL.generateSimpleSQL(values));	
		
	}
	

}
