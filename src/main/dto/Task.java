package main.dto;

import java.util.Date;
import java.util.List;

public class Task {
	private int taskId;
	private int priority;
	private int status;
	private int estimatedTime;

	private String title;
	private String description;

	private Date deadline;
	private Date createdAt;
	private Date updatedAt;

	private User owner;
	private Project project;
	private Group group;
	private Task parentTask;

	private List<User> collaborators;
	private List<Task> childTasks;
	private List<TimeEntry> timeEntries;

	public int getTaskId() {
		return taskId;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getEstimatedTime() {
		return estimatedTime;
	}

	public void setEstimatedTime(int estimatedTime) {
		this.estimatedTime = estimatedTime;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public Task getParentTask() {
		return parentTask;
	}

	public void setParentTask(Task parentTask) {
		this.parentTask = parentTask;
	}

	public List<User> getCollaborators() {
		return collaborators;
	}

	public List<Task> getChildTasks() {
		return childTasks;
	}

	public List<TimeEntry> getTimeEntries() {
		return timeEntries;
	}

}
