package main.dto;

import java.util.List;

import main.dao.DAOFactory;
import main.enums.PermissionLevel;

public class Project extends BaseModel implements IMembership {
	private int projectId;
	private String title;
	private String description;

	private int ownerId;
	private User owner;

	private int groupId;
	private Group group;
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
		if (this.projectId == 0) {
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
		if (owner == null && ownerId != 0) {
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

	public List<Task> getTasks() {
		return getFactory().getTaskDAO().getByProject(this);
	}

	public List<User> getMembers() {
		members = getFactory().getUserDAO().getByProject(this);
		members.add(getOwner());
		return members;
	}

	public void save() {
		if (this.projectId == 0) {
			insert();
		} else {
			update();
		}
	}

	private void insert() {
		DAOFactory.getInstance().getProjectDAO().insert(this);
	}

	private void update() {
		DAOFactory.getInstance().getProjectDAO().update(this);
	}

	@Override
	public String toString() {
		return getTitle();
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

	@Override
	public void addMember(User user) {
		addMember(user, PermissionLevel.USER);
	}

	@Override
	public void addMember(User user, PermissionLevel permissionLevel) {
		if(!getMembers().contains(user)) {
			DAOFactory.getInstance().getProjectMembershipDAO().addMember(this, user, permissionLevel);
		}
	}

	@Override
	public void removeMember(User user) {
		if(getMembers().contains(user)) {
			DAOFactory.getInstance().getProjectMembershipDAO().removeMember(this, user);
		}
	}
}
