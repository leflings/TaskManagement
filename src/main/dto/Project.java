package main.dto;

import java.util.List;

public class Project extends BaseModel {
	private int projectId;
	private String title;
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
	
	public void setProjectId(int projectId) {
		if(this.projectId == 0) {
			this.projectId = projectId;
		}
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

	public User getOwner() {
		if(owner == null && ownerId != 0) {
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
		if (tasks == null) {
			tasks = getFactory().getTaskDAO().getByProject(this);
		}
		return tasks;
	}

	public List<User> getMembers() {
		if(members == null) {
			members = getFactory().getUserDAO().getByProject(this);
			members.add(getOwner());
		}
		return members;
	}
	
    @Override
    public boolean equals(Object other) {
    	return (other instanceof Project) && (other != null) ? getProjectId() == (((Project) other).getProjectId()) : (other == this);
    }

    @Override
    public int hashCode() {
        return (projectId != 0) ? (this.getClass().hashCode() + projectId) : super.hashCode();
    }

	@Override
	public int getId() {
		return projectId;
	}
}
