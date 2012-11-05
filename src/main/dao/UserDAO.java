package main.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import main.dto.User;
import main.exceptions.DAOException;
import static main.dao.DAOUtil.*;

public class UserDAO {

	private DAOFactory daoFactory;

	private static final String SQL_FIND_BY_ID = "SELECT * FROM User WHERE UserId = ?";

	public UserDAO(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	private User find(String sql, Object ... values) {
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
	
	public User getById(int userId) {
		return find(SQL_FIND_BY_ID, userId);
	}
	

	private static User map(ResultSet rs) throws SQLException {
		User user = new User(rs.getInt("UserId"));
		user.setEmail(rs.getString("Email"));
		user.setName(rs.getString("Name"));
		user.setUsername(rs.getString("Username"));
		return user;
	}
}
