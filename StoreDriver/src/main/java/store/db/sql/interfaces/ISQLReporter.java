package store.db.sql.interfaces;

import java.sql.ResultSet;

public interface ISQLReporter {
	
	public abstract void reportFailure(Exception e);
	public abstract void reportExecuteProcess(String info);
	public abstract void reportResults(ResultSet result);
	public abstract void reportResults(int doneCnt);

}
