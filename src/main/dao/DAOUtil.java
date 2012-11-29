package main.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

public final class DAOUtil {

	private DAOUtil() {
	}

	public static PreparedStatement prepareStatement(Connection connection,
			String sql, boolean returnGeneratedKeys, Object... values)
			throws SQLException {
		PreparedStatement preparedStatement = connection.prepareStatement(sql,
				returnGeneratedKeys
						? Statement.RETURN_GENERATED_KEYS
						: Statement.NO_GENERATED_KEYS);
		if (values != null && values.length > 0) {
			setValues(preparedStatement, values);
		}

		return preparedStatement;
	}

	public static void setValues(PreparedStatement preparedStatement,
			Object... values) throws SQLException {
		for (int i = 0; i < values.length; i++) {
			preparedStatement.setObject(i + 1, values[i]);
		}
	}

	public static Timestamp sqlTimestampFromDate(java.util.Date date) {
		return (date != null) ? new Timestamp(date.getTime()) : null;
	}

	public static java.util.Date dateFromSqlTimestamp(Timestamp timestamp) {
		return (timestamp != null)
				? new java.util.Date(timestamp.getTime())
				: null;
	}

	public static void close(Connection connection) {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				System.err.println("Closing Connection failed: "
						+ e.getMessage());
				e.printStackTrace();
			}
		}
	}

	public static void close(Statement statement) {
		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {
				System.err.println("Closing Statement failed: "
						+ e.getMessage());
				e.printStackTrace();
			}
		}
	}

	public static void close(ResultSet resultSet) {
		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (SQLException e) {
				System.err.println("Closing ResultSet failed: "
						+ e.getMessage());
				e.printStackTrace();
			}
		}
	}

	public static void close(Connection connection, Statement statement) {
		close(statement);
		close(connection);
	}

	public static void close(Connection connection, Statement statement,
			ResultSet resultSet) {
		close(resultSet);
		close(statement);
		close(connection);
	}

}
