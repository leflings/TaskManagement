package main.dto;

import java.util.Date;
import java.util.List;

public class Task extends BaseModel {
	private int taskId;
	private int priority;
	private int status;
	private int estimatedTime;

	private String title;
	private String description;

	private Date deadline;
	private Date createdAt;
	private Date updatedAt;

	private int ownerId;
	private User owner;

	private int projectId;
	private Project project;

	private int groupId;
	private Group group;

	private int parentTaskId;
	private Task parentTask;
	
	private int rootTaskId;
	private Task rootTask;

	private List<User> collaborators;
	private List<Task> childTasks;
	private List<TimeEntry> timeEntries;

	public Task() {
		super();
	}

	public Task(int taskId) {
		this();
		this.taskId = taskId;
	}

	public Task(int taskId, int ownerId, int projectId, int groupId,
			int parentTaskId) {
		this(taskId);
		this.ownerId = ownerId;
		this.projectId = projectId;
		this.groupId = groupId;
		this.parentTaskId = parentTaskId;
	}

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
		if(owner == null) {
			factory.getUserDAO().getById(ownerId);
		}
		return owner;
	}

	public void setOwner(User owner) {
		this.ownerId = owner.getUserId();
		this.owner = owner;
	}

	public void setOwner(int ownerId) {
		this.ownerId = ownerId;
	}

	public Project getProject() {
		if(project == null && projectId != 0) {
			project = getFactory().getProjectDAO().getById(projectId);
		}
		return project;
	}

	public void setProject(Project project) {
		this.projectId = project.getProjectId();
		this.project = project;
	}

	public void setProject(int projectId) {
		this.projectId = projectId;
	}

	public Group getGroup() {
		if(group == null && groupId != 0) {
			group = getFactory().getGroupDAO().getById(groupId);
		}
		return group;
	}

	public void setGroup(Group group) {
		this.groupId = group.getGroupId();
		this.group = group;
	}

	public void setGroup(int groupId) {
		this.groupId = groupId;
	}

	public Task getParentTask() {
		if(parentTask == null && parentTaskId != 0) {
			parentTask = getFactory().getTaskDAO().getById(parentTaskId);
		}
		return parentTask;
	}

	public void setParentTask(Task parentTask) {
		this.parentTaskId = parentTask.getTaskId();
		this.parentTask = parentTask;
	}

	public void setParentTask(int parentTaskId) {
		this.parentTaskId = parentTaskId;
	}
	
	public Task getRootTask() {
		if(rootTask == null && rootTaskId != 0) {
			rootTask = getFactory().getTaskDAO().getById(rootTaskId);
		}
		return rootTask;
	}
	
	public void setRootTask(Task rootTask) {
		this.rootTaskId = rootTask.getTaskId();
		this.rootTask = rootTask;
	}
	
	public void setRootTask(int rootTaskId) {
		this.rootTaskId = rootTaskId;
	}

	public List<User> getCollaborators() {
		if(collaborators == null ) {
			collaborators = getFactory().getUserDAO().getByTaskAssignment(this);
		}
		return collaborators;
	}

	public List<Task> getChildTasks() {
		if(childTasks == null) {
			childTasks = getFactory().getTaskDAO().getByParentTask(this);
		}
		return childTasks;
	}

	public List<TimeEntry> getTimeEntries() {
		if(timeEntries == null) {
			timeEntries = getFactory().getTimeEntryDAO().getByTask(this);
		}
		return timeEntries;
	}
	
    @Override
    public boolean equals(Object other) {
    	return (other instanceof Task) && (other != null) ? getTaskId() == (((Task) other).getTaskId()) : (other == this);
    }

    @Override
    public int hashCode() {
        return (taskId != 0) ? (this.getClass().hashCode() + taskId) : super.hashCode();
    }
	
}
