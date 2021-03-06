package main.menu;

import java.util.List;

import main.Application;
import main.dao.DAOFactory;
import main.dto.Group;
import main.dto.Project;
import main.dto.Task;
import main.dto.User;
import main.enums.PermissionLevel;
import main.menu.menuitems.CreateProjectMenuItem;
import main.menu.menuitems.CreateTaskMenuItem;
import main.utilities.SelectUtilities;
import main.views.EditGroup;
import main.views.SelectItem;

public class EditGroupMenu extends TextMenu {

	Group group;
	EditGroup edit;

	private TextMenuItem editGroupTitle = new TextMenuItem("Rediger gruppens titel", new Runnable() {

		@Override
		public void run() {
			group.setTitle(SelectUtilities.prompt(edit.editName()));
			group.save();
		}
	});

	private TextMenuItem editGroupDescription = new TextMenuItem("Rediger gruppens beskrivelse", new Runnable() {

		@Override
		public void run() {
			group.setTitle(SelectUtilities.prompt(edit.editDescription()));
			group.save();
		}
	});
	
	private TextMenuItem editOwner = new TextMenuItem("Vælg en ny ejer af gruppen", new Runnable() {

		@Override
		public void run() {
			edit.editOwner();
			User user = SelectItem.getSelection(group.getMembers());
			if (user != null) {
				group.removeMember(user);
				group.setOwner(user);
				group.save();
			}
		}
	});

	private TextMenuItem addProject = new TextMenuItem("Tilføj et project til gruppen", new Runnable() {

		@Override
		public void run() {
			edit.addProject();
			List<Project> projects = DAOFactory.getInstance().getProjectDAO().getByNotInGroup();
			Project project = SelectItem.getSelection(projects);
			if (project != null) {
				project.setGroup(group);
				project.save();
			}
		}
	});

	private TextMenuItem removeProject = new TextMenuItem("Fjern et project fra gruppen", new Runnable() {

		@Override
		public void run() {
			edit.removeProject();
			List<Project> projects = DAOFactory.getInstance().getProjectDAO().getByGroup(group);
			Project project = SelectItem.getSelection(projects);
			if (project != null) {
				project.setGroup(null);
				project.save();
			}
		}
	});
	
	private TextMenuItem deleteGroup = new TextMenuItem("Slet gruppe", new Runnable() {
		
		@Override
		public void run() {
			if (SelectUtilities.confirm(edit.deleteGroup())) {
				DAOFactory.getInstance().getGroupDAO().delete(group);
				setExitLoop(true);
			}
		}
	});

	private TextMenuItem addTask = new TextMenuItem("Tilføj en opgave til gruppen", new Runnable() {

		@Override
		public void run() {
			edit.addTask();
			List<Task> tasks = DAOFactory.getInstance().getTaskDAO().getTasksWithoutGroup();
			Task task = SelectItem.getSelection(tasks);
			if (task != null) {
				task.setGroup(group);
				task.save();
			}
		}
	});

	private TextMenuItem removeTask = new TextMenuItem("Fjerne opgave fra gruppe", new Runnable() {

		@Override
		public void run() {
			edit.addTask();
			Task task = SelectItem.getSelection(group.getTasks());
			if (task != null) {
				task.setGroup(null);
				task.save();
			}
		}
	});

	public EditGroupMenu(Group group) {
		super("Group menu", true, false);
		this.group = group;
		edit = new EditGroup(group);
		ManageMembersMenu manageMembers = new ManageMembersMenu(group);
		PermissionLevel pl = group.getPermissionLevel(Application.User());
		switch (pl) {
		case OWNER:
			addItems(editOwner, new CreateProjectMenuItem(group), addProject, removeProject, deleteGroup);
		case ADMIN:
			addItems(manageMembers, editGroupTitle, editGroupDescription, new CreateTaskMenuItem(group), addTask, removeTask);
		case SUPERVISOR:
		case USER:
		default:
			break;
		}
	}
}
