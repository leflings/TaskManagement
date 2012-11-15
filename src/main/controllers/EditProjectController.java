package main.controllers;

import java.util.List;

import main.dao.GroupDAO;
import main.dao.TaskDAO;
import main.dao.UserDAO;
import main.dto.Project;
import main.dto.Task;
import main.utilities.SelectUtilities;
import main.views.EditGroup;
import main.views.EditProject;
import main.views.SelectGroup;
import main.views.SelectTask;
import main.views.SelectUser;

public class EditProjectController {

	Project project;
	EditProject edit;

	public EditProjectController(Project project) {
		this.project = project;
		edit = new EditProject(project);
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
			editOWner();
			break;
		case 6:
			addToGroup();
			break;
		case 7:
			removeFromGroup();
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
		project.setTitle(SelectUtilities.inputEdit(edit.editName()));
	}

	private void setDescription() {
		project.setDescription(SelectUtilities.inputEdit(edit.editDescription()));
	}

	private void addUser() {
		edit.addUser();
		UserDAO udao = new UserDAO();
		SelectUser su = new SelectUser(udao.allUsers().removeAll(project.getMembers()));		//TODO udao.allUsers() returnerer alle users i en List
		su.print();
		project.addMember(su.getResult());	//TODO tilføj metode addMember i dto.project
	}

	private void removeUser() {
		edit.removeUser();
		SelectUser su = new SelectUser(project.getMembers());
		su.print();
		project.removeMember(su.getResult());		//TODO tilføj metode removeMember i dto.project
	}

	private void editOWner() {
		edit.editOwner();
		SelectUser su = new SelectUser(project.getMembers());
		su.print();
		project.setOwner(su.getResult());
	}

	private void addToGroup() {
		edit.addToGroup();
		GroupDAO gdao = new GroupDAO();
		SelectGroup sg = new SelectGroup(gdao.allGroups());	//TODO gdao.allGroups returnerer alle groups
		sg.print();
		project.setGroup(sg.getResult());
	}

	private void removeFromGroup() {
		if (SelectUtilities.confirm(edit.removeFromGroup()))
			project.removeFromProject();			//TODO lav metode removeFromProject hvis det skal håndteres på denne måde
	}

	private void addTask() {
		edit.addTask();
		TaskDAO tdao = new TaskDAO();
		List<Task> allTasks = tdao.allTasks();		//TODO tilføj metode allTasks() der returenerer alle tasks i en list
		List<Task> tasks;
		for (Task task:allTasks)			//finder alle tasks og tilføjer dem der ikke er knyttet til et projekt til en list
			if (task.getProject() == null)
				tasks.add(task);
		SelectTask st = new SelectTask(tasks);
		st.print();
		project.addTask(st.getResult());	//TODO tilføj metode addTask(Task) til project
	}

	private void removeTask() {
		edit.removeTask();
		SelectTask st = new SelectTask(project.getTasks());
		st.print();
		project.removeTask(st.getResult());
	}

	public Project getProject(){
		return project;
	}
}
