package main.menu;

import java.util.Scanner;

import main.Application;
import main.dao.DAOFactory;
import main.dto.User;

public class WelcomeMenu extends TextMenu {
	private TextMenuItem login = new TextMenuItem("Login", new Runnable() {
		@Override
		public void run() {
			boolean authenticated = false;
			String username, password;
			User user = null;
			System.out.println("Indtast dine oplysninger herunder. Angiv blankt brugernavn for at vende tilbage");
			while (!authenticated) {
				System.out.print("Username> ");
				username = scanner.nextLine();
				if(username.length() == 0) {
					return;
				}
				System.out.print("Password> ");
				password = scanner.nextLine();
				if ((user = DAOFactory.getInstance().getUserDAO().authenticate(username, password)) != null) {
					authenticated = true;
				} else {
					System.out.println("Ikke genkendt af systemet. Prøv igen. \n");
				}
			}
			Application.setAuthenticatedUser(user);
			new MainMenu().run();
			Application.clearAuthenticatedUser();
		}
	});
	
	Scanner scanner;

	public WelcomeMenu() {
		super("Velkommen", false, true);
		scanner = new Scanner(System.in);
		addItems(new CreateUserMenuItem(), login);
	}

}
