package Views;

import java.util.List;

import utilities.SelectUtilities;

import DataTransferObjects.Project;


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
		choice = SelectUtilities.selectChoice();
		
	}
	
	public Project getResult(){
		Project selectedProject = projects.get(choice - 1);
		return selectedProject;
	}
}
