package main.menu;

import java.util.List;

import main.dao.DAOFactory;
import main.dao.GroupDAO;
import main.dao.TaskDAO;
import main.dao.UserDAO;
import main.dto.Project;
import main.dto.Task;
import main.utilities.SelectUtilities;
import main.views.EditProject;
import main.views.SelectGroup;
import main.views.SelectTask;
import main.views.SelectUser;

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
			SelectUser su = new SelectUser(udao.getByNotInProject(project));		//TODO udao.allUsers() returnerer alle users i en List
			su.print();
			DAOFactory.getInstance().getProjectMembershipDAO().addMember(project, su.getResult());
		}
	});
	
	TextMenuItem removeMember = new TextMenuItem("Fjern et medlem fra projektet", new Runnable() {
		
		@Override
		public void run() {
			edit.removeUser();
			SelectUser su = new SelectUser(project.getMembers());
			su.print();
			DAOFactory.getInstance().getProjectMembershipDAO().removeMember(project, su.getResult());
		}
	});
	
	TextMenuItem editOwner = new TextMenuItem("Vælg en ny ejer af gruppen", new Runnable() {
		
		@Override
		public void run() {
			edit.editOwner();
			SelectUser su = new SelectUser(project.getMembers());
			su.print();
			project.setOwner(su.getResult());
			DAOFactory.getInstance().getProjectDAO().update(project);
		}
	});
	
	TextMenuItem addToGroup = new TextMenuItem("Tilføj dette projekt til en gruppe", new Runnable() {
		
		@Override
		public void run() {
			edit.addToGroup();
			GroupDAO gdao = DAOFactory.getInstance().getGroupDAO();
			SelectGroup sg = new SelectGroup(gdao.getAll());	//TODO gdao.allGroups returnerer alle groups
			sg.print();
			DAOFactory.getInstance().getGeneralDAO().addProjectToGroup(sg.getResult(), project);
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
			SelectTask st = new SelectTask(tasks);
			st.print();
			DAOFactory.getInstance().getGeneralDAO().addTaskToProject(project, st.getResult());
		}
	});
	
	TextMenuItem removeTask = new TextMenuItem("Fjern en opgave fra projektet", new Runnable() {
		
		@Override
		public void run() {
			edit.removeTask();
			SelectTask st = new SelectTask(project.getTasks());
			st.print();
			DAOFactory.getInstance().getGeneralDAO().removeTaskFromProject(project, st.getResult());
		}
	});
	
	public EditProjectMenu(Project project) {
		super("Project menu", true, false);
		this.project = project;
		edit = new EditProject(project);
		addItems(editTitle, editDescription, addMember, removeMember, editOwner, 
				addToGroup, removeFromGroup, createTask, addTask, removeTask);
	}
}
