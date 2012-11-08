package main.dao;

import static main.dao.DAOUtil.close;
import static main.dao.DAOUtil.prepareStatement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import main.dto.Group;
import main.dto.User;
import main.enums.PermissionLevel;
import main.exceptions.DAOException;

public class GroupMembershipDAO {
	private DAOFactory daoFactory;
	
//	+-----------------+---------+------+-----+---------+-------+
//	| Field           | Type    | Null | Key | Default | Extra |
//	+-----------------+---------+------+-----+---------+-------+
//	| User_UserId     | int(11) | NO   | PRI | NULL    |       |
//	| Group_GroupId   | int(11) | NO   | PRI | NULL    |       |
//	| PermissionLevel | int(11) | NO   |     | NULL    |       |
//	+-----------------+---------+------+-----+---------+-------+

	private static final String SQL_ADD_MEMBER = "INSERT INTO GroupMembership(Group_GroupId, User_UserId, PermissionLevel) VALUES (?, ?, ?)";
	private static final String SQL_REMOVE_MEMBER = "DELETE FROM GroupMembership WHERE Group_GroupId = ? AND User_UserId = ?";

	public GroupMembershipDAO(DAOFactory daoFactory) {
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
	
	public void addMember(Group group, User user) {
		addMember(group, user, PermissionLevel.USER);
	}
	
	public void addMember(Group group, User user, PermissionLevel permissionLevel) {
		if(!group.getMembers().contains(user) && !user.getGroups().contains(group)) {
			executeUpdate(SQL_ADD_MEMBER, group.getGroupId(), user.getUserId(), permissionLevel.getCode());
			group.getMembers().add(user);
			user.getGroups().add(group);
		}
	}
	
	public void removeMember(Group group, User user) {
		if(group.getMembers().contains(user) && user.getGroups().contains(group)) {
			executeUpdate(SQL_REMOVE_MEMBER, group.getGroupId(), user.getUserId());
			group.getMembers().remove(user);
			user.getGroups().remove(group);
		}
	}

}
