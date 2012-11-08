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
	private List<TimeEntry> timeEntries;

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
		if(tasks == null) {
			tasks = getFactory().getTaskDAO().getByOwner(this);
			tasks.addAll(getFactory().getTaskDAO().getByCollaboration(this));
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
		if(projects == null) {
			projects = getFactory().getProjectDAO().getByOwner(this);
			projects.addAll(getFactory().getProjectDAO().getByMembership(this));
		}
		return projects;
	}
	
	public List<TimeEntry> getTimeEntries() {
		if(timeEntries == null) {
			timeEntries = getFactory().getTimeEntryDAO().getByUser(this);
		}
		return timeEntries;
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
	public int getId() {
		return userId;
	}
}
