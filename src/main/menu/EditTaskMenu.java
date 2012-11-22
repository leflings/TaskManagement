package main.menu;

import java.util.Date;

import main.Application;
import main.dao.DAOFactory;
import main.dto.Group;
import main.dto.Project;
import main.dto.Task;
import main.dto.User;
import main.menu.menuitems.CreateTaskMenuItem;
import main.utilities.SelectUtilities;
import main.utilities.UserIOUtil;
import main.views.EditTask;
import main.views.SelectEnum;
import main.views.SelectItem;
import main.views.ViewTask;
import main.views.ViewTaskTree;

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
			task.save();
		}
	});

	private TextMenuItem editDescription = new TextMenuItem("Rediger beskrivelse", new Runnable() {

		@Override
		public void run() {
			task.setDescription(SelectUtilities.inputEdit(edit.editDescription()));
			task.save();
		}
	});

	private TextMenuItem editOwner = new TextMenuItem("Vælg en ny ejer", new Runnable() {

		@Override
		public void run() {
			edit.editOwner();
			User user = SelectItem.getSelection(task.getCollaborators());
			if (user != null) {
				task.setOwner(user);
				task.save();
			}
		}
	});

	private TextMenuItem addToGroup = new TextMenuItem("Tilføj til en gruppe", new Runnable() {

		@Override
		public void run() {
			edit.addToGroup();
			Group group = SelectItem.getSelection(Application.User().getGroups());
			if (group != null) {
				task.setGroup(group);
				task.save();
			}
		}
	});

	private TextMenuItem removeFromGroup = new TextMenuItem("Fjern fra gruppen", new Runnable() {

		@Override
		public void run() {
			if (SelectUtilities.confirm(edit.removeFromGroup())) {
				task.setGroup(null);
				task.save();
			}
		}
	});

	private TextMenuItem addToProject = new TextMenuItem("Tilføj til et projekt", new Runnable() {

		@Override
		public void run() {
			edit.addToProject();
			Project project = SelectItem.getSelection(Application.User().getProjects());
			if(project != null) {
				task.setProject(project);
				task.save();
			}
		}
	});

	private TextMenuItem removeFromProject = new TextMenuItem("Fjern fra projekt", new Runnable() {

		@Override
		public void run() {
			if (SelectUtilities.confirm(edit.removeFromProject())) {
				task.setProject(null);
				task.save();
			}
		}
	});

	private TextMenuItem editDeadline = new TextMenuItem("Rediger deadline", new Runnable() {

		@Override
		public void run() {
			edit.editDeadline();
			Date deadline = UserIOUtil.askForDateAndTime();
			task.setDeadline(deadline);
			task.save();
		}
	});

	private TextMenuItem editStatus = new TextMenuItem("Rediger status", new Runnable() {

		@Override
		public void run() {
			edit.editStatus();
			task.setStatus(SelectEnum.getStatus());
			task.save();
		}
	});

	private TextMenuItem editPriority = new TextMenuItem("Rediger prioritet", new Runnable() {

		@Override
		public void run() {
			edit.editPriority();
			task.setPriority(SelectEnum.getPriority());
			task.save();
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
		addItems(showShortInfo, showTaskTree, editTitle, editDescription, editStatus, editPriority, editDeadline,
				new CreateTaskMenuItem(task));
		if(task.getOwner().equals(Application.User())) {
			addItems((task.getProject() == null ? addToProject : removeFromProject), manageMembers, (task.getGroup() == null ? addToGroup : removeFromGroup), editOwner, deleteTask);
			
		}
	}
}