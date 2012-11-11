package main.menu;

public class ManageTaskMenu extends TextMenu {
	
	private TextMenuItem viewTasks = new TextMenuItem("View tasks", new Runnable() {
		public void run() {
			System.out.println("En oversigt over tasks");
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
