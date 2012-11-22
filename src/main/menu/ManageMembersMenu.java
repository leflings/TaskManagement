package main.menu;

import java.util.List;

import main.dao.DAOFactory;
import main.dao.GroupMembershipDAO;
import main.dao.ProjectMembershipDAO;
import main.dao.TaskAssignmentDAO;
import main.dto.Group;
import main.dto.Project;
import main.dto.Task;
import main.dto.User;
import main.views.SelectEnum;
import main.views.SelectItem;

public class ManageMembersMenu extends TextMenu {

	private Group group;
	private Project project;
	private Task task;	
	
	public TextMenuItem addMember = new TextMenuItem("Tilføj medlem", new Runnable() {
		
		@Override
		public void run() {
			List<User> nonmembers;
			User user;
			if (group != null) {
				GroupMembershipDAO dao = DAOFactory.getInstance().getGroupMembershipDAO();
				nonmembers = DAOFactory.getInstance().getUserDAO().getByNotInGroup(group);
				user = SelectItem.getSelection(nonmembers);
				if (user != null) {
					group.addMember(user, SelectEnum.getPermissionLevel());
				}
			}
			else if (project != null) {
				ProjectMembershipDAO dao = DAOFactory.getInstance().getProjectMembershipDAO();
				nonmembers = DAOFactory.getInstance().getUserDAO().getByNotInProject(project);
				user = SelectItem.getSelection(nonmembers);
				if (user != null) {
					project.addMember(user, SelectEnum.getPermissionLevel());
				}
			}
			else if (task != null) {
				TaskAssignmentDAO dao = DAOFactory.getInstance().getTaskAssignmentDAO();
				nonmembers = DAOFactory.getInstance().getUserDAO().getByNotAssociatedWithTask(task);
				user = SelectItem.getSelection(nonmembers);
				if (user != null)
					task.addCollaborator(user);
			}
		}
	});
	
	public TextMenuItem removeMember = new TextMenuItem("Fjern medlem", new Runnable() {
		
		@Override
		public void run() {
			List<User> members;
			User user;
			if (group != null) {
				GroupMembershipDAO dao = DAOFactory.getInstance().getGroupMembershipDAO();
				members = group.getMembers();
				user = SelectItem.getSelection(members);
				if (user != null)
					dao.removeMember(group, user);
			}
			else if (project != null) {
				ProjectMembershipDAO dao = DAOFactory.getInstance().getProjectMembershipDAO();
				members = project.getMembers();
				user = SelectItem.getSelection(members);
				if (user != null)
					dao.removeMember(project, user);
			}
			else if (task != null) {
				TaskAssignmentDAO dao = DAOFactory.getInstance().getTaskAssignmentDAO();
				members = task.getCollaborators();
				user = SelectItem.getSelection(members);
				if (user != null)
					dao.removeMember(task, user);
			}
		}
	});
	
	private ManageMembersMenu() {
		super("Adminstrér medlemmer", true, false);
		addItems(addMember, removeMember);
	}
	
	public ManageMembersMenu(Group group) {
		this();
		this.group = group;
	}
	
	public ManageMembersMenu(Project project) {
		this();
		this.project = project;
	}
	
	public ManageMembersMenu(Task task) {
		this();
		this.task = task;
	}
}