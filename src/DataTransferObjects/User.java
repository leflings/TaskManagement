package DataTransferObjects;

import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;

public class User {
	/**
	 * <pre>
	 *           1..*     0..*
	 * User ------------------------- Task
	 *           collaborators        &gt;       tasks
	 * </pre>
	 */
	private List<Task> tasks;

	public List<Task> getTasks() {
		if (this.tasks == null) {
			this.tasks = new ArrayList<Task>();
		}
		return this.tasks;
	}

	private String name;

	public void setName(String value) {
		this.name = value;
	}

	public String getName() {
		return this.name;
	}

	private String userName;

	public void setUserName(String value) {
		this.userName = value;
	}

	public String getUserName() {
		return this.userName;
	}

	private int userId;

	public void setUserId(int value) {
		this.userId = value;
	}

	public int getUserId() {
		return this.userId;
	}

	private String email;

	public void setEmail(String value) {
		this.email = value;
	}

	public String getEmail() {
		return this.email;
	}

	/**
	 * <pre>
	 *           1..*     0..*
	 * User ------------------------- Group
	 *           members        &gt;       groups
	 * </pre>
	 */
	private Set<Group> groups;

	public Set<Group> getGroups() {
		if (this.groups == null) {
			this.groups = new HashSet<Group>();
		}
		return this.groups;
	}

	/**
	 * <pre>
	 *           1..1     0..*
	 * User ------------------------- Project
	 *           owner        &lt;       ownedProjects
	 * </pre>
	 */
	private List<Project> ownedProjects;

	public List<Project> getOwnedProjects() {
		if (this.ownedProjects == null) {
			this.ownedProjects = new ArrayList<Project>();
		}
		return this.ownedProjects;
	}

	/**
	 * <pre>
	 *           1..*     0..*
	 * User ------------------------- Project
	 *           members        &gt;       projects
	 * </pre>
	 */
	private List<Project> projects;

	public List<Project> getProjects() {
		if (this.projects == null) {
			this.projects = new ArrayList<Project>();
		}
		return this.projects;
	}

	/**
	 * <pre>
	 *           1..1     0..*
	 * User ------------------------- Task
	 *           owner        &lt;       ownedTasks
	 * </pre>
	 */
	private List<Task> ownedTasks;

	public List<Task> getOwnedTasks() {
		if (this.ownedTasks == null) {
			this.ownedTasks = new ArrayList<Task>();
		}
		return this.ownedTasks;
	}

}
