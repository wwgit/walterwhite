package store.db.sql;

import static org.junit.Assert.*;
import handy.tools.factorties.PropertiesBeanFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Before;
import org.junit.Test;

import store.db.sql.beans.DbConfig;
import store.db.sql.interfaces.ISQLReporter;
import store.db.sql.interfaces.SqlKnowledge;

public class SQLknowledgeChecker extends TestCaseAbstract implements ISQLReporter {
	
	@Before
	public void setUp() throws Exception {
		this.setBeanCreator(new PropertiesBeanFactory("configs/db_config_test.properties"));
		this.setConfig((DbConfig) this.getBeanCreator().getBean("dbConfig"));
		this.setPool(new DbPool());
		this.getPool().initConnections(this.getConfig(), this);
	}
	
	@Test
	public void test() {
		
		System.out.println("\ntesting doSimpleQuery");
		String sql = "select * from user";
		this.doSimpleQuery(this.getPool().retrieveConnection(), sql);
		
		Connection conn_2 = this.getPool().retrieveConnection();
		System.out.println("\ntesting doQuery");
		String baseSql = sql;
		String[] conditions = new String[]{"Host=","User="};
		String[] andOr = new String[]{"and"};
		Object[] condValues = new Object[]{"%","root"};		
		this.doQuery(conn_2, baseSql, conditions, andOr, condValues);
	}
	
	
	public void reportFailure(Exception e) {
		System.out.println("sql tester reporting a failure:");
		e.printStackTrace();
	}


	public void reportExecuteProcess(String info) {
		System.out.println("sql tester reporting progress: " + info);
	}
	
	
	public void reportResults(ResultSet result) {
		System.out.println("sql tester reporting  sql result: " + result);
		String testResult = null;
		try {
			while(result.next()) {
				testResult = result.getString("User");
				System.out.println(testResult);
				//System.out.println(result.getClass());
				//assertEquals(testResult,"root");
			}

		} catch (SQLException e) {
			reportFailure(e);
		}
		//
	}

	public void reportResults(int doneCnt) {
		System.out.println("sql tester reporting  sql result: " + doneCnt);
	}


	public void returnResources(Connection conn, PreparedStatement statement) {
		try {
			statement.close();
			this.getPool().returnConnection(conn);
		} catch (SQLException e) {
			reportFailure(e);
		}
	}

	@Override
	public void returnResources(Connection conn, Statement statement) {
		try {
			statement.close();
			this.getPool().returnConnection(conn);
		} catch (SQLException e) {
			reportFailure(e);
		}
	}

	

}
