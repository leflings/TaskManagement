package main.views;

import java.util.List;

import main.dto.Project;
import main.utilities.SelectUtilities;




public class SelectProject implements BaseView{

	private List<Project> projects;
	private int choice;

	public SelectProject(List<Project> projects){
		this.projects = projects;
	}
	
	@Override
	public void print() {
		for (int i = 0 ; i < projects.size(); i++) {
			SelectUtilities.printSelectLine(i+1,projects.get(i).getProjectName() + " (id: " + projects.get(i).getProjectId() + ")");		
		}
		choice = SelectUtilities.selectChoice("Vælg fra ovenstående liste:");
		
	}
	
	public Project getResult(){
		Project selectedProject = projects.get(choice - 1);
		return selectedProject;
	}
}
