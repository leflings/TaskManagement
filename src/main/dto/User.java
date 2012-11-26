package main.dto;

import java.util.List;

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

	public void setUserId(int userId) {
		this.userId = userId;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Task> getTasks() {
		tasks = getFactory().getTaskDAO().getByOwnershipAndCollaboration(this);
		return tasks;
	}

	public List<Group> getGroups() {
		groups = getFactory().getGroupDAO().getByOwnerAndMembership(this);
		return groups;
	}

	public List<Project> getProjects() {
		projects = getFactory().getProjectDAO().getByOwnerAndMembership(this);
		return projects;
	}

	public List<TimeEntry> getTimeEntries() {
		return getFactory().getTimeEntryDAO().getByUser(this);
	}

	public void save() {
		if (this.userId == 0) {
			insert();
		} else {
			update();
		}
	}

	private void insert() {
		getFactory().getUserDAO().insert(this);
	}

	private void update() {
		getFactory().getUserDAO().update(this);
	}

	@Override
	public boolean equals(Object other) {
		return (other instanceof User) && (other != null) ? getUserId() == (((User) other).getUserId()) : (other == this);
	}

	@Override
	public int hashCode() {
		return (userId != 0) ? (this.getClass().hashCode() + userId) : super.hashCode();
	}

	@Override
	public String toString() {
		return getName();
	}

	@Override
	public int getId() {
		return userId;
	}
}
