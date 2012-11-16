package main.menu;

import main.Application;
import main.views.SelectProject;

public class ManageProjectMenu extends TextMenu {

	private TextMenuItem viewProjects = new TextMenuItem("View projects", new Runnable() {
		public void run() {
			System.out.println("En oversigt over projects");
			SelectProject sp = new SelectProject(Application.getAuthenticatedUser().getProjects());
			sp.print();
			new EditProjectMenu(sp.getResult()).run();
		}
	});
	private TextMenuItem searchProjects = new TextMenuItem("Search projects", new Runnable() {
		public void run() {
			System.out.println("Søg i projekter");
		}
	});
	
	private TextMenuItem newProject = new TextMenuItem("New project", new Runnable() {
		
		@Override
		public void run() {
			System.out.println("Opret nyt project");
		}
	});
	public ManageProjectMenu() {
		super("Manage projects", true, false);
		addItems(viewProjects, searchProjects, newProject);
	}
	
	

}
