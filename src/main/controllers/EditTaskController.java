package main.controllers;

import java.util.List;

import main.dao.GroupDAO;
import main.dao.ProjectDAO;
import main.dao.TaskDAO;
import main.dao.UserDAO;
import main.dto.Task;
import main.utilities.SelectUtilities;
import main.views.EditTask;
import main.views.SelectGroup;
import main.views.SelectProject;
import main.views.SelectTask;
import main.views.SelectUser;

public class EditTaskController {

	Task task;
	EditTask edit;

	public EditTaskController(Task task) {
		this.task = task;
		edit = new EditTask(task);
	}

	public void editTask() {		//TODO skal nok kaldes admin view og så laver vi flere views udfra hvilke rettigheder den enkelte bruger har

		edit.print();

		switch(edit.choose()) {
		case 1:
			editName();
			break;
		case 2:
			editDescription();
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
			addToGroup();
			break;
		case 7:
			removeFromGroup();
			break;
		case 8:
			addToProject();
			break;
		case 9:
			removeFromProject();
			break;
		case 10:
			addChildTask();
			break;
		case 11:
			removeChildTask();
			break;
		case 12:
			addToParentTask();
			break;
		case 13:
			removeFromParentTask();
			break;
		}
	}

	private void editName() {
		task.setTaskName(SelectUtilities.inputEdit(edit.editName()));
	}

	private void editDescription() {
		task.setDescription(SelectUtilities.inputEdit(edit.editDescription()));
	}

	private void addUser() {
		edit.addUser();
		UserDAO udao = new UserDAO();
		SelectUser su = new SelectUser(udao.allUsers().removeAll(task.getCollaborators()));		//TODO udao.allUsers() returnerer alle users i en List
		su.print();
		task.addUser(su.getResult());	//TODO tilføj metode addMember i dto.task
	}

	private void removeUser() {
		edit.removeUser();
		SelectUser su = new SelectUser(task.getCollaborators());
		su.print();
		task.removeUser(su.getResult());		//TODO tilføj metode removeMember i dto.task
	}

	private void editOwner() {
		edit.editOwner();
		SelectUser su = new SelectUser(task.getCollaborators());
		su.print();
		task.setOwner(su.getResult());
	}

	private void addToGroup() {
		edit.addToGroup();
		GroupDAO gdao = new GroupDAO();
		SelectGroup sg = new SelectGroup(gdao.allGroups());	//TODO gdao.allGroups returnerer alle groups
		sg.print();
		task.setGroup(sg.getResult());
	}

	private void removeFromGroup() {
		if (SelectUtilities.confirm(edit.removeFromGroup()))
			task.removeFromGroup();			//TODO lav metode removeFromGroup hvis det skal håndteres på denne måde
	}

	private void addToProject() {
		edit.addToProject();
		ProjectDAO pdao = new ProjectDAO();
		SelectProject sp = new SelectProject(pdao.allProjects());	//TODO pdao.allProjects metode skal oprettes
		sp.print();
		task.setProject(sp.getResult());
	}

	private void removeFromProject() {
		if (SelectUtilities.confirm(edit.removeFromProject()))
			task.removeFromProject();		//TODO task.removeFromProject skal oprettes
	}

	private void addChildTask() {
		edit.addChildTask();
		TaskDAO tdao = new TaskDAO();
		List<Task> allTasks = tdao.allTasks();		//TODO tilføj metode allTasks() der returenerer alle tasks i en list
		List<Task> tasks;
		for (Task task:allTasks)			//finder alle tasks og tilføjer dem der ikke er knyttet til en parentTask til en list
			if (task.getParentTask() == null)
				tasks.add(task);
		SelectTask st = new SelectTask(tasks);
		st.print();
		task.addChildTask(st.getResult());	//TODO tilføj metode addChildTask(Task) til task
	}

	private void removeChildTask() {
		edit.removeChildTask();
		SelectTask st = new SelectTask(task.getChildTasks());	//TODO lav metode task.getChildTasks() der returnerer alle subtasks til en given task
		st.print();
		task.removeChildTask(st.getResult());
	}

	private void addToParentTask() {
		edit.addToParentTask();
		TaskDAO tado = new TaskDAO();
		SelectTask st = new SelectTask(tdao.allTasks().removeAll(task.getAllChildTasks()));	//TODO lav metode task.getAllChildTasks der returnerer all subtasks og alle deres subtasks
		st.print();		
		task.addToParentTask();		//TODO lav metode der tilføjer reference til en parentTask
	}

	private void removeFromParentTask() {
		if (SelectUtilities.confirm(edit.removeFromParentTask()))
			task.removeFromParentTask();		//TODO lav metode der fjerner reference til en parentTask
	}

//	public List<Task> getAllChildTasks() {		//TODO tænker at getAllChildTasks(Task) skal se ca sådan ud men ligge i Task
//		List<Task> newList = this.getChildTasks();
//		for (Task task: newList) 
//			newList.addAll(task.getAllChildTasks());
//		return newList;
//	}

	public Task getTask() {
		return task;
	}
}