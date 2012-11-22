package main.dto;

import java.util.List;

import main.dao.DAOFactory;

public class Group extends BaseModel {

	private int groupId;
	private String title;
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
		this.ownerId = (owner == null) ? 0 : owner.getUserId();
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
	
	public void save() {
		if(this.groupId == 0) {
			insert();
		} else {
			update();
		}
	}
	
	private void insert() {
		DAOFactory.getInstance().getGroupDAO().insert(this);
	}
	
	private void update() {
		DAOFactory.getInstance().getGroupDAO().update(this);
	}
	
	@Override
	public String toString() {
		return getTitle();
	}
	
    @Override
    public boolean equals(Object other) {
    	return (other instanceof Group) && (other != null) ? getGroupId() == (((Group) other).getGroupId()) : (other == this);
    }

    @Override
    public int hashCode() {
        return (groupId != 0) ? (this.getClass().hashCode() + groupId) : super.hashCode();
    }

    @Override
	public int getId() {
		return groupId;
	}
}