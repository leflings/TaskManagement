package main.dao;

import main.dto.Group;
import main.dto.Project;
import main.dto.Task;

public class GeneralDAO extends BaseDAO {

	protected GeneralDAO(DAOFactory daoFactory) {
		super(daoFactory);
	}
	
	public void addChildToParent(Task parent, Task child) {
		if(!parent.getChildTasks().contains(child) && !child.getParentTask().equals(parent)) {
			child.setParentTask(parent);
			child.setRootTask(parent.getRootTask() == null ? parent : parent.getRootTask());
			parent.getChildTasks().add(child);
			DAOFactory.getInstance().getTaskDAO().update(child);
		}
	}
	
	public void removeChildFromParent(Task parent, Task child) {
		if(parent.getChildTasks().contains(child) && child.getParentTask().equals(parent)) {
			parent.getChildTasks().remove(child);
			child.setParentTask(null);
			child.setRootTask(null);
			DAOFactory.getInstance().getTaskDAO().update(child);
		}
	}
	
	public void addTaskToGroup(Group group, Task task) {
		if(task.getProject() == null && !group.getTasks().contains(task)) {
			task.setGroup(group);
			group.getTasks().add(task);
			DAOFactory.getInstance().getTaskDAO().update(task);
		}
	}
	
	public void removeTaskFromGroup(Group group, Task task) {
		if(task.getGroup().equals(group) && group.getTasks().contains(task)) {
			task.setGroup(null);
			group.getTasks().remove(task);
			DAOFactory.getInstance().getTaskDAO().update(task);
		}
	}
	
	public void addProjectToGroup(Group group, Project project) {
		if(!group.getProjects().contains(project) && project.getGroup() == null) {
			project.setGroup(group);
			group.getProjects().add(project);
			DAOFactory.getInstance().getProjectDAO().update(project);
		}
	}
	
	public void removeProjectFromGroup(Group group, Project project) {
		if(group.getProjects().contains(project) && project.getGroup() != null) {
			project.setGroup(null);
			group.getProjects().remove(project);
			DAOFactory.getInstance().getProjectDAO().update(project);
		}
	}
	
	public void addTaskToProject(Project project, Task task) {
		if(!project.getTasks().contains(task) && !task.getProject().equals(project))  {
			task.setProject(project);
			project.getTasks().add(task);
			DAOFactory.getInstance().getTaskDAO().update(task);
		}
	}
	
	public void removeTaskFromProject(Project project, Task task) {
		if(project.getTasks().contains(task) && task.getProject().equals(project)) {
			project.getTasks().remove(task);
			task.setProject(null);
			DAOFactory.getInstance().getTaskDAO().update(task);
		}
	}

}
