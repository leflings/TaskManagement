package main.menu;

public class MainMenu extends TextMenu {

	
	public MainMenu() {
		super("Main menu", false, true);
		ManageTaskMenu manageTaskMenu = new ManageTaskMenu();
		EditUserMenu editUserMenu = new EditUserMenu();
		ManageGroupMenu manageGroupMenu = new ManageGroupMenu();
		ManageProjectMenu manageProjectMenu = new ManageProjectMenu();
		
		addItems(editUserMenu, manageTaskMenu, manageGroupMenu, manageProjectMenu);
	
	}

}
