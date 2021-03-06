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
import main.dto.User;
import main.exceptions.DAOException;

public class ProjectDAO extends BaseDAO {

	private static final String SQL_FIND_ALL = "SELECT * FROM Project";
	private static final String SQL_FIND_BY_ID = "SELECT * FROM Project WHERE p_ProjectId = ?";
	private static final String SQL_FIND_BY_GROUP_ID = "SELECT * FROM Project WHERE p_Group_GroupId = ?";
	private static final String SQL_FIND_BY_OWNER = "SELECT * FROM Project WHERE p_Owner_UserId = ?";
	private static final String SQL_FIND_BY_MEMBERSHIP = "SELECT p.* FROM Project p INNER JOIN ProjectMembership pm ON pm.pm_ProjectId = p.p_ProjectId AND pm.pm_UserId = ?";
	private static final String SQL_FIND_USERS_GROUPS = "SELECT p.* FROM Project p WHERE p.p_Owner_UserId = ? OR p.p_ProjectId IN (SELECT pm.pm_ProjectId FROM ProjectMembership pm WHERE pm.pm_UserId = ?)";
	
	private static final String SQL_FIND_PROJECTS_WITHOUT_GROUP = "SELECT * FROM Project WHERE p_Group_GroupId IS NULL";
	
	private static final String SQL_UPDATE = "UPDATE Project SET p_Title = ?, p_Description = ?, p_Owner_UserId = ?, p_Group_GroupId = ? WHERE p_ProjectId = ?";
	private static final String SQL_DELETE = "DELETE FROM Project WHERE p_ProjectId = ?";
	private static final String SQL_INSERT = "INSERT INTO Project (p_Title, p_Description, p_Owner_UserId, p_Group_GroupId) VALUES (?, ?, ?, ?)";

	protected ProjectDAO(DAOFactory daoFactory) {
		super(daoFactory);
	}
	
	private Project find(String sql, Object ... values) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Project project = null;
		try {
			connection = daoFactory.getConnection();
			preparedStatement = prepareStatement(connection, sql, false, values);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				project = map(resultSet);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			close(connection, preparedStatement, resultSet);
		}

		return project;
	}
	
	private List<Project> findMany(String sql) {
		return findMany(sql, new Object[0]);
	}
	
	private List<Project> findMany(String sql, Object ... values) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Project> projects = new ArrayList<Project>();
		Project project = null;
		try {
			connection = daoFactory.getConnection();
			preparedStatement = prepareStatement(connection, sql, false, values);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				project = map(resultSet);
				projects.add(project);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			close(connection, preparedStatement, resultSet);
		}

		return projects;
	}
	
	public List<Project> getAll() {
		return findMany(SQL_FIND_ALL);
	}
	
	public List<Project> getByOwnerAndMembership(User user) {
		return findMany(SQL_FIND_USERS_GROUPS, user.getId(), user.getId());
	}
	
	public List<Project> getByGroup(Group group) {
		return findMany(SQL_FIND_BY_GROUP_ID, group.getGroupId());
	}
	
	public List<Project> getByMembership(User user) {
		return findMany(SQL_FIND_BY_MEMBERSHIP, user.getUserId());
	}
	
	public List<Project> getByOwner(User user) {
		return findMany(SQL_FIND_BY_OWNER, user.getUserId());
	}
	
	public Project getById(int projectId) {
		return find(SQL_FIND_BY_ID, projectId);
	}
	
	public List<Project> getByNotInGroup() {
		return findMany(SQL_FIND_PROJECTS_WITHOUT_GROUP);
	}
	
	public void delete(Project project) {
		executeUpdate(SQL_DELETE, project.getProjectId());
	}
	
	public void update(Project project) {
		if(project.getProjectId() != 0) {
			executeUpdate(SQL_UPDATE,
					project.getTitle(),
					project.getDescription(),
					project.getOwner().getUserId(),
					((project.getGroup() == null) ? null : project.getGroup().getGroupId()),
					project.getProjectId());
		}
	}
	
	public void insert(Project project) {
		if (project.getProjectId() != 0) {
			throw new IllegalArgumentException("Group is already created. GroupId is not 0");
		}

		Object values[] = {
				project.getTitle(),
				project.getDescription(),
				project.getOwner().getUserId(),
				((project.getGroup() == null) ? null : project.getGroup().getGroupId())
			};
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet generatedKeys = null;

		try {
			connection = daoFactory.getConnection();
			preparedStatement = prepareStatement(connection, SQL_INSERT, true, values);
			int affectedRows = preparedStatement.executeUpdate();
			if (affectedRows == 0) {
				throw new DAOException("Creating project failed, no rows affected.");
			}
			generatedKeys = preparedStatement.getGeneratedKeys();
			if (generatedKeys.next()) {
				project.setProjectId(generatedKeys.getInt(1));
			} else {
				throw new DAOException("Creating project failed, no generated key obtained.");
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			close(connection, preparedStatement, generatedKeys);
		}
	}
	
	private static Project map(ResultSet rs) throws SQLException {
		Project project = new Project(rs.getInt("p_ProjectId"));
		project.setOwner(rs.getInt("p_Owner_UserId"));
		project.setTitle(rs.getString("p_Title"));
		project.setDescription(rs.getString("p_Description"));
		project.setGroup(rs.getInt("p_Group_GroupId"));
		
		return project;
	}
}
