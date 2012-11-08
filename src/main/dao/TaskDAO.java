package main.dao;

import static main.dao.DAOUtil.close;
import static main.dao.DAOUtil.dateFromSqlTimestamp;
import static main.dao.DAOUtil.prepareStatement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import main.dto.Task;
import main.dto.User;
import main.exceptions.DAOException;

public class TaskDAO {
	private DAOFactory daoFactory;

	private static final String SQL_FIND_BY_OWNER = "SELECT * FROM Task WHERE Owner_UserId = ?";
	private static final String SQL_FIND_BY_ID = "SELECT * FROM Task WHERE TaskId = ?";
	private static final String SQL_FIND_ALL = "SELECT * FROM Task";

	public TaskDAO(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	private Task find(String sql, Object... values) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Task task = null;
		try {
			connection = daoFactory.getConnection();
			preparedStatement = prepareStatement(connection, sql, false, values);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				task = map(resultSet);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			close(connection, preparedStatement, resultSet);
		}

		return task;
	}

	private List<Task> findMany(String sql, Object... values) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Task task = null;
		List<Task> tasks = new ArrayList<Task>();
		try {
			connection = daoFactory.getConnection();
			preparedStatement = prepareStatement(connection, sql, false, values);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				task = map(resultSet);
				tasks.add(task);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			close(connection, preparedStatement, resultSet);
		}

		return tasks;
	}
	
	public Task getById(int taskId) {
		return find(SQL_FIND_BY_ID, taskId);
	}
	
	public List<Task> getByOwner(User owner) {
		return findMany(SQL_FIND_BY_OWNER, owner.getUserId());
	}
	
	public List<Task> getAll() {
		return findMany(SQL_FIND_ALL, null);
	}

	private static Task map(ResultSet rs) throws SQLException {
		Task task = new Task(rs.getInt("TaskId"));
		task.setTitle(rs.getString("Title"));
		task.setDescription(rs.getString("Description"));
		task.setPriority(rs.getInt("Priority"));
		task.setStatus(rs.getInt("Status"));
		task.setDeadline(dateFromSqlTimestamp(rs.getTimestamp("Deadline")));
		task.setOwner(rs.getInt("Owner_UserId"));
		task.setEstimatedTime(rs.getInt("EstimatedTime"));
		task.setCreatedAt(dateFromSqlTimestamp(rs.getTimestamp("CreatedAt")));
		task.setUpdatedAt(dateFromSqlTimestamp(rs.getTimestamp("UpdatedAt")));

		task.setGroup(rs.getInt("Group_GroupId"));
		task.setProject(rs.getInt("Project_ProjectId"));
		task.setParentTask(rs.getInt("ParentId"));

		return task;
	}
}
