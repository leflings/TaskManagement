package DataTransferObjects;


public class TimeEntry {
	/**
	 * <pre>
	 *           0..*     1..1
	 * TimeEntry ------------------------- Task
	 *           timeEntry        &gt;       task
	 * </pre>
	 */
	private Task task;


	public void setTask(Task value) {
		this.task = value;
	}

	public Task getTask() {
		return this.task;
	}

	private int duration;

	public void setDuration(int value) {
		this.duration = value;
	}

	public int getDuration() {
		return this.duration;
	}

	private String date;

	public void setDate(String value) {
		this.date = value;
	}

	public String getDate() {
		return this.date;
	}

	/**
	 * <pre>
	 *           0..*     1..1
	 * TimeEntry ------------------------> User
	 *           timeEntry        &gt;       user
	 * </pre>
	 */
	private User user;

	public void setUser(User value) {
		this.user = value;
	}

	public User getUser() {
		return this.user;
	}

}
