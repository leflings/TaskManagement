package main.dto;

import java.util.List;

public class Group {

	private int groupId;
	private String name;
	private String description;
	private User owner;
	private List<Project> projects;
	private List<Task> tasks;
	private List<User> members;
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public List<Project> getProjects() {
		return projects;
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public List<User> getMembers() {
		return members;
	}

	public int getGroupId() {
		return groupId;
	}

}