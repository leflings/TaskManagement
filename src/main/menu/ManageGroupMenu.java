package main.menu;

import main.Application;
import main.dto.Group;
import main.views.SelectItem;

public class ManageGroupMenu extends TextMenu {

	private TextMenuItem viewGroups = new TextMenuItem("View Groups", new Runnable() {
		
		@Override
		public void run() {
			Group group = SelectItem.getSelection(Application.User().getGroups());
			if(group != null) {
				new EditGroupMenu(group).run();
			}
			
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
