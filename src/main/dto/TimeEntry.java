package main.dto;

import java.util.Date;

public class TimeEntry extends BaseModel {
	private int timeEntryId;
	private int taskId;
	private Task task;
	private int userId;
	private User user;
	private int duration;
	private Date date;
	
	public TimeEntry() {
		super();
	}
	
	public TimeEntry(int timeEntryId) {
		this();
		this.timeEntryId = timeEntryId;
	}
	
	public int getTimeEntryId() {
		return timeEntryId;
	}
	
	public void setTimeEntryId(int timeEntryId) {
		if(timeEntryId == 0) {
			this.timeEntryId = timeEntryId;
		}
	}

	public Task getTask() {
		if(task == null) {
			task = getFactory().getTaskDAO().getById(this.taskId);
		}
		return task;
	}

	public void setTask(Task task) {
		this.taskId = (task == null) ? 0 : task.getTaskId();
		this.task = task;
	}
	
	public void setTask(int taskId) {
		this.taskId = taskId;
	}

	public User getUser() {
		if(user == null) {
			user = getFactory().getUserDAO().getById(this.userId);
		}
		return user;
	}

	public void setUser(User user) {
		this.userId = (user == null) ? 0 : user.getUserId();
		this.user = user;
	}
	
	public void setUser(int userId) {
		this.userId = userId;
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

    @Override
    public boolean equals(Object other) {
    	return (other instanceof TimeEntry) && (other != null) ? getTimeEntryId() == (((TimeEntry) other).getTimeEntryId()) : (other == this);
    }

    @Override
    public int hashCode() {
        return (timeEntryId != 0) ? (this.getClass().hashCode() + timeEntryId) : super.hashCode();
    }

	@Override
	public int getId() {
		return timeEntryId;
	}
}
