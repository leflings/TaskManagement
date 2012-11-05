package main.dto;
import java.util.ArrayList;
import java.util.List;


public class Task {
/**
 * <pre>
 *           0..*     1..*
 * Task ------------------------- User
 *           tasks        &lt;       collaborators
 * </pre>
 */
private List<User> collaborators;

public List<User> getCollaborators() {
   if (this.collaborators == null) {
this.collaborators = new ArrayList<User>();
   }
   return this.collaborators;
}

/**
 * <pre>
 *           0..*     0..1
 * Task ------------------------- Project
 *           tasks        &lt;       project
 * </pre>
 */
private Project project;

public void setProject(Project value) {
   this.project = value;
}

public Project getProject() {
   return this.project;
}

/**
 * <pre>
 *           0..*     0..1
 * Task ------------------------- Group
 *           tasks        &lt;       group
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
 *           0..*     0..1
 * Task ------------------------- Task
 *           childTasks        &gt;       parentTask
 * </pre>
 */
private Task parentTask;

public void setParentTask(Task value) {
   this.parentTask = value;
}

public Task getParentTask() {
   return this.parentTask;
}

/**
 * <pre>
 *           0..1     0..*
 * Task ------------------------- Task
 *           parentTask        &lt;       childTasks
 * </pre>
 */
private List<Task> childTasks;

public List<Task> getChildTasks() {
   if (this.childTasks == null) {
this.childTasks = new ArrayList<Task>();
   }
   return this.childTasks;
}

/**
 * <pre>
 *           1..1     0..*
 * Task ------------------------- TimeEntry
 *           task        &lt;       timeEntry
 * </pre>
 */
private List<TimeEntry> timeEntry;

public List<TimeEntry> getTimeEntry() {
   if (this.timeEntry == null) {
this.timeEntry = new ArrayList<TimeEntry>();
   }
   return this.timeEntry;
}

private int taskId;

public void setTaskId(int value) {
   this.taskId = value;
}

public int getTaskId() {
   return this.taskId;
}

private String taskName;

public void setTaskName(String value) {
   this.taskName = value;
}

public String getTaskName() {
   return this.taskName;
}

/**
 * <pre>
 *           0..*     1..1
 * Task ------------------------- User
 *           ownedTasks        &gt;       owner
 * </pre>
 */
private User owner;

public void setOwner(User value) {
   this.owner = value;
}

public User getOwner() {
   return this.owner;
}

}
