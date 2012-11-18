package main.menu;

import main.menu.menuitems.CreateUserMenuItem;
import main.menu.menuitems.LoginMenuItem;

public class WelcomeMenu extends TextMenu {
	
	public WelcomeMenu() {
		super("Velkommen", false, true);
		addItems(new CreateUserMenuItem(), new LoginMenuItem());
	}

}
