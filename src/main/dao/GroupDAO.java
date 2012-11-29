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
import main.exceptions.DAOException;

public class GroupDAO extends BaseDAO {
	private static final String SQL_FIND_ALL = "SELECT * FROM `Group`";
	private static final String SQL_FIND_BY_ID = "SELECT * FROM `Group` WHERE g_GroupId = ?";
	private static final String SQL_FIND_BY_MEMBERSHIP = "SELECT g.* FROM `Group` g INNER JOIN GroupMembership gm ON gm.gm_GroupId = g.g_GroupId AND gm.gm_UserId = ?";
	private static final String SQL_FIND_BY_OWNERSHIP = "SELECT * FROM `Group` WHERE g_Owner_UserId = ?";
	private static final String SQL_FIND_BY_MEMBER_OWNERSHIP = "SELECT g.* FROM `Group` g WHERE g.g_Owner_UserId = ? OR g.g_GroupId IN (SELECT gm.gm_GroupId FROM GroupMembership gm WHERE gm.gm_UserId = ?)";
	
	private static final String SQL_INSERT = "INSERT INTO `Group` (g_Title, g_Description, g_Owner_UserId) VALUES (?, ?, ?)";
	private static final String SQL_DELETE = "DELETE FROM `Group` WHERE g_GroupId = ?";
	private static final String SQL_UPDATE = "UPDATE `Group` SET g_Title = ?, g_Description = ?, g_Owner_UserId = ? WHERE g_GroupId = ?";
	
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
	
	public List<Group> getByOwnerAndMembership(User user) {
		return findMany(SQL_FIND_BY_MEMBER_OWNERSHIP, user.getUserId(), user.getUserId());
	}

	public List<Group> getByMembership(User user) {
		return findMany(SQL_FIND_BY_MEMBERSHIP, user.getUserId());
	}
	
	public List<Group> getByOwnership(User user) {
		return findMany(SQL_FIND_BY_OWNERSHIP, user.getUserId());
	}

	public List<Group> getAll() {
		return findMany(SQL_FIND_ALL);
	}
	
	public void insert(Group group) {
		if (group.getGroupId() != 0) {
			throw new IllegalArgumentException("Group is already created. GroupId is not 0");
		}

		Object values[] = { group.getTitle(), group.getDescription(), group.getOwner().getUserId() };
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
			executeUpdate(SQL_UPDATE, group.getTitle(), group.getDescription(), group.getOwner().getUserId(), group.getGroupId());
		}
	}
	
	public void delete(Group group) {
		executeUpdate(SQL_DELETE, group.getGroupId());
	}
	
	private static Group map(ResultSet rs) throws SQLException {
		Group group = new Group(rs.getInt("g_GroupId"), rs.getInt("g_Owner_UserId"));
		group.setTitle(rs.getString("g_Title"));
		group.setDescription(rs.getString("g_Description"));

		return group;
	}

}
