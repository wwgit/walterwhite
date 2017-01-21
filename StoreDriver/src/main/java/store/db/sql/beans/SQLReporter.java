package store.db.sql.beans;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.LinkedBlockingQueue;

import store.db.sql.interfaces.ISQLReporter;

public class SQLReporter implements ISQLReporter {
	
	/** 
	* @Fields reporterGetQueue : 
	* SQLDriver initializes this queue 
	* Robots write reports and SQL results to this queue.
	* SQLReporter read reports and SQL results from this queue
	*/ 
	private LinkedBlockingQueue<Object> reporterQueue;
	
	public SQLReporter() {
		this.reporterQueue = new LinkedBlockingQueue<Object>();
	}
	
	public void reportsHandling() {
		try {
			Object report = this.reporterQueue.take();
			if(report instanceof String) {
				reportExecuteProcess(String.valueOf(report));
			}
			if(report instanceof Exception) {
				reportFailure((Exception)report);
			}
			if(report instanceof Integer) {
				reportUpdateResults(Integer.parseInt(String.valueOf(report)));
			}
			if(report instanceof TreeMap) {
				reportQueryResults((Map<Object, List<List<Object>>>) report);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}


	public void reportFailure(Exception e) {
		System.out.println("sql reporter reporting a failure:");
		e.printStackTrace();
	}


	public void reportExecuteProcess(String info) {
		System.out.println("sql reporter reporting progress: " + info);
	}


	public void reportQueryResults(Map<Object, List<List<Object>>> results) {
		System.out.println("sql reporter reporting  sql result: " + results);
		
	}


	public void reportUpdateResults(int doneCnt) {
		System.out.println("sql reporter reporting  sql result: " + doneCnt);
	}


	public LinkedBlockingQueue<Object> getReporterQueue() {
		return reporterQueue;
	}


	public void setReporterQueue(LinkedBlockingQueue<Object> reporterQueue) {
		this.reporterQueue = reporterQueue;
	}
	
}
