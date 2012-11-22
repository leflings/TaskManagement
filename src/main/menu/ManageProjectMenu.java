package main.menu;

import main.Application;
import main.dto.Project;
import main.menu.menuitems.CreateProjectMenuItem;
import main.views.SelectItem;

public class ManageProjectMenu extends TextMenu {

	private TextMenuItem viewProjects = new TextMenuItem("View projects", new Runnable() {
		public void run() {
			Project project = SelectItem.getSelection(Application.User().getProjects());
			if(project != null) {
				new EditProjectMenu(project).run();
			}
		}
	});
	
	private TextMenuItem searchProjects = new TextMenuItem("Search projects", new Runnable() {
		public void run() {
			System.out.println("Søg i projekter");
		}
	});
	
	public ManageProjectMenu() {
		super("Manage projects", true, false);
		addItems(viewProjects, searchProjects, new CreateProjectMenuItem());
	}
	
	

}
