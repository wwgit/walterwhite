package store.db.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import handy.tools.factorties.PropertiesBeanFactory;
import handy.tools.interfaces.bean.BeanFactory;
import store.db.sql.beans.DbConfig;
import store.db.sql.beans.SQLReporter;
import store.db.sql.interfaces.ISQLReporter;
import store.db.sql.interfaces.SqlKnowledge;

public abstract class TestCaseAbstract extends SqlKnowledge {

	private DbConfig config;
	private DbPool pool;
	private BeanFactory beanCreator;
	private Object currentResult;
	private String failedMessage;
	
	private SQLReporter reporter;
	
	
	public TestCaseAbstract() {
		
		this.reporter = new SQLReporter();
		this.setBeanCreator(new PropertiesBeanFactory("configs/db_config_test.properties"));
		this.setConfig((DbConfig) this.getBeanCreator().getBean("dbConfig"));
		this.setPool(new DbPool());
		this.getPool().initConnections(this.getConfig(),reporter);
	}
	
	public void printReports() {
		while(this.reporter.getReporterQueue().isEmpty() == false) {
			this.reporter.reportsHandling();
		}
	}
	
	
	public Object getCurrentResult() {
		return currentResult;
	}

	public void setCurrentResult(Object currentResult) {
		this.currentResult = currentResult;
	}

	public DbConfig getConfig() {
		return config;
	}


	public void setConfig(DbConfig config) {
		this.config = config;
	}


	public DbPool getPool() {
		return pool;
	}


	public void setPool(DbPool pool) {
		this.pool = pool;
	}


	public BeanFactory getBeanCreator() {
		return beanCreator;
	}


	public void setBeanCreator(BeanFactory beanCreator) {
		this.beanCreator = beanCreator;
	}
	
	public SQLReporter getReporter() {
		return reporter;
	}

	public void setReporter(SQLReporter reporter) {
		this.reporter = reporter;
	}
	
	public boolean areColumnsReturned(String[] colNames) throws Exception {
		
		ResultSet queryResult = null;
		
		queryResult = (ResultSet) this.getCurrentResult();
		
		int colCntReturned = queryResult.getMetaData().getColumnCount();	

		boolean isColFound = false;
		StringBuilder sb = new StringBuilder("Columns:");
		for(int j = 0; j < colNames.length; j++) {
			
			String actRtnColName = null;
			for(int i = 1; i <= colCntReturned; i++) {
				actRtnColName = queryResult.getMetaData().getColumnName(i);			
				if(colNames[j].equals(actRtnColName)) {
					isColFound = true;
					sb.append(colNames[j]);
					sb.append(" found\n");
					break;
				} else {
					isColFound = false;
				}
			}
			//only if one column not found does this checking failed
			if(!isColFound) {
				sb.append(colNames[j]);
				sb.append(" not found\n");
				this.setFailedMessage("at least one of column not found\n" + 
				"details are as follows: \n" + sb.toString());
				return isColFound;
			}
		}	
		
		return isColFound;
	}
	
	

	public String getFailedMessage() {
		return failedMessage;
	}

	public void setFailedMessage(String failedMessage) {
		this.failedMessage = failedMessage;
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

	/* (non-Javadoc)
	 * @see store.db.sql.interfaces.ISQLReporter#reportQueryResults(java.util.Map)
	 */
	public void reportQueryResults(Map<Object, List<List<Object>>> result) {
	}

	/* (non-Javadoc)
	 * @see store.db.sql.interfaces.ISQLReporter#reportUpdateResults(int)
	 */
	public void reportUpdateResults(int doneCnt) {
	}
	
	/* (non-Javadoc)
	 * @see store.db.sql.interfaces.SqlKnowledge#reportFailure(java.lang.Exception)
	 */
	@Override
	protected void reportFailure(Exception e) {
	}

	/* (non-Javadoc)
	 * @see store.db.sql.interfaces.SqlKnowledge#reportExecuteProcess(java.lang.String)
	 */
	@Override
	protected void reportExecuteProcess(String info) {
	}

	/* (non-Javadoc)
	 * @see store.db.sql.interfaces.SqlKnowledge#reportResults(java.sql.ResultSet)
	 */
	@Override
	protected void reportResults(ResultSet result) {
	}

	/* (non-Javadoc)
	 * @see store.db.sql.interfaces.SqlKnowledge#reportResults(int)
	 */
	@Override
	protected void reportResults(int doneCnt) {
	}

}
