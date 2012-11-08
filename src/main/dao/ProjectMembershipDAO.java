package main.dao;

import static main.dao.DAOUtil.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import main.dto.Group;
import main.dto.Project;
import main.dto.User;
import main.enums.PermissionLevel;
import main.exceptions.DAOException;

public class ProjectMembershipDAO {
	private DAOFactory daoFactory;

	private static final String SQL_ADD_MEMBER = "INSERT INTO ProjectMembership VALUES (?, ?, ?)";
	private static final String SQL_REMOVE_MEMBER = "DELETE FROM ProjectMembership WHERE Project_ProjectId = ? AND User_UserId = ?";

	public ProjectMembershipDAO(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	private void executeUpdate(String sql, Object ... values) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connection = daoFactory.getConnection();
			preparedStatement = prepareStatement(connection, sql, false, values);
			int affectedRows = preparedStatement.executeUpdate();
			if (affectedRows == 0) {
                throw new DAOException("Failed, no rows affected.");
            }
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			close(connection, preparedStatement, resultSet);
		}
	}
	
	public void addMember(Project project, User user) {
		addMember(project, user, PermissionLevel.USER);
	}
	
	public void addMember(Project project, User user, PermissionLevel permissionLevel) {
		if(!project.getMembers().contains(user) && !user.getProjects().contains(project)) {
			executeUpdate(SQL_ADD_MEMBER, project.getProjectId(), user.getUserId(), permissionLevel.getCode());
			project.getMembers().add(user);
			user.getProjects().add(project);
		}
	}
	
	public void removeMember(Project project, User user) {
		if(project.getMembers().contains(user) && user.getProjects().contains(project)) {
			executeUpdate(SQL_REMOVE_MEMBER, project.getProjectId(), user.getUserId());
			project.getMembers().remove(user);
			user.getProjects().remove(project);
		}
	}

}
