package main.controllers;

import java.util.List;

import main.dao.ProjectDAO;
import main.dao.TaskDAO;
import main.dao.UserDAO;
import main.dto.Group;
import main.dto.Project;
import main.dto.Task;
import main.utilities.SelectUtilities;
import main.views.EditGroup;
import main.views.SelectGroup;
import main.views.SelectProject;
import main.views.SelectTask;
import main.views.SelectUser;

public class EditGroupController {

	Group group;
	EditGroup edit;

	public EditGroupController(Group group) {
		this.group = group;
		edit = new EditGroup(group);
	}

	public void editGroup() {

		edit.print();
		
		switch(edit.choose()) {
		case 1:
			setName();
			break;
		case 2:
			setDescription();
			break;
		case 3:
			addUser();
			break;
		case 4:
			removeUser();
			break;
		case 5:
			editOwner();
			break;
		case 6:
			addProject();
			break;
		case 7:
			removeProject();
			break;
		case 8:
			addTask();
			break;
		case 9:
			removeTask();
			break;
		}
	}

	private void setName() {
		group.setTitle(SelectUtilities.inputEdit(edit.editName()));
	}

	private void setDescription() {
		group.setDescription(SelectUtilities.inputEdit(edit.editDescription()));
	}
	
	private void addUser() {
		edit.addUser();
		UserDAO udao = new UserDAO();
		SelectUser su = new SelectUser(udao.allUsers().removeAll(group.getMembers()));		//TODO udao.allUsers() returnerer alle users i en List
		su.print();
		group.addMember(su.getResult());	//TODO tilføj metode addMember i group
	}
	
	private void removeUser() {
		edit.removeUser();
		SelectUser su = new SelectUser(group.getMembers());	//TODO lav members i group til List
		su.print();
		group.removeMember(su.getResult());		//TODO tilføj metode removeMember(User) i group
	}
	
	private void editOwner() {
		edit.editOwner();
		SelectUser su = new SelectUser(group.getMembers());	//TODO lav members i group til List i stedet for set
		su.print();
		group.setOwner(su.getResult());
	}
	
	private void addProject() {
		edit.addProject();
		ProjectDAO pdao = new ProjectDAO();
		List<Project> allProjects = pdao.getAll();
		List<Project> projects;
		for (Project project:allProjects)		//Kigger alle projekter igennem og tilføjer alle der ikke er tilføjet en gruppe til en list
			if (project.getGroup() == null)
				projects.add(project);
		SelectProject sp = new SelectProject(projects);
		sp.print();
		group.addProject(sp.getResult());	//TODO tilføj metode addProject i group
	}
	
	private void removeProject() {
		edit.removeProject();
		SelectProject sp = new SelectProject(group.getProjects());
		sp.print();
		group.removeProject(sp.getResult());	//TODO tilføj metode removeProject i group
		
	}
	
	private void addTask() {
		edit.addTask();
		TaskDAO tdao = new TaskDAO();
		List<Task> allTasks = tdao.allTasks();
		List<Task> tasks;
		for (Task task:allTasks)		//Kigger alle tasks igennem og tilføjer alle der ikke er tilføjet en gruppe til en list
			if (task.getGroup() == null)
				tasks.add(task);
		SelectTask st = new SelectTask(tdao.allTasks().removeAll(group.getTasks()));	//TODO lav tdao.allTasks() der returnerer alle tasks
		st.print();
		group.addTask(st.getResult());	//TODO tilføj metode addTask til Group
	}
	
	private void removeTask() {
		edit.removeTask();
		SelectTask st = new SelectTask(group.getTasks());
		st.print();
		group.removeTask(st.getResult());	//TODO tilføj metode removeTask() til group
	}

	public Group getGroup(){
		return group;
	}
}