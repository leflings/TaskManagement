package main.menu;

import main.Application;
import main.dao.DAOFactory;
import main.dao.UserDAO;
import main.dto.Group;
import main.dto.Task;
import main.dto.User;
import main.utilities.SelectUtilities;
import main.views.EditTask;
import main.views.SelectItem;
import main.views.ViewTaskTree;
import main.views.ViewTask;

public class EditTaskMenu extends TextMenu {

	Task task;
	EditTask edit;

	TextMenuItem showShortInfo = new TextMenuItem("Vis kort info", new Runnable() {

		public void run() {
			new ViewTask(task).displayShortInfo();
		}
	});

	TextMenuItem showTaskTree = new TextMenuItem("Vis opgave hieraki", new Runnable() {

		@Override
		public void run() {
			new ViewTaskTree(task).printTaskTree();
		}
	});

	TextMenuItem editTitle = new TextMenuItem("Rediger titel", new Runnable() {

		@Override
		public void run() {
			task.setTitle(SelectUtilities.inputEdit(edit.editName()));
			DAOFactory.getInstance().getTaskDAO().update(task);
		}
	});

	TextMenuItem editDescription = new TextMenuItem("Rediger beskrivelse", new Runnable() {

		@Override
		public void run() {
			task.setDescription(SelectUtilities.inputEdit(edit.editDescription()));
			DAOFactory.getInstance().getTaskDAO().update(task);
		}
	});

	TextMenuItem addMember = new TextMenuItem("Tilføj medlem", new Runnable() {

		@Override
		public void run() {
			edit.addUser();
			UserDAO udao = DAOFactory.getInstance().getUserDAO();
			User user = SelectItem.getSelection(udao.getByNotAssociatedWithTask(task));
			if (user != null) {
				DAOFactory.getInstance().getTaskAssignmentDAO().addAssignemnt(task, user);
			}
		}
	});

	TextMenuItem removeMember = new TextMenuItem("Fjern medlem", new Runnable() {

		@Override
		public void run() {
			edit.removeUser();
			User user = SelectItem.getSelection(task.getCollaborators());
			if (user != null) {
				DAOFactory.getInstance().getTaskAssignmentDAO().removeMember(task, user);
			}
		}
	});

	TextMenuItem editOwner = new TextMenuItem("Vælg en ny ejer", new Runnable() {

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

	TextMenuItem addToGroup = new TextMenuItem("Tilføj til en gruppe", new Runnable() {

		@Override
		public void run() {
			edit.addToGroup();
			Group group = SelectItem.getSelection(Application.User().getGroups());
			if (group != null) {
				DAOFactory.getInstance().getGeneralDAO().addTaskToGroup(group, task);
			}
		}
	});

	TextMenuItem removeFromGroup = new TextMenuItem("Fjern fra gruppen", new Runnable() {

		@Override
		public void run() {
			if (SelectUtilities.confirm(edit.removeFromGroup()))
				DAOFactory.getInstance().getGeneralDAO().removeTaskFromGroup(task.getGroup(), task);
		}
	});

	TextMenuItem addToProject = new TextMenuItem("Tilføj til en projekt", new Runnable() {

		@Override
		public void run() {
			edit.addToProject();
//			ProjectDAO pdao = DAOFactory.getInstance().getProjectDAO();
			// SelectProject sp = new SelectProject(pdao.getAll()); //TODO
			// pdao.allProjects metode skal oprettes
			// sp.print();
			// DAOFactory.getInstance().getGeneralDAO().addTaskToProject(sp.getResult(),
			// task);
		}
	});

	TextMenuItem removeFromProject = new TextMenuItem("Fjern fra projekt", new Runnable() {

		@Override
		public void run() {
			if (SelectUtilities.confirm(edit.removeFromProject()))
				DAOFactory.getInstance().getGeneralDAO().removeTaskFromProject(task.getProject(), task);
		}
	});

	TextMenuItem createTask = new TextMenuItem("Opret opgave", new Runnable() {

		@Override
		public void run() {
		}
	});

	// TextMenuItem addChildTask = new TextMenuItem("Tilføj en underopgave", new
	// Runnable() {
	//
	// @Override
	// public void run() {
	// edit.addChildTask();
	// TaskDAO tdao = DAOFactory.getInstance().getTaskDAO();
	// List<Task> tasks = tdao.getByNoParent();
	// SelectTask st = new SelectTask(tasks);
	// st.print();
	// DAOFactory.getInstance().getGeneralDAO().addChildToParent(st.getResult(),
	// task);
	// }
	// });

	TextMenuItem removeChildTask = new TextMenuItem("Fjern en underopgave", new Runnable() {

		@Override
		public void run() {
			edit.removeChildTask();
			// SelectTask st = new SelectTask(task.getChildTasks());
			// st.print();
			// DAOFactory.getInstance().getGeneralDAO().removeChildFromParent(st.getResult(),
			// task);
		}
	});

	public EditTaskMenu(Task task) {
		super("Opgave menu", true, false);
		this.task = task;
		edit = new EditTask(task);
		addItems(showShortInfo, showTaskTree, editTitle, editDescription, addMember, removeMember, editOwner, addToGroup, removeFromGroup, addToProject, removeFromProject, createTask);
	}
}