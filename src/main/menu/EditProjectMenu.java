package main.menu;

import java.util.List;

import main.dao.DAOFactory;
import main.dao.TaskDAO;
import main.dao.UserDAO;
import main.dto.Project;
import main.dto.Task;
import main.dto.User;
import main.utilities.SelectUtilities;
import main.views.EditProject;
import main.views.SelectItem;

public class EditProjectMenu extends TextMenu {

	Project project;
	EditProject edit;
	
	TextMenuItem editTitle = new TextMenuItem("Rediger titel", new Runnable() {
		
		@Override
		public void run() {
			project.setTitle(SelectUtilities.inputEdit(edit.editName()));
			DAOFactory.getInstance().getProjectDAO().update(project);
		}
	});
	
	TextMenuItem editDescription = new TextMenuItem("Rediger beskrivelse", new Runnable() {
		
		@Override
		public void run() {
			project.setDescription(SelectUtilities.inputEdit(edit.editDescription()));
			DAOFactory.getInstance().getProjectDAO().update(project);
		}
	});
	
	TextMenuItem addMember = new TextMenuItem("Tilføj et medlem til projektet", new Runnable() {
		
		@Override
		public void run() {
			edit.addUser();
			UserDAO udao = DAOFactory.getInstance().getUserDAO();
			User user = SelectItem.getSelection(udao.getByNotInProject(project));
			if(user != null) {
				DAOFactory.getInstance().getProjectMembershipDAO().addMember(project, user);
			}
		}
	});
	
	TextMenuItem removeMember = new TextMenuItem("Fjern et medlem fra projektet", new Runnable() {
		
		@Override
		public void run() {
			edit.removeUser();
			User user = SelectItem.getSelection(project.getMembers());
			if(user != null) {
				DAOFactory.getInstance().getProjectMembershipDAO().removeMember(project, user);
			}
		}
	});
	
	TextMenuItem editOwner = new TextMenuItem("Vælg en ny ejer af gruppen", new Runnable() {
		
		@Override
		public void run() {
			edit.editOwner();
			User user = SelectItem.getSelection(project.getMembers());
			if(user != null) {
				project.setOwner(user);
				DAOFactory.getInstance().getProjectDAO().update(project);
			}
		}
	});
	
	TextMenuItem addToGroup = new TextMenuItem("Tilføj dette projekt til en gruppe", new Runnable() {
		
		@Override
		public void run() {
			edit.addToGroup();
//			GroupDAO gdao = DAOFactory.getInstance().getGroupDAO();
//			SelectGroup sg = new SelectGroup(gdao.getAll());	//TODO gdao.allGroups returnerer alle groups
//			sg.print();
//			DAOFactory.getInstance().getGeneralDAO().addProjectToGroup(sg.getResult(), project);
		}
	});
	
	TextMenuItem removeFromGroup = new TextMenuItem("Fjern projektet fra gruppen", new Runnable() {
		
		@Override
		public void run() {
			if (SelectUtilities.confirm(edit.removeFromGroup()))
				DAOFactory.getInstance().getGeneralDAO().removeProjectFromGroup(project.getGroup(), project);
		}
	});
	
	TextMenuItem createTask = new TextMenuItem("Opret en ny opgave", new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			
		}
	});
	
	TextMenuItem addTask = new TextMenuItem("Tilføj en opgave til projektet", new Runnable() {
		
		@Override
		public void run() {
			edit.addTask();
			TaskDAO tdao = DAOFactory.getInstance().getTaskDAO();
			List<Task> tasks = tdao.getAll();
			Task task = SelectItem.getSelection(tasks);
			if(task != null ) {
				DAOFactory.getInstance().getGeneralDAO().addTaskToProject(project, task);
			}
		}
	});
	
	TextMenuItem removeTask = new TextMenuItem("Fjern en opgave fra projektet", new Runnable() {
		
		@Override
		public void run() {
			edit.removeTask();
			Task task = SelectItem.getSelection(project.getTasks());
			if(task != null) {
				DAOFactory.getInstance().getGeneralDAO().removeTaskFromProject(project, task);
			}
		}
	});
	
	public EditProjectMenu(Project project) {
		super("Project menu", true, false);
		this.project = project;
		edit = new EditProject(project);
		addItems(editTitle,
				editDescription,
				addMember,
				removeMember,
				editOwner, 
				(project.getGroup() == null ? addToGroup : removeFromGroup),
				createTask,
				addTask,
				removeTask);
	}
}
