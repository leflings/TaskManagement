package main.dao;

import main.dto.Group;
import main.dto.Project;

public class GeneralDAO extends BaseDAO {

	protected GeneralDAO(DAOFactory daoFactory) {
		super(daoFactory);
	}
	
	public void addProjectToGroup(Group group, Project project) {
		if(!group.getProjects().contains(project) && project.getGroup() == null) {
			project.setGroup(group);
			group.getProjects().add(project);
			DAOFactory.getInstance().getProjectDAO().update(project);
		}
	}
	
	public void removeProjectFromGroup(Group group, Project project) {
		if(group.getProjects().contains(project) && project.getGroup() != null) {
			project.setGroup(null);
			group.getProjects().remove(project);
			DAOFactory.getInstance().getProjectDAO().update(project);
		}
	}

}
