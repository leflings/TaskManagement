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
import main.dto.User;
import main.enums.PermissionLevel;
import main.exceptions.DAOException;

public class GroupDAO extends BaseDAO {
	private static final String SQL_REMOVE_MEMBER = "DELETE FROM GroupMembership WHERE User_UserId = ? AND Group_GroupId = ?";
	private static final String SQL_ADD_MEMBER = "INSERT INTO GroupMembership VALUES (?, ?, ?)";
	private static final String SQL_FIND_ALL = "SELECT * FROM `Group`";
	private static final String SQL_FIND_BY_ID = "SELECT * FROM `Group` WHERE GroupId = ?";
	private static final String SQL_FIND_BY_MEMBERSHIP = "SELECT g.* FROM `Group` g INNER JOIN GroupMembership gm ON gm.Group_GroupId = g.GroupId AND gm.User_UserId = ?";
	private static final String SQL_FIND_BY_OWNERSHIP = "SELECT * FROM `Group` WHERE Owner_UserId = ?";
	
	private static final String SQL_INSERT = "INSERT INTO `Group` (Name, Description, Owner_UserId) VALUES (?, ?, ?)";
	private static final String SQL_UPDATE = "UPDATE `Group` SET Name = ?, Description = ?, Owner_UserId = ? WHERE GroupId = ?";

	
	protected GroupDAO(DAOFactory daoFactory) {
		super(daoFactory);
	}

	private List<Group> findMany(String sql, Object... values) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Group> groups = new ArrayList<Group>();
		Group group = null;
		try {
			connection = daoFactory.getConnection();
			preparedStatement = prepareStatement(connection, sql, false, values);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				group = map(resultSet);
				groups.add(group);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			close(connection, preparedStatement, resultSet);
		}

		return groups;

	}

	private Group find(String sql, Object... values) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Group group = null;
		try {
			connection = daoFactory.getConnection();
			preparedStatement = prepareStatement(connection, sql, false, values);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				group = map(resultSet);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			close(connection, preparedStatement, resultSet);
		}

		return group;
	}

	public Group getById(int groupId) {
		return find(SQL_FIND_BY_ID, groupId);
	}

	public List<Group> getByMembership(User user) {
		return findMany(SQL_FIND_BY_MEMBERSHIP, user.getUserId());
	}
	
	public List<Group> getByOwnership(User user) {
		return findMany(SQL_FIND_BY_OWNERSHIP, user.getUserId());
	}

	public List<Group> getAll() {
		return findMany(SQL_FIND_ALL, null);
	}
	
	public void insert(Group group) {
		if (group.getGroupId() != 0) {
			throw new IllegalArgumentException("Group is already created. GroupId is not 0");
		}

		Object values[] = { group.getName(), group.getDescription(), group.getOwner().getUserId() };
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet generatedKeys = null;

		try {
			connection = daoFactory.getConnection();
			preparedStatement = prepareStatement(connection, SQL_INSERT, true, values);
			int affectedRows = preparedStatement.executeUpdate();
			if (affectedRows == 0) {
				throw new DAOException("Creating group failed, no rows affected.");
			}
			generatedKeys = preparedStatement.getGeneratedKeys();
			if (generatedKeys.next()) {
				group.setGroupId(generatedKeys.getInt(1));
			} else {
				throw new DAOException("Creating group failed, no generated key obtained.");
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			close(connection, preparedStatement, generatedKeys);
		}
	}
	
	public void update(Group group) {
		if(group.getGroupId() != 0) {
			executeUpdate(SQL_UPDATE, group.getName(), group.getDescription(), group.getOwner().getUserId(), group.getGroupId());
		}
	}

	
	private static Group map(ResultSet rs) throws SQLException {
		Group group = new Group(rs.getInt("GroupId"), rs.getInt("Owner_UserId"));
		group.setName(rs.getString("Name"));
		group.setDescription(rs.getString("Description"));

		return group;
	}

}
