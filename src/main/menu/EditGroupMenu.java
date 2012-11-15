package main.menu;

import java.util.List;

import main.Application;
import main.dao.DAOFactory;
import main.dao.ProjectDAO;
import main.dao.TaskDAO;
import main.dao.UserDAO;
import main.dto.Group;
import main.dto.Project;
import main.dto.Task;
import main.enums.PermissionLevel;
import main.utilities.SelectUtilities;
import main.views.EditGroup;
import main.views.SelectProject;
import main.views.SelectTask;
import main.views.SelectUser;

public class EditGroupMenu extends TextMenu {

	Group group;
	EditGroup edit;
	
	private TextMenuItem editGroupTitle = new TextMenuItem("Rediger gruppens titel", new Runnable() {
		
		@Override
		public void run() {
			group.setName(SelectUtilities.inputEdit(edit.editName()));
			DAOFactory.getInstance().getGroupDAO().update(group);
		}
	});
	
	private TextMenuItem editGroupDescription = new TextMenuItem("Rediger gruppens beskrivelse", new Runnable() {
		
		@Override
		public void run() {
			group.setName(SelectUtilities.inputEdit(edit.editDescription()));
			DAOFactory.getInstance().getGroupDAO().update(group);
		}
	});
	
	private TextMenuItem addMember = new TextMenuItem("Tilføj et medlem til gruppen", new Runnable() { //TODO skal ikke være her skal være under manage members
		
		@Override
		public void run() {
			edit.addUser();
			UserDAO udao = DAOFactory.getInstance().getUserDAO();
			SelectUser su = new SelectUser(udao.getByNotInGroup(group));		//TODO getByNotInGroup
			su.print();
			PermissionLevel permissionLevel; //TODO hvordan skal dette besluttes?
			DAOFactory.getInstance().getGroupMembershipDAO().addMember(group, su.getResult(), permissionLevel);
		}
	});
	
	private TextMenuItem removeMember = new TextMenuItem("Fjern et medlem fra gruppen", new Runnable() {
		
		@Override
		public void run() {
			edit.removeUser();
			SelectUser su = new SelectUser(group.getMembers());
			su.print();
			DAOFactory.getInstance().getGroupMembershipDAO().removeMember(group, su.getResult());
		}
	});
	
	private TextMenuItem editOwner = new TextMenuItem("Vælg en ny ejer af gruppen", new Runnable() {
		
		@Override
		public void run() {
			edit.editOwner();
			SelectUser su = new SelectUser(group.getMembers());	//TODO lav members i group til List i stedet for set
			su.print();
			group.setOwner(su.getResult());
			DAOFactory.getInstance().getGroupDAO().update(group);
		}
	});
	
	private TextMenuItem createProject = new TextMenuItem("Opret et projekt i denne gruppe", new Runnable() {
		
		@Override
		public void run() {
			//TODO hvordan håndterer vi dette
		}
	});
	
	private TextMenuItem addProject = new TextMenuItem("Tilføj et project til gruppen", new Runnable() {
		
		@Override
		public void run() {
			edit.addProject();
			ProjectDAO pdao = DAOFactory.getInstance().getProjectDAO();
			List<Project> projects = pdao.getNotInGroup();	//TODO opret metode i projectdao getNotInGroup()
			SelectProject sp = new SelectProject(projects);
			sp.print();
			DAOFactory.getInstance().getGroupProjectDAO().addProject(group, sp.getResult());	//TODO tilføj klasse groupProjectDAO med metoder tilsvarende til GroupMembershipDAO
		}
	});
	
	private TextMenuItem removeProject = new TextMenuItem("Tilføj et project til gruppen", new Runnable() {
		
		@Override
		public void run() {
			edit.removeProject();
			ProjectDAO pdao = DAOFactory.getInstance().getProjectDAO();
			List<Project> projects = pdao.getByGroup(group);
			SelectProject sp = new SelectProject(projects);
			sp.print();
			DAOFactory.getInstance().getGroupProjectDAO().removeProject(group, sp.getResult());	//TODO tilføj klasse groupProjectDAO med metoder tilsvarende til GroupMembershipDAO
		}
	});
	
	private TextMenuItem createTask = new TextMenuItem("Opret en opgave til gruppen", new Runnable() {
		
		@Override
		public void run() {
			//TODO hvordan håndterer vi dette?
		}
	});
	
	private TextMenuItem addTask = new TextMenuItem("Tilføj en opgave til gruppen", new Runnable() {
		
		@Override
		public void run() {
			edit.addTask();
			TaskDAO tdao = DAOFactory.getInstance().getTaskDAO();
			List<Task> tasks = tdao.getAllNotInGroup();	//TODO finder alle tasks der ikke er i en gruppe
			SelectTask st = new SelectTask(tasks);
			st.print();
			DAOFactory.getInstance().getGroupTaskDAO().addTask(group, st.getResult());	//TODO opret klassen GroupTaskDAO med metoder tilsvarende til GroupMembershipDAO
		}
	});
	
	private TextMenuItem removeTask = new TextMenuItem("Tilføj en opgave til gruppen", new Runnable() {
		
		@Override
		public void run() {
			edit.addTask();
			TaskDAO tdao = DAOFactory.getInstance().getTaskDAO();
			List<Task> tasks = tdao.getByGroup(group);
			SelectTask st = new SelectTask(tasks);
			st.print();
			DAOFactory.getInstance().getGroupTaskDAO().removeTask(group, st.getResult());	//TODO opret klassen GroupTaskDAO med metoder tilsvarende til GroupMembershipDAO
		}
	});
	
	private TextMenuItem deleteGroup = new TextMenuItem("Slet denne gruppe", new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			
		}
	});
	
	
	
	public EditGroupMenu(Group group) {
		super("User menu", true, false);
		this.group = group;
		edit = new EditGroup(group);
		addItems(editGroupTitle, editGroupDescription, addMember, removeMember, editOwner, 
				createProject, addProject, removeProject, createTask, addTask, removeTask, deleteGroup);
	}
}