package store.db.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import store.db.sql.interfaces.SqlKnowledge;

public class TestCaseAbstract extends SqlKnowledge {

	@Override
	public void reportFailure(Exception e) {
		e.printStackTrace();
	}

	@Override
	public void reportExecuteProcess(String info) {
		System.out.println(info);
	}

	@Override
	public void reportResults(ResultSet result) {
		System.out.println(result);
	}

	@Override
	public void reportResults(int doneCnt) {
		System.out.println(doneCnt);
	}

	@Override
	public void returnResources(Connection conn, PreparedStatement statement) {
		try {
			statement.close();
			conn.close();
		} catch (SQLException e) {
			reportFailure(e);
		}
		
	}

	@Override
	public void returnResources(Connection conn, Statement statement) {
		try {
			statement.close();
			conn.close();
		} catch (SQLException e) {
			reportFailure(e);
		}
		
	}

}
