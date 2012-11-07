package main.dto;

import java.util.List;

import main.dao.DAOFactory;

public class User extends BaseModel {
	
	private int userId;
	private String username;
	private String name;
	private String email;
	private String password;
	private List<Task> tasks;
	private List<Group> groups;
	private List<Project> projects;

	public User() {
		super();
	}
	
	public User(int userId) {
		this();
		this.userId = userId;
	}
	
	public int getUserId() {
		return userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Task> getTasks() {
		if(tasks == null) {
			tasks = getFactory().getTaskDAO().getByOwner(this);
		}
		return tasks;
	}

	public List<Group> getGroups() {
		if(groups == null) {
			groups = getFactory().getGroupDAO().getByOwnership(this);
			groups.addAll(getFactory().getGroupDAO().getByMembership(this));
		}
		return groups;
	}

	public List<Project> getProjects() {
		return projects;
	}

}
