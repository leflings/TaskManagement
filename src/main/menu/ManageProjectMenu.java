package main.menu;

public class ManageProjectMenu extends TextMenu {

	private TextMenuItem viewProjects = new TextMenuItem("View projects", new Runnable() {
		public void run() {
			System.out.println("En oversigt over projects");
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
