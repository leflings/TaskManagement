package main.dto;

import java.util.List;
import java.util.ArrayList;

public class Project {
/**
 * <pre>
 *           0..*     0..1
 * Project ------------------------- Group
 *           projects        &lt;       group
 * </pre>
 */
private Group group;

public void setGroup(Group value) {
   this.group = value;
}

public Group getGroup() {
   return this.group;
}

/**
 * <pre>
 *           0..1     0..*
 * Project ------------------------- Task
 *           project        &gt;       tasks
 * </pre>
 */
private List<Task> tasks;

public List<Task> getTasks() {
   if (this.tasks == null) {
this.tasks = new ArrayList<Task>();
   }
   return this.tasks;
}

private int projectId;

public void setProjectId(int value) {
   this.projectId = value;
}

public int getProjectId() {
   return this.projectId;
}

private String projectName;

public void setProjectName(String value) {
   this.projectName = value;
}

public String getProjectName() {
   return this.projectName;
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
 *           0..*     1..1
 * Project ------------------------- User
 *           ownedProjects        &gt;       owner
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
 * Project ------------------------- User
 *           projects        &lt;       members
 * </pre>
 */
private List<User> members;

public List<User> getMembers() {
   if (this.members == null) {
this.members = new ArrayList<User>();
   }
   return this.members;
}

}
