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

public class GroupDAO {
	private DAOFactory daoFactory;

	private static final String SQL_FIND_ALL = "SELECT * FROM `Group`";
	private static final String SQL_FIND_BY_ID = "SELECT * FROM `Group` WHERE GroupId = ?";
	private static final String SQL_FIND_BY_MEMBERSHIP = "SELECT g.* FROM `Group` g INNER JOIN GroupMembership gm ON gm.Group_GroupId = g.GroupId AND gm.User_UserId = ?";
	private static final String SQL_FIND_BY_OWNERSHIP = "SELECT * FROM `Group` WHERE Owner_UserId = ?";
//	SELECT g.* FROM `Group` g INNER JOIN GroupMembership gm ON gm.Group_GroupId = g.GroupId AND gm.User_UserId = 1

	public GroupDAO(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
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

	private static Group map(ResultSet rs) throws SQLException {
		Group group = new Group(rs.getInt("GroupId"), rs.getInt("Owner_UserId"));
		group.setName(rs.getString("Name"));
		group.setDescription(rs.getString("Description"));

		return group;
	}

}
