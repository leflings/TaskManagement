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

	private static final String SQL_FIND_BY_COLLABORATION = "SELECT t.* FROM Task t INNER JOIN TaskAssignment ta ON ta.ta_TaskId = t.t_TaskId AND ta.ta_UserId = ?";
	private static final String SQL_FIND_BY_OWNER = "SELECT * FROM Task WHERE t_Owner_UserId = ?";
	private static final String SQL_FIND_BY_PARENT_ID = "SELECT * FROM Task WHERE t_Parent_TaskId = ?";
	private static final String SQL_FIND_BY_ROOT_ID = "SELECT * FROM Task WHERE t_Root_TaskId = ?";
	private static final String SQL_FIND_BY_ID = "SELECT * FROM Task WHERE t_TaskId = ?";
	private static final String SQL_FIND_ALL = "SELECT * FROM Task";
	private static final String SQL_FIND_BY_PROJECT = "SELECT * FROM Task WHERE t_ProjectId = ?";
	private static final String SQL_FIND_BY_GROUP = "SELECT * FROM Task WHERE t_GroupId = ?";
	
	private static final String SQL_FIND_TASKS_WITHOUT_GROUP = "SELECT * FROM Task WHERE t_GroupId IS NULL";
	private static final String SQL_FIND_TASKS_WITHOUT_PROJECT = "SELECT * FROM Task WHERE t_ProjectId IS NULL";
	
	private static final String SQL_UPDATE = "UPDATE Task SET t_Title = ?, t_Description = ?, t_Priority = ?, t_Status = ?, t_Deadline = ?, t_Owner_UserId = ?, t_EstimatedTime = ?, t_Updated = CURRENT_TIMESTAMP, t_GroupId = ?, t_ProjectId = ?, t_Parent_TaskId = ?, t_Root_TaskId = ? WHERE t_TaskId = ?";
	private static final String SQL_INSERT = "INSERT INTO Task (t_Title, t_Description, t_Priority, t_Status, t_Deadline, t_Owner_UserId, t_EstimatedTime, t_GroupId, t_ProjectId, t_Parent_TaskId, t_Task_TaskId) VALUES (?, ?, ?, ? ,?, ?, ?, ?, ?, ?, ?)";

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
	
	private List<Task> findMany(String sql) { return findMany(sql, new Object[0]); }

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

	public List<Task> getTasksWithoutProject() {
		return findMany(SQL_FIND_TASKS_WITHOUT_PROJECT);
	}
	public List<Task> getTasksWithoutGroup() {
		return findMany(SQL_FIND_TASKS_WITHOUT_GROUP);
	}
	
	public List<Task> getByOwner(User owner) {
		return findMany(SQL_FIND_BY_OWNER, owner.getUserId());
	}

	public List<Task> getAll() {
		return findMany(SQL_FIND_ALL);
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
					task.getPriority().getCode(),
					task.getStatus().getCode(),
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
		Task task = new Task(rs.getInt("t_TaskId"));
		task.setTitle(rs.getString("t_Title"));
		task.setDescription(rs.getString("t_Description"));
		task.setPriority(Priority.values()[rs.getInt("t_Priority")]);
		task.setStatus(Status.values()[rs.getInt("t_Status")]);
		task.setDeadline(dateFromSqlTimestamp(rs.getTimestamp("t_Deadline")));
		task.setOwner(rs.getInt("t_Owner_UserId"));
		task.setEstimatedTime(rs.getInt("t_EstimatedTime"));
		task.setCreatedAt(dateFromSqlTimestamp(rs.getTimestamp("t_Created")));
		task.setUpdatedAt(dateFromSqlTimestamp(rs.getTimestamp("t_Updated")));

		task.setGroup(rs.getInt("t_GroupId"));
		task.setProject(rs.getInt("t_ProjectId"));
		task.setParentTask(rs.getInt("t_Parent_TaskId"));
		task.setRootTask(rs.getInt("t_Root_TaskId"));

		return task;
	}
}
