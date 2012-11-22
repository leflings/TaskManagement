package main.dao;

import main.dto.Group;
import main.dto.User;
import main.enums.PermissionLevel;

public class GroupMembershipDAO extends BaseDAO {
	
//	+-----------------+---------+------+-----+---------+-------+
//	| Field           | Type    | Null | Key | Default | Extra |
//	+-----------------+---------+------+-----+---------+-------+
//	| User_UserId     | int(11) | NO   | PRI | NULL    |       |
//	| Group_GroupId   | int(11) | NO   | PRI | NULL    |       |
//	| PermissionLevel | int(11) | NO   |     | NULL    |       |
//	+-----------------+---------+------+-----+---------+-------+

	private static final String SQL_ADD_MEMBER = "INSERT INTO GroupMembership(gm_GroupId, gm_UserId, gm_PermissionLevel) VALUES (?, ?, ?)";
	private static final String SQL_REMOVE_MEMBER = "DELETE FROM GroupMembership WHERE gm_GroupId = ? AND gm_UserId = ?";

	protected GroupMembershipDAO(DAOFactory daoFactory) {
		super(daoFactory);
	}

	public void addMember(Group group, User user) {
		addMember(group, user, PermissionLevel.USER);
	}
	
	public void addMember(Group group, User user, PermissionLevel permissionLevel) {
		executeUpdate(SQL_ADD_MEMBER, group.getGroupId(), user.getUserId(), permissionLevel.getCode());
	}
	
	public void removeMember(Group group, User user) {
		executeUpdate(SQL_REMOVE_MEMBER, group.getGroupId(), user.getUserId());
	}

}
