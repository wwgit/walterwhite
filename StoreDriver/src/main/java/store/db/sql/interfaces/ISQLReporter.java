package store.db.sql.interfaces;

import java.sql.ResultSet;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public interface ISQLReporter {
	
	public abstract void reportFailure(Exception e);
	public abstract void reportExecuteProcess(String info);
	public abstract void reportQueryResults(Map<Object, List<List<Object>>> result);
	public abstract void reportUpdateResults(int doneCnt);

}
