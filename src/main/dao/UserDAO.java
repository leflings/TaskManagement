package main.dao;

import static main.dao.DAOUtil.close;
import static main.dao.DAOUtil.prepareStatement;

import java.sql.CallableStatement;
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

public class UserDAO extends BaseDAO {

	private static final String SQL_FIND_BY_TASK_ASSIGNMENT = "SELECT u.* FROM User u INNER JOIN TaskAssignment ta ON ta.ta_UserId = u.u_UserId AND ta.ta_TaskId = ?";
	private static final String SQL_FIND_BY_PROJECT = "SELECT u.* FROM User u INNER JOIN ProjectMembership pm ON pm.pm_UserId = u.u_UserId AND pm.pm_ProjectId = ?";
	private static final String SQL_FIND_BY_GROUP = "SELECT u.* FROM User u INNER JOIN GroupMembership gm ON gm.gm_UserId = u.u_UserId AND gm.gm_GroupId = ?";
	private static final String SQL_FIND_BY_ID = "SELECT * FROM User WHERE u_UserId = ?";
	private static final String SQL_FIND_BY_USERNAME = "SELECT * FROM User WHERE u_Username = ?";
	private static final String SQL_FIND = "SELECT * FROM User";
	private static final String SQL_FIND_BY_LOGIN = "SELECT * FROM User WHERE u_Username = ? AND u_Password = ?";
	
	private static final String SQL_FIND_BY_NOT_IN_GROUP = "SELECT u.* FROM User u WHERE u.u_UserId NOT IN (SELECT gm.gm_UserId FROM GroupMembership gm WHERE gm.gm_GroupId = ?) AND u.u_UserId NOT IN (SELECT g.g_Owner_UserId FROM `Group` g WHERE g.g_GroupId = ?)";
	private static final String SQL_FIND_BY_NOT_IN_PROJECT = "SELECT u.* FROM User u WHERE u.u_UserId NOT IN (SELECT pm.pm_UserId FROM ProjectMembership pm WHERE pm.pm_ProjectId = ?) AND u.u_UserId NOT IN (SELECT p.p_Owner_UserId FROM Project p WHERE p.p_ProjectId = ?)";
	private static final String SQL_FIND_BY_NOT_ASSOCIATED_WITH_TASK = "SELECT u.* FROM User u WHERE u.u_UserId NOT IN (SELECT ta.ta_UserId FROM TaskAssignment ta WHERE ta.ta_TaskId = ?) AND u.u_UserId NOT IN (SELECT t.t_Owner_UserId FROM Task t WHERE t.t_TaskId = ?)"; 

	private static final String SQL_INSERT = "INSERT INTO User (u_Username, u_Name, u_Email, u_Password) VALUES (?, ?, ?, ?)";
	private static final String SQL_UPDATE = "UPDATE User SET u_Username = ?, u_Name = ?, u_Email = ? WHERE u_UserId = ?";

	protected UserDAO(DAOFactory daoFactory) {
		super(daoFactory);
	}

	private User find(String sql, Object... values) {
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

	private List<User> findMany(String sql) {
		return findMany(sql, new Object[0]);
	}
	
	private List<User> findMany(String sql, Object... values) {
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
	
//	public User authenticate(String username, String password) {
//		return find(SQL_FIND_BY_LOGIN, username, password);
//	}
	/**
	 * Bruges til stored procedure!
	 */
	public User authenticate(String username, String password){
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		User user = null;
		try {
			connection = daoFactory.getConnection();
			CallableStatement proc = connection.prepareCall("{call Login(?,?) }");
			proc.setString(1, username);
			proc.setString(2, password);
	
			resultSet = proc.executeQuery();
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

	public User getById(int userId) {
		return find(SQL_FIND_BY_ID, userId);
	}

	public User getByUsername(String username) {
		return find(SQL_FIND_BY_USERNAME, username);
	}

	public List<User> getAll() {
		return findMany(SQL_FIND);
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
	
	public List<User> getByNotInGroup(Group group) {
		return findMany(SQL_FIND_BY_NOT_IN_GROUP, group.getGroupId(), group.getGroupId());
	}
	
	public List<User> getByNotInProject(Project project) {
		return findMany(SQL_FIND_BY_NOT_IN_PROJECT, project.getProjectId(), project.getProjectId());
	}
	
	public List<User> getByNotAssociatedWithTask(Task task) {
		return findMany(SQL_FIND_BY_NOT_ASSOCIATED_WITH_TASK, task.getTaskId(), task.getTaskId());
	}

	public void update(User user) {
		if (user.getUserId() != 0) {
			executeUpdate(SQL_UPDATE, user.getUsername(), user.getName(), user.getEmail(), user.getUserId());
		}
	}

	public void insert(User user) {
		if (user.getUserId() != 0) {
			throw new IllegalArgumentException("User is already created. UserId is not 0");
		}

		Object values[] = { user.getUsername(), user.getName(), user.getEmail(), user.getPassword() };
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet generatedKeys = null;

		try {
			connection = daoFactory.getConnection();
			preparedStatement = prepareStatement(connection, SQL_INSERT, true, values);
			int affectedRows = preparedStatement.executeUpdate();
			if (affectedRows == 0) {
				throw new DAOException("Creating user failed, no rows affected.");
			}
			generatedKeys = preparedStatement.getGeneratedKeys();
			if (generatedKeys.next()) {
				user.setUserId(generatedKeys.getInt(1));
			} else {
				throw new DAOException("Creating user failed, no generated key obtained.");
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			close(connection, preparedStatement, generatedKeys);
		}

	}

	private static User map(ResultSet rs) throws SQLException {
		User user = new User(rs.getInt("u_UserId"));
		user.setEmail(rs.getString("u_Email"));
		user.setName(rs.getString("u_Name"));
		user.setUsername(rs.getString("u_Username"));
		return user;
	}
}
