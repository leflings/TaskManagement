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

public class TaskAssignmentDAO extends BaseDAO {
	
	private static final String SQL_ADD_ASSIGNMENT = "INSERT INTO TaskAssignment VALUES (?, ?)";
	private static final String SQL_REMOVE_ASSIGNMENT = "DELETE FROM TaskAssignment WHERE Task_TaskId = ? AND User_UserId = ?";

	protected TaskAssignmentDAO(DAOFactory daoFactory) {
		super(daoFactory);
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
