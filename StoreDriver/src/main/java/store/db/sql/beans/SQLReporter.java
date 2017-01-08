package store.db.sql.beans;

import java.sql.ResultSet;

import store.db.sql.interfaces.ISQLReporter;

public class SQLReporter implements ISQLReporter {


	public void reportFailure(Exception e) {
		System.out.println("sql reporter reporting a failure:");
		e.printStackTrace();
	}


	public void reportExecuteProcess(String info) {
		System.out.println("sql reporter reporting progress: " + info);
	}


	public void reportResults(ResultSet result) {
		System.out.println("sql reporter reporting  sql result: " + result);
	}


	public void reportResults(int doneCnt) {
		System.out.println("sql reporter reporting  sql result: " + doneCnt);
	}

	
	
}
