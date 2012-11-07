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

public class ProjectDAO {
	private DAOFactory daoFactory;

	private static final String SQL_FIND_ALL = "SELECT * FROM Project";
	private static final String SQL_FIND_BY_ID = "SELECT * FROM Project WHERE ProjectId = ?";

	public ProjectDAO(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
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
		return findMany(SQL_FIND_ALL, null);
	}
	
	public Project getById(int projectId) {
		return find(SQL_FIND_BY_ID, projectId);
	}
	
	private static Project map(ResultSet rs) throws SQLException {
		Project project = new Project(rs.getInt("ProjectId"));
		project.setOwner(rs.getInt("Owner_UserId"));
		project.setProjectName(rs.getString("ProjectName"));
		project.setDescription(rs.getString("Description"));
		project.setGroup(rs.getInt("Group_GroupId"));
		
		return project;
	}
}
