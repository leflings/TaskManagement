package main.menu;

import java.util.Scanner;

import main.Application;
import main.dao.DAOFactory;
import main.dto.User;
import main.menu.menuitems.CreateUserMenuItem;
import main.menu.menuitems.LoginMenuItem;

public class WelcomeMenu extends TextMenu {
	
	public WelcomeMenu() {
		super("Velkommen", false, true);
		addItems(new CreateUserMenuItem(), new LoginMenuItem());
	}

}
