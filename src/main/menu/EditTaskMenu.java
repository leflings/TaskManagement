package main.menu;

import java.util.List;
import main.dao.DAOFactory;
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

public class EditTaskMenu extends TextMenu {

	Task task;
	EditTask edit;
	
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
			SelectUser su = new SelectUser(udao.getByNotAssociatedWithTask(task));		//TODO metode mangler
			su.print();
			DAOFactory.getInstance().getTaskAssignmentDAO().addAssignemnt(task, su.getResult());
		}
	});
	
	TextMenuItem removeMember = new TextMenuItem("Fjern medlem", new Runnable() {
		
		@Override
		public void run() {
			edit.removeUser();
			SelectUser su = new SelectUser(task.getCollaborators());
			su.print();
			DAOFactory.getInstance().getTaskAssignmentDAO().removeMember(task, su.getResult());
		}
	});
	
	TextMenuItem editOwner = new TextMenuItem("Vælg en ny ejer", new Runnable() {
		
		@Override
		public void run() {
			edit.editOwner();
			SelectUser su = new SelectUser(task.getCollaborators());
			su.print();
			task.setOwner(su.getResult());
			DAOFactory.getInstance().getTaskDAO().update(task);
		}
	});
	
	TextMenuItem addToGroup = new TextMenuItem("Tilføj til en gruppe", new Runnable() {
		
		@Override
		public void run() {
			edit.addToGroup();
			GroupDAO gdao = DAOFactory.getInstance().getGroupDAO();
			SelectGroup sg = new SelectGroup(gdao.getAll());	//TODO gdao.allGroups returnerer alle groups
			sg.print();
			DAOFactory.getInstance().getGeneralDAO().addTaskToGroup(sg.getResult(), task);
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
			ProjectDAO pdao = DAOFactory.getInstance().getProjectDAO();
			SelectProject sp = new SelectProject(pdao.getAll());	//TODO pdao.allProjects metode skal oprettes
			sp.print();
			DAOFactory.getInstance().getGeneralDAO().addTaskToProject(sp.getResult(), task);
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
			// TODO Auto-generated method stub
		}
	});
	
//	TextMenuItem addChildTask = new TextMenuItem("Tilføj en underopgave", new Runnable() {
//		
//		@Override
//		public void run() {
//			edit.addChildTask();
//			TaskDAO tdao = DAOFactory.getInstance().getTaskDAO();
//			List<Task> tasks = tdao.getByNoParent();
//			SelectTask st = new SelectTask(tasks);
//			st.print();
//			DAOFactory.getInstance().getGeneralDAO().addChildToParent(st.getResult(), task);
//		}
//	});
	
	TextMenuItem removeChildTask = new TextMenuItem("Fjern en underopgave", new Runnable() {
		
		@Override
		public void run() {
			edit.removeChildTask();
			SelectTask st = new SelectTask(task.getChildTasks());
			st.print();
			DAOFactory.getInstance().getGeneralDAO().removeChildFromParent(st.getResult(), task);
		}
	});
	
	public EditTaskMenu(Task task) {
		super("Opgave menu", true, false);
		this.task = task;
		edit = new EditTask(task);
		addItems(editTitle, editDescription, addMember, removeMember, editOwner, 
				addToGroup, removeFromGroup, addToProject, removeFromProject, createTask);
	}
}