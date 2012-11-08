package main.dao;

import static main.dao.DAOUtil.close;
import static main.dao.DAOUtil.prepareStatement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import main.dto.Task;
import main.dto.User;
import main.exceptions.DAOException;

public class TaskAssignmentDAO {
	
	private DAOFactory daoFactory;

	private static final String SQL_ADD_ASSIGNMENT = "INSERT INTO TaskAssignment VALUES (?, ?)";
	private static final String SQL_REMOVE_ASSIGNMENT = "DELETE FROM TaskAssignment WHERE Task_TaskId = ? AND User_UserId = ?";

	public TaskAssignmentDAO(DAOFactory daoFactory) {
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
	
	public void addAssignemnt(Task task, User user) {
		if(!task.getCollaborators().contains(user) && !user.getTasks().contains(task)) {
			executeUpdate(SQL_ADD_ASSIGNMENT, task.getTaskId(), user.getUserId());
			task.getCollaborators().add(user);
			user.getTasks().add(task);
		}
	}
	
	public void removeMember(Task task, User user) {
		if(task.getCollaborators().contains(user) && user.getTasks().contains(task)) {
			executeUpdate(SQL_REMOVE_ASSIGNMENT, task.getTaskId(), user.getUserId());
			task.getCollaborators().remove(user);
			user.getTasks().remove(task);
		}
	}

}
