package main.menu;

import main.Application;
import main.dto.Group;
import main.menu.menuitems.CreateGroupMenuItem;
import main.views.SelectItem;

public class ManageGroupMenu extends TextMenu {

	private TextMenuItem viewGroups = new TextMenuItem("Vælg grupper", new Runnable() {
		
		@Override
		public void run() {
			Group group = SelectItem.getSelection(Application.User().getGroups());
			if(group != null) {
				new EditGroupMenu(group).run();
			}
			
		}
	});
	
	public ManageGroupMenu() {
		super("Adminstrér grupper", true, false);
		addItems(viewGroups, new CreateGroupMenuItem());
	}

}
