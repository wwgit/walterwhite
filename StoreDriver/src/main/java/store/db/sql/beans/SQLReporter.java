package store.db.sql.beans;

import java.sql.ResultSet;

import store.db.sql.interfaces.ISQLReporter;

public class SQLReporter implements ISQLReporter{


	public void reportFailure(Exception e) {
		e.printStackTrace();
	}


	public void reportExecuteProcess(String info) {
		System.out.println(info);
	}


	public void reportResults(ResultSet result) {
		System.out.println(result);
	}


	public void reportResults(int doneCnt) {
		System.out.println(doneCnt);
	}

	
	
}
