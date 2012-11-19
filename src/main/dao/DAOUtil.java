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

	// Actions
	// ------------------------------------------------------------------------------------

	/**
	 * Returns a PreparedStatement of the given connection, set with the given
	 * SQL query and the given parameter values.
	 * 
	 * @param connection
	 *            The Connection to create the PreparedStatement from.
	 * @param sql
	 *            The SQL query to construct the PreparedStatement with.
	 * @param returnGeneratedKeys
	 *            Set whether to return generated keys or not.
	 * @param values
	 *            The parameter values to be set in the created
	 *            PreparedStatement.
	 * @throws SQLException
	 *             If something fails during creating the PreparedStatement.
	 */
	public static PreparedStatement prepareStatement(Connection connection, String sql, boolean returnGeneratedKeys, Object... values) throws SQLException {
		PreparedStatement preparedStatement = connection.prepareStatement(sql, returnGeneratedKeys ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS);
		if(values != null && values.length > 0) {
			setValues(preparedStatement, values);
		}
		
		return preparedStatement;
	}

	/**
	 * Set the given parameter values in the given PreparedStatement.
	 * 
	 * @param connection
	 *            The PreparedStatement to set the given parameter values in.
	 * @param values
	 *            The parameter values to be set in the created
	 *            PreparedStatement.
	 * @throws SQLException
	 *             If something fails during setting the PreparedStatement
	 *             values.
	 */
	public static void setValues(PreparedStatement preparedStatement, Object... values) throws SQLException {
		for (int i = 0; i < values.length; i++) {
			preparedStatement.setObject(i + 1, values[i]);
		}
	}

	public static Timestamp sqlTimestampFromDate(java.util.Date date) {
		return(date != null) ? new Timestamp(date.getTime()) : null;
	}
	
	public static java.util.Date dateFromSqlTimestamp(Timestamp timestamp) {
		return (timestamp != null) ? new java.util.Date(timestamp.getTime()) : null;
	}

	/**
	 * Quietly close the Connection. Any errors will be printed to the stderr.
	 * 
	 * @param connection
	 *            The Connection to be closed quietly.
	 */
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

	/**
	 * Quietly close the Statement. Any errors will be printed to the stderr.
	 * 
	 * @param statement
	 *            The Statement to be closed quietly.
	 */
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

	/**
	 * Quietly close the ResultSet. Any errors will be printed to the stderr.
	 * 
	 * @param resultSet
	 *            The ResultSet to be closed quietly.
	 */
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

	/**
	 * Quietly close the Connection and Statement. Any errors will be printed to
	 * the stderr.
	 * 
	 * @param connection
	 *            The Connection to be closed quietly.
	 * @param statement
	 *            The Statement to be closed quietly.
	 */
	public static void close(Connection connection, Statement statement) {
		close(statement);
		close(connection);
	}

	/**
	 * Quietly close the Connection, Statement and ResultSet. Any errors will be
	 * printed to the stderr.
	 * 
	 * @param connection
	 *            The Connection to be closed quietly.
	 * @param statement
	 *            The Statement to be closed quietly.
	 * @param resultSet
	 *            The ResultSet to be closed quietly.
	 */
	public static void close(Connection connection, Statement statement,
			ResultSet resultSet) {
		close(resultSet);
		close(statement);
		close(connection);
	}

}
