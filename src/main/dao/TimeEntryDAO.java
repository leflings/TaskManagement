package main.dao;

import static main.dao.DAOUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import main.dto.Task;
import main.dto.TimeEntry;
import main.dto.User;
import main.exceptions.DAOException;

public class TimeEntryDAO extends BaseDAO {

	private static final String SQL_FIND_ALL = "SELECT * FROM TimeEntry";
	private static final String SQL_FIND_BY_USER = "SELECT * FROM TimeEntry WHERE te_UserId = ?";
	private static final String SQL_FIND_BY_TASK = "SELECT * FROM TimeEntry WHERE te_TaskId = ?";
	private static final String SQL_INSERT = "INSERT INTO TimeEntry(te_TaskId, te_UserId, te_Duration, te_Date) VALUES (?, ?, ?, ?)";
	private static final String SQL_DELETE = "DELETE FROM TimeEntry WHERE te_TimeEntryId = ?";

	protected TimeEntryDAO(DAOFactory daoFactory) {
		super(daoFactory);
	}

	@SuppressWarnings("unused")
	private TimeEntry find(String sql, Object ... values) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		TimeEntry timeentry = null;
		try {
			connection = daoFactory.getConnection();
			preparedStatement = prepareStatement(connection, sql, false, values);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				timeentry = map(resultSet);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			close(connection, preparedStatement, resultSet);
		}

		return timeentry;
	}
	
	private List<TimeEntry> findMany(String sql) {
		return findMany(sql, new Object[0]);
	}
	
	private List<TimeEntry> findMany(String sql, Object ... values) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<TimeEntry> timeentries = new ArrayList<TimeEntry>();
		TimeEntry timeentry = null;
		try {
			connection = daoFactory.getConnection();
			preparedStatement = prepareStatement(connection, sql, false, values);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				timeentry = map(resultSet);
				timeentries.add(timeentry);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			close(connection, preparedStatement, resultSet);
		}

		return timeentries;
	}
	
	public List<TimeEntry> getAll() {
		return findMany(SQL_FIND_ALL);
	}
	
	public List<TimeEntry> getByUser(User user) {
		return findMany(SQL_FIND_BY_USER, user.getUserId());
	}
	
	public List<TimeEntry> getByTask(Task task) {
		return findMany(SQL_FIND_BY_TASK, task.getTaskId());
	}
	
	public void delete(TimeEntry timeEntry) {
		executeUpdate(SQL_DELETE, timeEntry.getId());
	}
	
	public void insert(TimeEntry timeEntry) {
		if (timeEntry.getId() != 0) {
			throw new IllegalArgumentException("TimeEntry is already created. Id is not 0");
		}
		
		Object[] values = {
			timeEntry.getTask().getTaskId(),
			timeEntry.getUser().getUserId(),
			timeEntry.getDuration(),
			sqlTimestampFromDate(timeEntry.getDate())
		};
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet generatedKeys = null;

		try {
			connection = daoFactory.getConnection();
			preparedStatement = prepareStatement(connection, SQL_INSERT, true, values);
			int affectedRows = preparedStatement.executeUpdate();
			if (affectedRows == 0) {
				throw new DAOException("Creating task failed, no rows affected.");
			}
			generatedKeys = preparedStatement.getGeneratedKeys();
			if (generatedKeys.next()) {
				timeEntry.setTimeEntryId(generatedKeys.getInt(1));
			} else {
				throw new DAOException("Creating task failed, no generated key obtained.");
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			close(connection, preparedStatement, generatedKeys);
		}
	}
	
	private static TimeEntry map(ResultSet rs) throws SQLException {
		TimeEntry timeentry = new TimeEntry(rs.getInt("te_TimeEntryId"));
		timeentry.setUser(rs.getInt("te_UserId"));
		timeentry.setTask(rs.getInt("te_TaskId"));
		timeentry.setDuration(rs.getInt("te_Duration"));
		timeentry.setDate(dateFromSqlTimestamp(rs.getTimestamp("te_Date")));
		
		return timeentry;
	}
}
