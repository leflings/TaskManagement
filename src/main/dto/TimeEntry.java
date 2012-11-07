package main.dto;

import java.util.Date;

public class TimeEntry extends BaseModel {
	private int taskId;
	private Task task;
	private int userId;
	private User user;
	private int duration;
	private Date date;
	
	public TimeEntry() {
		super();
	}
	
	public TimeEntry(int taskId, int userId) {
		this();
		this.taskId = taskId;
		this.userId = userId;
	}
	
	public TimeEntry(User user, Task task) {
		this();
		setTask(task);
		setUser(user);
	}

	public Task getTask() {
		if(task == null) {
			task = getFactory().getTaskDAO().getById(this.taskId);
		}
		return task;
	}

	public void setTask(Task task) {
		this.taskId = task.getTaskId();
		this.task = task;
	}

	public User getUser() {
		if(user == null) {
			user = getFactory().getUserDAO().getById(this.userId);
		}
		return user;
	}

	public void setUser(User user) {
		this.userId = user.getUserId();
		this.user = user;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
