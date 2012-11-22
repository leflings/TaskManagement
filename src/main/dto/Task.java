package main.dto;

import java.util.Date;
import java.util.List;

import main.dao.DAOFactory;
import main.enums.PermissionLevel;
import main.enums.Priority;
import main.enums.Status;

public class Task extends BaseModel implements IAssignable {
	private int taskId;
	private Priority priority;
	private Status status;
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
		status = Status.NONE;
		priority = Priority.NONE;
	}

	public Task(int taskId) {
		this();
		this.taskId = taskId;
	}

	public Task(int taskId, int ownerId, int projectId, int groupId, int parentTaskId) {
		this(taskId);
		this.ownerId = ownerId;
		this.projectId = projectId;
		this.groupId = groupId;
		this.parentTaskId = parentTaskId;
	}

	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		if (this.taskId == 0) {
			this.taskId = taskId;
		}
	}

	public Priority getPriority() {
		return priority;
	}

	public void setPriority(Priority priority) {
		this.priority = priority;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
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
		if (owner == null) {
			owner = getFactory().getUserDAO().getById(ownerId);
		}
		return owner;
	}

	public void setOwner(User owner) {
		this.ownerId = (owner == null) ? 0 : owner.getUserId();
		this.owner = owner;
	}

	public void setOwner(int ownerId) {
		this.ownerId = ownerId;
	}

	public Project getProject() {
		if (project == null && projectId != 0) {
			project = getFactory().getProjectDAO().getById(projectId);
		}
		return project;
	}

	public void setProject(Project project) {
		this.projectId = (project == null) ? 0 : project.getProjectId();
		this.project = project;
	}

	public void setProject(int projectId) {
		this.projectId = projectId;
	}

	public Group getGroup() {
		if (group == null && groupId != 0) {
			group = getFactory().getGroupDAO().getById(groupId);
		}
		return group;
	}

	public void setGroup(Group group) {
		this.groupId = (group == null) ? 0 : group.getGroupId();
		this.group = group;
	}

	public void setGroup(int groupId) {
		this.groupId = groupId;
	}

	public Task getParentTask() {
		if (parentTask == null && parentTaskId != 0) {
			parentTask = getFactory().getTaskDAO().getById(parentTaskId);
		}
		return parentTask;
	}

	public void setParentTask(Task parentTask) {
		this.parentTaskId = (parentTask == null) ? 0 : parentTask.getTaskId();
		this.parentTask = parentTask;
	}

	public void setParentTask(int parentTaskId) {
		this.parentTaskId = parentTaskId;
	}

	public Task getRootTask() {
		if(rootTaskId == 0) {
			return this;
		} else {
			rootTask = getFactory().getTaskDAO().getById(rootTaskId);
			return rootTask;
		}
	}

	public void setRootTask(Task rootTask) {
		this.rootTaskId = (rootTask == null) ? 0 : rootTask.getTaskId();
		this.rootTask = rootTask;
	}

	public void setRootTask(int rootTaskId) {
		this.rootTaskId = rootTaskId;
	}

	public List<User> getCollaborators() {
		collaborators = getFactory().getUserDAO().getByTaskAssignment(this);
		return collaborators;
	}

	public List<Task> getChildTasks() {
		childTasks = getFactory().getTaskDAO().getByParentTask(this);
		return childTasks;
	}

	public List<TimeEntry> getTimeEntries() {
		timeEntries = getFactory().getTimeEntryDAO().getByTask(this);
		return timeEntries;
	}

	public void save() {
		if (this.taskId == 0) {
			insert();
		} else {
			update();
		}
	}

	private void insert() {
		DAOFactory.getInstance().getTaskDAO().insert(this);
	}

	private void update() {
		DAOFactory.getInstance().getTaskDAO().update(this);
	}

	@Override
	public String toString() {
		return getTitle();
	}

	@Override
	public boolean equals(Object other) {
		return (other instanceof Task) && (other != null) ? getTaskId() == (((Task) other).getTaskId()) : (other == this);
	}

	@Override
	public int hashCode() {
		return (taskId != 0) ? (this.getClass().hashCode() + taskId) : super.hashCode();
	}

	@Override
	public int getId() {
		return taskId;
	}

	@Override
	public void addCollaborator(User user) {
		if(!getCollaborators().contains(user)) {
			getFactory().getTaskAssignmentDAO().addAssignemnt(this, user);
		}
		
	}

	@Override
	public void removeCollaborator(User user) {
		if(getCollaborators().contains(user)) {
			getFactory().getTaskAssignmentDAO().removeMember(this, user);
		}
		
	}

}
