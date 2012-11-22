package main.menu;

import java.util.List;

import main.dao.DAOFactory;
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
				nonmembers = DAOFactory.getInstance().getUserDAO().getByNotInGroup(group);
				user = SelectItem.getSelection(nonmembers);
				if (user != null) {
					group.addMember(user, SelectEnum.getPermissionLevel());
				}
			}
			else if (project != null) {
				nonmembers = DAOFactory.getInstance().getUserDAO().getByNotInProject(project);
				user = SelectItem.getSelection(nonmembers);
				if (user != null) {
					project.addMember(user, SelectEnum.getPermissionLevel());
				}
			}
			else if (task != null) {
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
				members = group.getMembers();
				user = SelectItem.getSelection(members);
				if (user != null)
					group.removeMember(user);
			}
			else if (project != null) {
				members = project.getMembers();
				user = SelectItem.getSelection(members);
				if (user != null)
					project.removeMember(user);
			}
			else if (task != null) {
				members = task.getCollaborators();
				user = SelectItem.getSelection(members);
				if (user != null)
					task.removeCollaborator(user);
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