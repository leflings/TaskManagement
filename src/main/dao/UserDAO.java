package main.dao;

import static main.dao.DAOUtil.close;
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
import main.exceptions.DAOException;

public class UserDAO {

	private DAOFactory daoFactory;

	private static final String SQL_FIND_BY_TASK_ASSIGNMENT = "SELECT u.* FROM User u INNER JOIN TaskAssignment ta ON ta.User_UserId = u.UserId AND ta.Task_TaskId = ?";
	private static final String SQL_FIND_BY_PROJECT = "SELECT u.* FROM User u INNER JOIN ProjectMembership pm ON pm.User_UserId = u.UserId AND pm.Project_ProjectId = ?";
	private static final String SQL_FIND_BY_GROUP = "SELECT u.* FROM User u INNER JOIN GroupMembership gm ON gm.User_UserId = u.UserId AND gm.Group_GroupId = ?";
	private static final String SQL_FIND_BY_ID = "SELECT * FROM User WHERE UserId = ?";
	private static final String SQL_FIND_BY_USERNAME = "SELECT * FROM User WHERE Username = ?";
	private static final String SQL_FIND = "SELECT * FROM User";

	public UserDAO(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	private User find(String sql, Object ... values) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		User user = null;
		try {
			connection = daoFactory.getConnection();
			preparedStatement = prepareStatement(connection, sql, false, values);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				user = map(resultSet);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			close(connection, preparedStatement, resultSet);
		}

		return user;
	}
	
	private List<User> findMany(String sql, Object ... values) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<User> users = new ArrayList<User>();
		User user = null;
		try {
			connection = daoFactory.getConnection();
			preparedStatement = prepareStatement(connection, sql, false, values);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				user = map(resultSet);
				users.add(user);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			close(connection, preparedStatement, resultSet);
		}

		return users;
	}
	
	public User getById(int userId) {
		return find(SQL_FIND_BY_ID, userId);
	}
	
	public User getByUsername(String username) {
		return find(SQL_FIND_BY_USERNAME, username);
	}
	
	public List<User> getAll() {
		return findMany(SQL_FIND, null);
	}
	
	public List<User> getByGroup(Group group) {
		return findMany(SQL_FIND_BY_GROUP, group.getGroupId());
	}
	
	public List<User> getByProject(Project project) {
		return findMany(SQL_FIND_BY_PROJECT, project.getProjectId());
	}
	
	public List<User> getByTaskAssignment(Task task) {
		return findMany(SQL_FIND_BY_TASK_ASSIGNMENT, task.getTaskId());
	}

	private static User map(ResultSet rs) throws SQLException {
		User user = new User(rs.getInt("UserId"));
		user.setEmail(rs.getString("Email"));
		user.setName(rs.getString("Name"));
		user.setUsername(rs.getString("Username"));
		return user;
	}
}
