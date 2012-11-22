package main.menu;

import java.util.List;

import main.Application;
import main.dao.DAOFactory;
import main.dao.TaskDAO;
import main.dto.Group;
import main.dto.Project;
import main.dto.Task;
import main.dto.User;
import main.enums.PermissionLevel;
import main.menu.menuitems.CreateTaskMenuItem;
import main.utilities.SelectUtilities;
import main.views.EditProject;
import main.views.SelectItem;

public class EditProjectMenu extends TextMenu {

	Project project;
	EditProject edit;

	private TextMenuItem editTitle = new TextMenuItem("Rediger titel", new Runnable() {

		@Override
		public void run() {
			project.setTitle(SelectUtilities.inputEdit(edit.editName()));
			project.save();
		}
	});

	private TextMenuItem editDescription = new TextMenuItem("Rediger beskrivelse", new Runnable() {

		@Override
		public void run() {
			project.setDescription(SelectUtilities.inputEdit(edit.editDescription()));
			project.save();
		}
	});

	private TextMenuItem editOwner = new TextMenuItem("Vælg en ny ejer af gruppen", new Runnable() {

		@Override
		public void run() {
			edit.editOwner();
			User user = SelectItem.getSelection(project.getMembers());
			if (user != null) {
				project.removeMember(user);
				project.addMember(project.getOwner(), PermissionLevel.ADMIN);
				project.setOwner(user);
				project.save();
			}
		}
	});

	private TextMenuItem addToGroup = new TextMenuItem("Tilføj dette projekt til en gruppe", new Runnable() {

		@Override
		public void run() {
			edit.addToGroup();
			Group group = SelectItem.getSelection(Application.User().getGroups());
			if (group != null) {
				project.setGroup(group);
				project.save();
			}
		}
	});

	private TextMenuItem removeFromGroup = new TextMenuItem("Fjern projektet fra gruppen", new Runnable() {

		@Override
		public void run() {
			if (SelectUtilities.confirm(edit.removeFromGroup())) {
				project.setGroup(null);
				project.save();
			}
		}
	});

	private TextMenuItem addTask = new TextMenuItem("Tilføj en opgave til projektet", new Runnable() {

		@Override
		public void run() {
			edit.addTask();
			TaskDAO tdao = DAOFactory.getInstance().getTaskDAO();
			List<Task> tasks = tdao.getAll();
			Task task = SelectItem.getSelection(tasks);
			if (task != null) {
				DAOFactory.getInstance().getGeneralDAO().addTaskToProject(project, task);
			}
		}
	});

	private TextMenuItem removeTask = new TextMenuItem("Fjern en opgave fra projektet", new Runnable() {

		@Override
		public void run() {
			edit.removeTask();
			Task task = SelectItem.getSelection(project.getTasks());
			if (task != null) {
				DAOFactory.getInstance().getGeneralDAO().removeTaskFromProject(project, task);
			}
		}
	});

	public EditProjectMenu(Project project) {
		super("Project menu", true, false);
		this.project = project;
		edit = new EditProject(project);
		ManageMembersMenu manageMembers = new ManageMembersMenu(project);
		switch (project.getPermissionLevel(Application.User())) {
		case OWNER:
			addItems(editOwner, (project.getGroup() == null ? addToGroup : removeFromGroup));
		case ADMIN:
			addItems(editTitle, editDescription, manageMembers);
		case SUPERVISOR:
		case USER:
			addItems(new CreateTaskMenuItem(project));
		default:
		}
	}
}
