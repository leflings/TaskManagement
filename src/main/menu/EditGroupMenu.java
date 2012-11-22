package main.menu;

import java.util.List;

import main.dao.DAOFactory;
import main.dao.ProjectDAO;
import main.dao.TaskDAO;
import main.dto.Group;
import main.dto.Project;
import main.dto.Task;
import main.dto.User;
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
			group.setTitle(SelectUtilities.inputEdit(edit.editName()));
			DAOFactory.getInstance().getGroupDAO().update(group);
		}
	});

	private TextMenuItem editGroupDescription = new TextMenuItem("Rediger gruppens beskrivelse", new Runnable() {

		@Override
		public void run() {
			group.setTitle(SelectUtilities.inputEdit(edit.editDescription()));
			DAOFactory.getInstance().getGroupDAO().update(group);
		}
	});
	
	private TextMenuItem editOwner = new TextMenuItem("Vælg en ny ejer af gruppen", new Runnable() {

		@Override
		public void run() {
			edit.editOwner();
			User user = SelectItem.getSelection(group.getMembers());
			if (user != null) {
				group.setOwner(user);
				DAOFactory.getInstance().getGroupDAO().update(group);
				DAOFactory.getInstance().getGroupMembershipDAO().removeMember(group, user);
			}
		}
	});

	private TextMenuItem addProject = new TextMenuItem("Tilføj et project til gruppen", new Runnable() {

		@Override
		public void run() {
			edit.addProject();
			ProjectDAO pdao = DAOFactory.getInstance().getProjectDAO();
			List<Project> projects = pdao.getByNotInGroup();
			Project project = SelectItem.getSelection(projects);
			if (project != null) {
				DAOFactory.getInstance().getGeneralDAO().addProjectToGroup(group, project);
			}
		}
	});

	private TextMenuItem removeProject = new TextMenuItem("Fjern et project fra gruppen", new Runnable() {

		@Override
		public void run() {
			edit.removeProject();
			ProjectDAO pdao = DAOFactory.getInstance().getProjectDAO();
			List<Project> projects = pdao.getByGroup(group);
			Project project = SelectItem.getSelection(projects);
			if (project != null) {
				DAOFactory.getInstance().getGeneralDAO().removeProjectFromGroup(group, project);
			}
		}
	});

	private TextMenuItem addTask = new TextMenuItem("Tilføj en opgave til gruppen", new Runnable() {

		@Override
		public void run() {
			edit.addTask();
			TaskDAO tdao = DAOFactory.getInstance().getTaskDAO();
			List<Task> tasks = tdao.getTasksWithoutGroup();
			Task task = SelectItem.getSelection(tasks);
			if (task != null) {
				DAOFactory.getInstance().getGeneralDAO().addTaskToGroup(group, task);
			}
		}
	});

	private TextMenuItem removeTask = new TextMenuItem("Fjerne opgave fra gruppe", new Runnable() {

		@Override
		public void run() {
			edit.addTask();
			TaskDAO tdao = DAOFactory.getInstance().getTaskDAO();
			List<Task> tasks = tdao.getByGroup(group);
			Task task = SelectItem.getSelection(tasks);
			if (task != null) {
				DAOFactory.getInstance().getGeneralDAO().removeTaskFromGroup(group, task);
			}
		}
	});

	public EditGroupMenu(Group group) {
		super("Group menu", true, false);
		this.group = group;
		edit = new EditGroup(group);
		ManageMembersMenu manageMembers = new ManageMembersMenu(group);
		addItems(editGroupTitle, 
				editGroupDescription, 
				manageMembers, 
				editOwner, 
				new CreateProjectMenuItem(group),
				addProject, 
				removeProject, 
				new CreateTaskMenuItem(group), 
				addTask, 
				removeTask);
	}
}
