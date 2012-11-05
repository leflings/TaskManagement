package main.views;

import java.util.List;

import main.dto.Project;
import main.utilities.SelectUtilities;


public class EditProject implements BaseView {
	List<Project> projects;
	Project selectedProjects;
	
	
	public EditProject(List<Project> projects) {
		this.projects = projects;
	}
	
	@Override
	public void print() {
		for (int i = 0; i < projects.size(); i++) {
			SelectUtilities.printSelectLine(i+1, projects.get(i).getProjectName() + " (ID: " + projects.get(i).getProjectId() + ")" );
		}
		selectedProjects = projects.get(SelectUtilities.selectChoice("Vælg fra ovenstående liste: ")+1);
	}

	
	public Project getSelectedProject() {
		return selectedProjects;
	}
}
