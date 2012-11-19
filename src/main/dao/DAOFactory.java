package main.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import main.exceptions.DAOConfigurationException;

public abstract class DAOFactory {

	private static final String PROPERTY_URL = "url";
	private static final String PROPERTY_DRIVER = "driver";
	private static final String PROPERTY_USERNAME = "username";
	private static final String PROPERTY_PASSWORD = "password";

	public static DAOFactory getInstance() {
		return getInstance(DAOProperties.CONNECTION_NAME);
	}

	public static DAOFactory getInstance(String name) throws DAOConfigurationException {
		if (name == null) {
			throw new DAOConfigurationException("Database name is null.");
		}

		DAOProperties properties = new DAOProperties(name);
		String url = properties.getProperty(PROPERTY_URL, true);
		String driverClassName = properties.getProperty(PROPERTY_DRIVER, false);
		String password = properties.getProperty(PROPERTY_PASSWORD, false);
		String username = properties.getProperty(PROPERTY_USERNAME, password != null);
		DAOFactory instance;

		if (driverClassName != null) {
			try {
				Class.forName(driverClassName);
			} catch (ClassNotFoundException e) {
				throw new DAOConfigurationException("Driver class '" + driverClassName + "' is missing in classpath.", e);
			}
			instance = new DriverManagerDAOFactory(url, username, password);
		} else {
			throw new DAOConfigurationException("Driver instance not created");
		}

		return instance;
	}

	abstract Connection getConnection() throws SQLException;

	public UserDAO getUserDAO() {
		return new UserDAO(this);
	}

	public GroupDAO getGroupDAO() {
		return new GroupDAO(this);
	}

	public ProjectDAO getProjectDAO() {
		return new ProjectDAO(this);
	}

	public TaskDAO getTaskDAO() {
		return new TaskDAO(this);
	}

	public TimeEntryDAO getTimeEntryDAO() {
		return new TimeEntryDAO(this);
	}

	public GroupMembershipDAO getGroupMembershipDAO() {
		return new GroupMembershipDAO(this);
	}

	public ProjectMembershipDAO getProjectMembershipDAO() {
		return new ProjectMembershipDAO(this);
	}

	public TaskAssignmentDAO getTaskAssignmentDAO() {
		return new TaskAssignmentDAO(this);
	}

	public PermissionLevelDAO getPermissionLevelDAO() {
		return new PermissionLevelDAO(this);
	}

	public GeneralDAO getGeneralDAO() {
		return new GeneralDAO(this);
	}
}

class DriverManagerDAOFactory extends DAOFactory {
	private String url;
	private String username;
	private String password;

	DriverManagerDAOFactory(String url, String username, String password) {
		this.url = url;
		this.username = username;
		this.password = password;
	}

	@Override
	Connection getConnection() throws SQLException {
		return DriverManager.getConnection(url, username, password);
	}
}