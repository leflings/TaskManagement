package main.dao;

import main.dto.Project;
import main.dto.User;
import main.enums.PermissionLevel;

public class ProjectMembershipDAO extends BaseDAO {
	private static final String SQL_ADD_MEMBER = "INSERT INTO ProjectMembership VALUES (?, ?, ?)";
	private static final String SQL_REMOVE_MEMBER = "DELETE FROM ProjectMembership WHERE pm_ProjectId = ? AND pm_UserId = ?";

	protected ProjectMembershipDAO(DAOFactory daoFactory) {
		super(daoFactory);
	}

	public void addMember(Project project, User user) {
		addMember(project, user, PermissionLevel.USER);
	}
	
	public void addMember(Project project, User user, PermissionLevel permissionLevel) {
		if(!project.getMembers().contains(user) && !user.getProjects().contains(project)) {
			executeUpdate(SQL_ADD_MEMBER, project.getProjectId(), user.getUserId(), permissionLevel.getCode());
			project.getMembers().add(user);
			user.getProjects().add(project);
		}
	}
	
	public void removeMember(Project project, User user) {
		if(project.getMembers().contains(user) && user.getProjects().contains(project)) {
			executeUpdate(SQL_REMOVE_MEMBER, project.getProjectId(), user.getUserId());
			project.getMembers().remove(user);
			user.getProjects().remove(project);
		}
	}

}
