package main.menu;

import main.Application;
import main.dto.Task;
import main.menu.menuitems.CreateTaskMenuItem;
import main.views.SelectItem;

public class ManageTaskMenu extends TextMenu {

	private TextMenuItem viewTasks = new TextMenuItem("Vælg opgave", new Runnable() {
		public void run() {
			Task task = SelectItem.getSelection(Application.User().getTasks());
			if(task != null) {
				new EditTaskMenu(task).run();
			}
		}
	});

	public ManageTaskMenu() {
		super("Adminstrér opgaver", true, false);
		addItems(viewTasks, new CreateTaskMenuItem());
	}

}
