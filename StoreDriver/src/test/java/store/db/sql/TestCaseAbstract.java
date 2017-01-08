package store.db.sql;

import handy.tools.interfaces.bean.BeanFactory;

import store.db.sql.beans.DbConfig;
import store.db.sql.interfaces.SqlKnowledge;

public abstract class TestCaseAbstract extends SqlKnowledge {

	private DbConfig config;
	private DbPool pool;
	private BeanFactory beanCreator;

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


}
