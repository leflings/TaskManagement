package main.menu;

import java.util.prefs.BackingStoreException;

public class MainMenu extends TextMenu {

	private TextMenuItem logout = new TextMenuItem("logout");
	
	public MainMenu() {
		super("Main menu", false, false);
		ManageTaskMenu manageTaskMenu = new ManageTaskMenu();
		EditUserMenu editUserMenu = new EditUserMenu();
		ManageGroupMenu manageGroupMenu = new ManageGroupMenu();
		ManageProjectMenu manageProjectMenu = new ManageProjectMenu();
		
		addItems(logout, editUserMenu, manageTaskMenu, manageGroupMenu, manageProjectMenu);
	}

}
