package main.menu;

public class ManageGroupMenu extends TextMenu {

	private TextMenuItem viewGroups = new TextMenuItem("View Groups", new Runnable() {
		public void run() {
			System.out.println("En oversigt over Groups");
		}
	});
	private TextMenuItem searchGroups = new TextMenuItem("Search Groups", new Runnable() {
		public void run() {
			System.out.println("Søg i grupper");
		}
	});
	
	private TextMenuItem newGroup = new TextMenuItem("New Group", new Runnable() {
		
		@Override
		public void run() {
			System.out.println("Opret nyt Group");
		}
	});
	
	public ManageGroupMenu() {
		super("Manage groups", true, false);
		addItems(viewGroups, searchGroups, newGroup);
	}

}
