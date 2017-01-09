package store.db.sql.verify;

import java.sql.ResultSet;
import java.sql.SQLException;

/** 
* @ClassName: SqlknowledgeVerifyLogics 
* @Description: TODO(what to do) 
* @author walterwhite
* @date 2017年1月9日 下午2:07:34 
*  
*/

public abstract class SqlknowledgeVerifyLogics {
	
	private Object currentResult;
	private String failedMessage;
	private String warningMessage;
	
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
	
	protected boolean hasExpectedValue(ResultSet result, Object expectedValue, String colName) throws SQLException {
		
		Object actValue = null;
		while(result.next()) {
			actValue = result.getObject(colName);
			if(expectedValue.equals(actValue)) {
				return true;
			}
		}
			
		return false;	
	}
	
	public boolean isDataSatisified(String whereColName, String operator, 
									Object expecteValue) throws Exception {
		
		
		
		return false;
	}
	
	public String getFailedMessage() {
		return failedMessage;
	}

	public void setFailedMessage(String failedMessage) {
		this.failedMessage = failedMessage;
	}
	
	public Object getCurrentResult() {
		return currentResult;
	}

	public void setCurrentResult(Object currentResult) {
		this.currentResult = currentResult;
	}




	public String getWarningMessage() {
		return warningMessage;
	}



	
	public void setWarningMessage(String warningMessage) {
		this.warningMessage = warningMessage;
	}
	
	
}
