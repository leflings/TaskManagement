package main.menu;

import main.Application;
import main.dto.Project;
import main.menu.menuitems.CreateProjectMenuItem;
import main.views.SelectItem;

public class ManageProjectMenu extends TextMenu {

	private TextMenuItem viewProjects = new TextMenuItem("Vælg projekt", new Runnable() {
		public void run() {
			Project project = SelectItem.getSelection(Application.User().getProjects());
			if(project != null) {
				new EditProjectMenu(project).run();
			}
		}
	});
	
	public ManageProjectMenu() {
		super("Adminstrér projekter", true, false);
		addItems(viewProjects, new CreateProjectMenuItem());
	}
	
	

}
