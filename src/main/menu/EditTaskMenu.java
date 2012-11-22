package main.menu;

import java.util.Date;

import main.Application;
import main.dao.DAOFactory;
import main.dao.UserDAO;
import main.dto.Group;
import main.dto.Task;
import main.dto.User;
import main.enums.Priority;
import main.enums.Status;
import main.menu.menuitems.CreateTaskMenuItem;
import main.utilities.SelectUtilities;
import main.utilities.UserIOUtil;
import main.views.EditTask;
import main.views.SelectEnum;
import main.views.SelectItem;
import main.views.ViewTaskTree;
import main.views.ViewTask;

public class EditTaskMenu extends TextMenu {

	private Task task;
	private EditTask edit;

	private TextMenuItem showShortInfo = new TextMenuItem("Vis kort info", new Runnable() {

		public void run() {
			new ViewTask(task).displayShortInfo();
		}
	});

	private TextMenuItem showTaskTree = new TextMenuItem("Vis opgave hieraki", new Runnable() {

		@Override
		public void run() {
			new ViewTaskTree(task).printTaskTree();
		}
	});

	private TextMenuItem editTitle = new TextMenuItem("Rediger titel", new Runnable() {

		@Override
		public void run() {
			task.setTitle(SelectUtilities.inputEdit(edit.editName()));
			DAOFactory.getInstance().getTaskDAO().update(task);
		}
	});

	private TextMenuItem editDescription = new TextMenuItem("Rediger beskrivelse", new Runnable() {

		@Override
		public void run() {
			task.setDescription(SelectUtilities.inputEdit(edit.editDescription()));
			DAOFactory.getInstance().getTaskDAO().update(task);
		}
	});

	private TextMenuItem editOwner = new TextMenuItem("Vælg en ny ejer", new Runnable() {

		@Override
		public void run() {
			edit.editOwner();
			User user = SelectItem.getSelection(task.getCollaborators());
			if (user != null) {
				task.setOwner(user);
				DAOFactory.getInstance().getTaskDAO().update(task);
			}
		}
	});

	private TextMenuItem addToGroup = new TextMenuItem("Tilføj til en gruppe", new Runnable() {

		@Override
		public void run() {
			edit.addToGroup();
			Group group = SelectItem.getSelection(Application.User().getGroups());
			if (group != null) {
				DAOFactory.getInstance().getGeneralDAO().addTaskToGroup(group, task);
			}
		}
	});

	private TextMenuItem removeFromGroup = new TextMenuItem("Fjern fra gruppen", new Runnable() {

		@Override
		public void run() {
			if (SelectUtilities.confirm(edit.removeFromGroup()))
				DAOFactory.getInstance().getGeneralDAO().removeTaskFromGroup(task.getGroup(), task);
		}
	});

	private TextMenuItem addToProject = new TextMenuItem("Tilføj til et projekt", new Runnable() {

		@Override
		public void run() {
			edit.addToProject();
			// ProjectDAO pdao = DAOFactory.getInstance().getProjectDAO();
			// SelectProject sp = new SelectProject(pdao.getAll()); //TODO
			// pdao.allProjects metode skal oprettes
			// sp.print();
			// DAOFactory.getInstance().getGeneralDAO().addTaskToProject(sp.getResult(),
			// task);
		}
	});

	private TextMenuItem removeFromProject = new TextMenuItem("Fjern fra projekt", new Runnable() {

		@Override
		public void run() {
			if (SelectUtilities.confirm(edit.removeFromProject()))
				DAOFactory.getInstance().getGeneralDAO().removeTaskFromProject(task.getProject(), task);
		}
	});

	private TextMenuItem editDeadline = new TextMenuItem("Rediger deadline", new Runnable() {

		@Override
		public void run() {
			edit.editDeadline();
			Date deadline = UserIOUtil.askForDateAndTime();
			if (deadline != null) {
				task.setDeadline(deadline);
				DAOFactory.getInstance().getTaskDAO().update(task);
			}
		}
	});

	private TextMenuItem editStatus = new TextMenuItem("Rediger status", new Runnable() {

		@Override
		public void run() {
			edit.editStatus();
			Status status = SelectEnum.getStatus();
			if (status != null) {
				task.setStatus(status);
				DAOFactory.getInstance().getTaskDAO().update(task);
			}
		}
	});

	private TextMenuItem editPriority = new TextMenuItem("Rediger prioritet", new Runnable() {

		@Override
		public void run() {
			edit.editPriority();
			Priority priority = SelectEnum.getPriority();
			if (priority != null) {
				task.setPriority(priority);
				DAOFactory.getInstance().getTaskDAO().update(task);
			}
		}
	});

	private TextMenuItem deleteTask = new TextMenuItem("Slet opgave", new Runnable() {

		@Override
		public void run() {
			if (SelectUtilities.confirm(edit.deleteTask())) {
				DAOFactory.getInstance().getTaskDAO().delete(task);
				setExitLoop(true);
			}
		}
	});

	public EditTaskMenu(Task task) {
		super("Opgave menu", true, false);
		this.task = task;
		edit = new EditTask(task);
		ManageMembersMenu manageMembers = new ManageMembersMenu(task);
		addItems(showShortInfo, showTaskTree, editTitle, editDescription, manageMembers, editOwner, editStatus, editPriority, editDeadline, (task.getGroup() == null ? addToGroup : removeFromGroup),
				(task.getProject() == null ? addToProject : removeFromProject), new CreateTaskMenuItem(task), deleteTask);
	}
}