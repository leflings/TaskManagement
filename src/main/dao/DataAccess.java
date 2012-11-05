package main.dao;

public class DataAccess {

	private GroupDAO groupDAO;
	private ProjectDAO projectDAO;
	private TaskDAO taskDAO;
	private TimeEntryDAO timeEntryDAO;
	private UserDAO userDAO;
	
	private static DataAccess instance;

	private DataAccess() {
	}
	
	public static DataAccess Get() {
		if (instance == null) {
			instance = new DataAccess();
		}
		return instance;
	}

	public GroupDAO getGroupDAO() {
		if (groupDAO == null) {
			groupDAO = new GroupDAO();
		}
		return groupDAO;
	}

	public ProjectDAO getProjectDAO() {
		if (projectDAO == null) {
			projectDAO = new ProjectDAO();
		}
		return projectDAO;
	}

	public TaskDAO getTaskDAO() {
		if (taskDAO == null) {
			taskDAO = new TaskDAO();
		}
		return taskDAO;
	}

	public TimeEntryDAO getTimeEntryDAO() {
		if (timeEntryDAO == null) {
			timeEntryDAO = new TimeEntryDAO();
		}
		return timeEntryDAO;
	}

	public UserDAO getUserDAO() {
		if (userDAO == null) {
			userDAO = new UserDAO();
		}
		return userDAO;
	}
}
