package main.menu;

import main.Application;
import main.dto.Group;
import main.menu.menuitems.CreateGroupMenuItem;
import main.views.SelectItem;

public class ManageGroupMenu extends TextMenu {

	private TextMenuItem viewGroups = new TextMenuItem("Vis grupper", new Runnable() {
		
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
	
	public ManageGroupMenu() {
		super("Manage groups", true, false);
		addItems(viewGroups, searchGroups, new CreateGroupMenuItem());
	}

}
