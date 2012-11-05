package main.dto;

import java.util.List;

public class Project {
	private int projectId;
	private String projectName;
	private String description;

	private User owner;
	private Group group;

	private List<Task> tasks;
	private List<User> members;

	public int getProjectId() {
		return projectId;
	}
	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
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

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public List<User> getMembers() {
		return members;
	}
}
