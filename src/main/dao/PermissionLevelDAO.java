package main.dao;

import static main.dao.DAOUtil.close;
import static main.dao.DAOUtil.prepareStatement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import main.dto.Group;
import main.dto.Project;
import main.dto.User;
import main.enums.PermissionLevel;
import main.exceptions.DAOException;

public class PermissionLevelDAO extends BaseDAO {
	
	private static final String SQL_GROUP_MEMBERSHIP_PERMISSIONLEVEL = "SELECT gm_PermissionLevel FROM GroupMembership WHERE gm_GroupId = ? AND gm_UserId = ?";
	private static final String SQL_PROJECT_MEMBERSHIP_PERMISSIONLEVEL = "SELECT pm_PermissionLevel FROM ProjectMembership WHERE pm_ProjectId = ? AND pm_UserId = ?";

	protected PermissionLevelDAO(DAOFactory daoFactory) {
		super(daoFactory);
	}
	
	private PermissionLevel find(String sql, Object... values) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		PermissionLevel pl = null;
		try {
			connection = daoFactory.getConnection();
			preparedStatement = prepareStatement(connection, sql, false, values);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				pl = map(resultSet);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			close(connection, preparedStatement, resultSet);
		}

		return pl;
	}
	
	public PermissionLevel fromGroup(Group group, User user) {
		return find(SQL_GROUP_MEMBERSHIP_PERMISSIONLEVEL, group.getGroupId(), user.getUserId());
	}
	
	public PermissionLevel fromProject(Project project, User user) {
		return find(SQL_PROJECT_MEMBERSHIP_PERMISSIONLEVEL, project.getProjectId(), user.getUserId());
	}
	
	private static PermissionLevel map(ResultSet rs) throws SQLException {
		return PermissionLevel.values()[rs.getInt(1)];
	}

}
