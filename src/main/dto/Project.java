package main.dto;

import java.util.List;

public class Project extends BaseModel {
	private int projectId;
	private String projectName;
	private String description;

	private int ownerId;
	private User owner;
	
	private int groupId;
	private Group group;

	private List<Task> tasks;
	private List<User> members;
	
	public Project() {
		super();
	}
	
	public Project(int projectId) {
		this();
		this.projectId = projectId;
	}

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
		if(owner == null) {
			owner = getFactory().getUserDAO().getById(ownerId);
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

	public List<Task> getTasks() {
		return tasks;
	}

	public List<User> getMembers() {
		return members;
	}
}
