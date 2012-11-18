package main.menu;

import main.Application;
import main.dto.Task;
import main.views.SelectItem;

public class ManageTaskMenu extends TextMenu {

	private TextMenuItem viewTasks = new TextMenuItem("View tasks", new Runnable() {
		public void run() {
			Task task = SelectItem.getSelection(Application.User().getTasks());
			if(task != null) {
				new EditTaskMenu(task).run();
			}
		}
	});
	private TextMenuItem createTask = new TextMenuItem("Create task", new Runnable() {
		public void run() {
			System.out.println("Dialog til at oprette");
		}
	});

	public ManageTaskMenu() {
		super("Manage tasks", true, false);
		addItems(viewTasks, createTask);
	}

}
