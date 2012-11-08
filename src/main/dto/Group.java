package main.dto;

import java.util.List;

import main.enums.PermissionLevel;

public class Group extends BaseModel {

	private int groupId;
	private String name;
	private String description;
	private int ownerId;
	private User owner;
	private List<Project> projects;
	private List<Task> tasks;
	private List<User> members;
	
	public Group() {
		super();
	}
	
	public Group(int groupId) {
		this();
		this.groupId = groupId;
	}
	
	public Group(int groupId, int ownerId) {
		this(groupId);
		this.ownerId = ownerId;
	}

	public int getGroupId() {
		return groupId;
	}
	
	public void setGroupId(int groupId) {
		if(groupId == 0) {
			this.groupId = groupId;
		}
	}

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

	public List<Project> getProjects() {
		if(projects == null ) {
			projects = getFactory().getProjectDAO().getByGroup(this);
		}
		return projects;
	}

	public List<Task> getTasks() {
		if(tasks == null) {
			tasks = getFactory().getTaskDAO().getByGroup(this);
		}
		return tasks;
	}

	public List<User> getMembers() {
		if(members == null) {
			members = getFactory().getUserDAO().getByGroup(this);
			members.add(getOwner());
		}
		return members;
	}
	
    @Override
    public boolean equals(Object other) {
    	return (other instanceof Group) && (other != null) ? getGroupId() == (((Group) other).getGroupId()) : (other == this);
    }

    @Override
    public int hashCode() {
        return (groupId != 0) ? (this.getClass().hashCode() + groupId) : super.hashCode();
    }

}