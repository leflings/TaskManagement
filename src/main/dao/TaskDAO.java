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

import main.dto.Group;
import main.dto.Project;
import main.dto.Task;
import main.dto.User;
import main.enums.Priority;
import main.enums.Status;
import main.exceptions.DAOException;

public class TaskDAO extends BaseDAO {

	private static final String SQL_FIND_BY_COLLABORATION = "SELECT t.* FROM Task t INNER JOIN TaskAssignment ta ON ta.Task_TaskId = t.TaskId AND ta.User_UserId = ?";
	private static final String SQL_FIND_BY_OWNER = "SELECT * FROM Task WHERE Owner_UserId = ?";
	private static final String SQL_FIND_BY_PARENT_ID = "SELECT * FROM Task WHERE ParentId = ?";
	private static final String SQL_FIND_BY_ROOT_ID = "SELECT * FROM Task WHERE RootId = ?";
	private static final String SQL_FIND_BY_ID = "SELECT * FROM Task WHERE TaskId = ?";
	private static final String SQL_FIND_ALL = "SELECT * FROM Task";
	private static final String SQL_FIND_BY_PROJECT = "SELECT * FROM Task WHERE Project_ProjectId = ?";
	private static final String SQL_FIND_BY_GROUP = "SELECT * FROM Task WHERE Group_GroupId = ?";
	
	private static final String SQL_UPDATE = "UPDATE Task SET Title = ?, Description = ?, Priority = ?, Status = ?, Deadline = ?, Owner_UserId = ?, EstimatedTime = ?, Updated = CURRENT_TIMESTAMP, Group_GroupId = ?, Project_ProjectId = ?, ParentId = ?, RootId = ? WHERE TaskId = ?";
	private static final String SQL_INSERT = "INSERT INTO Task (Title, Description, Priority, Status, Deadline, Owner_UserId, EstimatedTime, Group_GroupId, Project_ProjectId, ParentId, RootId) VALUES (?, ?, ?, ? ,?, ?, ?, ?, ?, ?, ?)";

	protected TaskDAO(DAOFactory daoFactory) {
		super(daoFactory);
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
			while (resultSet.next()) {
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

	public List<Task> getByProject(Project project) {
		return findMany(SQL_FIND_BY_PROJECT, project.getProjectId());
	}
	
	public List<Task> getByGroup(Group group) {
		return findMany(SQL_FIND_BY_GROUP, group.getGroupId());
	}
	
	public List<Task> getByParentTask(Task task) {
		return findMany(SQL_FIND_BY_PARENT_ID, task.getTaskId());
	}
	
	public List<Task> getByRootTask(Task task) {
		return findMany(SQL_FIND_BY_ROOT_ID, task.getTaskId());
	}
	
	public List<Task> getByCollaboration(User user) {
		return findMany(SQL_FIND_BY_COLLABORATION, user.getUserId());
	}
	
	public void update(Task task) {
		if(task.getTaskId() != 0) {
			executeUpdate(SQL_UPDATE,
					task.getTitle(),
					task.getDescription(),
					task.getPriority(),
					task.getStatus(),
					DAOUtil.sqlTimestampFromDate(task.getDeadline()),
					task.getOwner().getUserId(),
					task.getEstimatedTime(),
					(task.getGroup() == null) ? null : task.getGroup().getGroupId(),
					(task.getProject() == null) ? null : task.getProject().getProjectId(),
					(task.getParentTask() == null) ? null : task.getParentTask().getTaskId(),
					(task.getRootTask() == null) ? null : task.getRootTask().getTaskId(),
					task.getTaskId()
				);
		}
					
					
	}
	
	public void insert(Task task) {
		if (task.getTaskId() != 0) {
			throw new IllegalArgumentException("User is already created. UserId is not 0");
		}

		Object values[] = {
				task.getTitle(),
				task.getDescription(),
				task.getPriority().getCode(),
				task.getStatus().getCode(),
				task.getDeadline(),
				task.getOwner().getUserId(),
				task.getEstimatedTime(),
				((task.getGroup() == null) ? null : task.getGroup().getGroupId()),
				((task.getProject() == null) ? null : task.getProject().getProjectId()),
				((task.getParentTask() == null) ? null : task.getParentTask().getTaskId()),
				((task.getRootTask() == null) ? null : task.getRootTask().getTaskId())
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
				task.setTaskId(generatedKeys.getInt(1));
			} else {
				throw new DAOException("Creating task failed, no generated key obtained.");
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			close(connection, preparedStatement, generatedKeys);
		}

	}

	private static Task map(ResultSet rs) throws SQLException {
		Task task = new Task(rs.getInt("TaskId"));
		task.setTitle(rs.getString("Title"));
		task.setDescription(rs.getString("Description"));
		task.setPriority(Priority.values()[rs.getInt("Priority")]);
		task.setStatus(Status.values()[rs.getInt("Status")]);
		task.setDeadline(dateFromSqlTimestamp(rs.getTimestamp("Deadline")));
		task.setOwner(rs.getInt("Owner_UserId"));
		task.setEstimatedTime(rs.getInt("EstimatedTime"));
		task.setCreatedAt(dateFromSqlTimestamp(rs.getTimestamp("Created")));
		task.setUpdatedAt(dateFromSqlTimestamp(rs.getTimestamp("Updated")));

		task.setGroup(rs.getInt("Group_GroupId"));
		task.setProject(rs.getInt("Project_ProjectId"));
		task.setParentTask(rs.getInt("ParentId"));
		task.setRootTask(rs.getInt("RootId"));

		return task;
	}
}
