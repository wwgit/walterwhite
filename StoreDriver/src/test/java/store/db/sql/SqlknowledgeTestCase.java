package store.db.sql;

import handy.tools.factorties.PropertiesBeanFactory;
import handy.tools.factorties.XmlBeanFactory;
import handy.tools.interfaces.bean.BeanFactory;

import java.sql.Connection;

import org.junit.Before;
import org.junit.Test;

import store.db.sql.beans.DbConfig;
import store.db.sql.beans.SQLReporter;
import store.db.sql.interfaces.ISQLReporter;

public class SqlknowledgeTestCase extends TestCaseAbstract {

	private Connection conn;
	private DbConfig config;
	private DbPool pool;
	private ISQLReporter reporter;
	
	@Before
	public void setUp() throws Exception {
		
		BeanFactory beanCreator = new PropertiesBeanFactory();
		
		beanCreator.loadBeans("configs/db_config.properties");
			
		this.config = (DbConfig) beanCreator.getBean("dbConfig");
		
		this.pool = new DbPool();
		this.reporter = new SQLReporter();
		
		pool.initConnections(config,reporter);
		this.conn = pool.retrieveConnection();
		
	}
	
	@Test
	public void test() {
		
		System.out.println("hello this is junit testing time !");
		String sql = "select * from mysql.user";
		this.doSimpleQuery(conn, sql);
		
	}
	
	

}
