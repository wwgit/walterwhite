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
		
		String newPhone = "21010101010";
		String updateSql = "UPDATE user_base_info set phone=" +newPhone
				+ " where phone=" + phone;
		this.doSimpleSql(this.getPool().retrieveConnection(), updateSql);
		
		String delSql = "DELETE FROM user_base_info"
				+ " where phone=" + newPhone;
		this.doSimpleSql(this.getPool().retrieveConnection(), delSql);
		
		//this.doSimpleQuery(this.getPool().retrieveConnection(), querySql);
		
	}
	
//	@Test
	public void simpleQueryTest() {
		
		//System.out.println("\ntesting doSimpleQuery");
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
	
//	@Test
	public void doQueryTest() {
		
		//System.out.println("\ntesting doQuery");
		
		String baseSql = "select * from user_base_info";
		String[] conditions = new String[]{"phone=","mail="};
		String[] andOr = new String[]{"and"};
		Object[] condValues = new Object[]{"13510084082","steveredd@qq.com"};		
		this.doQuery(this.getPool().retrieveConnection(), baseSql, 
				conditions, andOr, condValues);
		boolean colReturned = false;
		try {
			colReturned = areColumnsReturned(new String[]{"phone","mail"});
			org.junit.Assert.assertTrue(this.getFailedMessage(), colReturned);
		} catch (Exception e) {
			reportFailure(e);
			fail(e.getMessage());
		}	
	}
	

	@Test
	public void doUpdateTest() {
		//String baseSql = "update user_base_info";
		String[] condColumns = new String[]{"phone","mail","phone"};
		String[] colTypes = new String[]{"java.lang.String","java.lang.String"};
		String[] whereConds = new String[]{"phone="};
		String[] andOr = new String[]{"and"};
		Object[] condValues = new Object[]{"21212121212","data@qq.com","13510084627"};
		Map<String,Object> columns_values = null;
		try {
			columns_values = BasicHelper.StrObjArrayToHashMap(condColumns, condValues);
			
		} catch (Exception e) {
			reportFailure(e);
		}
		List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
		data.add(columns_values);
		this.doUpdate(this.getPool().retrieveConnection(), "user_base_info",
					  data, whereConds, null);
	}
	
	
	public void reportFailure(Exception e) {
		System.out.println("sql tester reporting a failure:");
		e.printStackTrace();
	}


	public void reportExecuteProcess(String info) {
		System.out.println("sql tester reporting progress: " + info);
	}
	
	
	public void reportResults(ResultSet result) {
		//System.out.println("sql tester reporting  sql result: " + result);
		String testResult = null;
		try {
			while(result.next()) {
				testResult = result.getString("phone");
				System.out.println(testResult);
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
