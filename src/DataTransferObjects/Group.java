package DataTransferObjects;
import java.util.ArrayList;
import java.util.List;

import java.util.Set;
import java.util.HashSet;

public class Group {
/**
 * <pre>
 *           0..1     0..*
 * Group ------------------------- Project
 *           group        &gt;       projects
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
 *           0..1     0..*
 * Group ------------------------- Task
 *           group        &gt;       tasks
 * </pre>
 */
private List<Task> tasks;

public List<Task> getTasks() {
   if (this.tasks == null) {
this.tasks = new ArrayList<Task>();
   }
   return this.tasks;
}

private int groupId;

public void setGroupId(int value) {
   this.groupId = value;
}

public int getGroupId() {
   return this.groupId;
}

private String name;

public void setName(String value) {
   this.name = value;
}

public String getName() {
   return this.name;
}

private String description;

public void setDescription(String value) {
   this.description = value;
}

public String getDescription() {
   return this.description;
}

/**
 * <pre>
 *           1..*     1..1
 * Group ------------------------> User
 *           group        &gt;       owner
 * </pre>
 */
private User owner;

public void setOwner(User value) {
   this.owner = value;
}

public User getOwner() {
   return this.owner;
}

/**
 * <pre>
 *           0..*     1..*
 * Group ------------------------- User
 *           groups        &lt;       members
 * </pre>
 */
private Set<User> members;

public Set<User> getMembers() {
   if (this.members == null) {
this.members = new HashSet<User>();
   }
   return this.members;
}

}
