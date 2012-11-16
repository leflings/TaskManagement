package main.menu;

import main.Application;
import main.views.SelectTask;

public class ManageTaskMenu extends TextMenu {
	
	private TextMenuItem viewTasks = new TextMenuItem("View tasks", new Runnable() {
		public void run() {
			SelectTask st = new SelectTask(Application.getAuthenticatedUser().getTasks());
			st.print();
			new EditTaskMenu(st.getResult()).run();
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
