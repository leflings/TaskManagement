package main.menu;

import java.util.List;

import main.dao.DAOFactory;
import main.dao.ProjectDAO;
import main.dao.TaskDAO;
import main.dto.Group;
import main.dto.Project;
import main.dto.Task;
import main.dto.User;
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

	private TextMenuItem addMember = new TextMenuItem("Tilføj et medlem til gruppen", new Runnable() { // TODO
																										// skal
																										// ikke
																										// være
																										// her
																										// skal
																										// være
																										// under
																										// manage
																										// members

				@Override
				public void run() {
					edit.addUser();
//					UserDAO udao = DAOFactory.getInstance().getUserDAO();
					// SelectUser su = new
					// SelectUser(udao.getByNotInGroup(group));
					// su.print();
					// PermissionLevel permissionLevel = PermissionLevel.USER;
					// //TODO hvordan skal dette besluttes?
					// DAOFactory.getInstance().getGroupMembershipDAO().addMember(group,
					// su.getResult(), permissionLevel);
				}
			});

	private TextMenuItem removeMember = new TextMenuItem("Fjern et medlem fra gruppen", new Runnable() {

		@Override
		public void run() {
			edit.removeUser();
			User user = SelectItem.getSelection(group.getMembers());
			if (user != null) {
				DAOFactory.getInstance().getGroupMembershipDAO().removeMember(group, user);
			}
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

	private TextMenuItem createProject = new TextMenuItem("Opret et projekt i denne gruppe", new Runnable() {

		@Override
		public void run() {
			// TODO hvordan håndterer vi dette
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

	private TextMenuItem createTask = new TextMenuItem("Opret en opgave til gruppen", new Runnable() {

		@Override
		public void run() {
			// TODO hvordan håndterer vi dette?
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

	private TextMenuItem deleteGroup = new TextMenuItem("Slet denne gruppe", new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub

		}
	});

	public EditGroupMenu(Group group) {
		super("Group menu", true, false);
		this.group = group;
		edit = new EditGroup(group);
		addItems(editGroupTitle, editGroupDescription, addMember, removeMember, editOwner, createProject, addProject, removeProject, createTask, addTask, removeTask, deleteGroup);
	}
}
