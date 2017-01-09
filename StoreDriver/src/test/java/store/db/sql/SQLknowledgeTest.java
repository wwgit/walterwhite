package store.db.sql;

import static org.junit.Assert.*;
import handy.tools.factorties.PropertiesBeanFactory;
import handy.tools.factorties.XmlBeanFactory;
import handy.tools.helpers.BasicHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import store.db.sql.beans.DbConfig;
import store.db.sql.interfaces.ISQLReporter;
import store.db.sql.interfaces.SqlKnowledge;
import store.db.sql.testdata.TestDataCreator;

/** 
* @ClassName: SQLknowledgeTest 
* @Description: TODO(what to do) 
* @author walterwhite
* @date 2017年1月9日 下午6:36:37 
*  
*/
public class SQLknowledgeTest extends TestCaseAbstract implements ISQLReporter {

	
	@Before
	public void setUp() throws Exception {
		this.setBeanCreator(new PropertiesBeanFactory("configs/db_config_test.properties"));
		this.setConfig((DbConfig) this.getBeanCreator().getBean("dbConfig"));
		this.setPool(new DbPool());
		this.getPool().initConnections(this.getConfig(), this);
	}
	
//	@Test
	public void testSimpleSql() {
		System.out.println("\ntesting doSimpleSql inserting");
		String phone = "31010101010";
		String sql = "INSERT INTO user_base_info (acct_name,phone,mail,pwd) VALUES(\"insertuser\"," + phone + ",\"mysql@163.com\",\"junittest\")";
		this.doSimpleSql(this.getPool().retrieveConnection(), sql);
		
		String querySql = "select * from user_base_info"
				+ " where phone=" + phone;
		//this.doSimpleQuery(this.getPool().retrieveConnection(), querySql);
		
		String newPhone = "11313";
		String updateSql = "UPDATE user_base_info set phone=" + newPhone
				+ ",acct_name='simpleName' where pwd='autopwd'";
		this.doSimpleSql(this.getPool().retrieveConnection(), updateSql);
		
		String delSql = "DELETE FROM user_base_info where pwd='autophone'";
//				+ " where phone=" + newPhone;
		this.doSimpleSql(this.getPool().retrieveConnection(), delSql);
		
		//this.doSimpleQuery(this.getPool().retrieveConnection(), querySql);
		
	}
	
//	@Test
	public void simpleQueryTest() {
		
		String sql = "select * from user_base_info "
					+ "where phone=\'13510084082\' and mail='steveredd@qq.com'";
		boolean colReturned = false;
		this.doSimpleQuery(this.getPool().retrieveConnection(), sql);
		try {
			colReturned = areColumnsReturned(new String[]{"phone","mail"});
			org.junit.Assert.assertTrue(this.getFailedMessage(), colReturned);
		} catch (Exception e) {
			reportFailure(e);
			fail(e.getMessage());
		}		
		
	}
	
	@Test
	public void doQueryTest() {
		
		String baseSql = "select * from user_base_info";

		Object[] condValues = new Object[]{"simpleName","steveredd@qq.com"};		
		this.doPrepareQuery(this.getPool().retrieveConnection(), baseSql, null);
		boolean colReturned = false;
		try {
			colReturned = areColumnsReturned(new String[]{"phone","mail"});
			org.junit.Assert.assertTrue(this.getFailedMessage(), colReturned);
		} catch (Exception e) {
			reportFailure(e);
			fail(e.getMessage());
		}	
	}
	

//	@Test
	public void doInsertTest() {
		//String baseSql = "update user_base_info";
		String[] condColumns = new String[]{"phone","pwd","acct_name"};
		String[] colTypes = new String[]{"java.lang.String","java.lang.String","java.lang.String"};
		String[] whereConds = new String[]{"phone="};
		String[] andOr = new String[]{"and"};
		Object[] condValues = new Object[]{"212","junitpwd","junitname"};
		Map<String,Object> columns_values = null;
		Map<String,String> columns_types = null;
		List<Map<String,Object>> batchData = null;
		try {
			columns_values = BasicHelper.StrObjArrayToHashMap(condColumns, condValues);
			columns_types = BasicHelper.StrArrayToHashMap(condColumns, colTypes);
			batchData = TestDataCreator.rowDataGenerateUTF8(300, columns_types);
		} catch (Exception e) {
			reportFailure(e);
		}
//		List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
//		data.add(columns_values);
		this.doInsert(this.getPool().retrieveConnection(), "user_base_info", batchData);
	}
	
//	@Test
	public void doUpdateTest() {
		Object[] condValues = new Object[]{"21asdf","junitpwffd","junitnaasdfme"};
		String updateSql = "update user_base_info set phone=?,pwd=?,acct_name=? where pwd=?";
		String deleteSql = "delete from user_base_info where phone=? and pwd=? and acct_name=?";
		this.doPrepareSql(this.getPool().retrieveConnection(), deleteSql, condValues);
	}
	
	
	public void reportFailure(Exception e) {
		System.out.println("sql tester reporting a failure:");
		e.printStackTrace();
	}


	public void reportExecuteProcess(String info) {
		System.out.println("sql tester reporting progress: " + info);
	}
	
	
	public void reportResults(ResultSet result) {

		String testResult = null;
		try {

			//result.last();
			//System.out.println("sql tester reporting  total count return: " + result.getRow());
			//result.first();
			int i = 0;
			while(result.next()) {
				testResult = (String) result.getObject("phone");
				System.out.println(testResult + i++);
			}

		} catch (SQLException e) {
			reportFailure(e);
		}
		this.setCurrentResult(result);
	}

	public void reportResults(int doneCnt) {
		System.out.println("sql tester reporting  sql result: " + doneCnt);
		this.setCurrentResult(doneCnt);
	}


	public void returnResources(Connection conn, PreparedStatement statement) {
		/*try {
			statement.close();
			this.getPool().returnConnection(conn);
		} catch (SQLException e) {
			reportFailure(e);
		}*/
		this.getPool().returnConnection(conn);
	}

	@Override
	public void returnResources(Connection conn, Statement statement) {
		/*try {
			statement.close();
			this.getPool().returnConnection(conn);
		} catch (SQLException e) {
			reportFailure(e);
		}*/
		this.getPool().returnConnection(conn);
	}



	

}
