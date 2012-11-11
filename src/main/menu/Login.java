package main.menu;

import java.util.Scanner;

import main.dao.DAOFactory;
import main.dto.User;

public class Login {

	private String username;
	private String password;
	
	private Scanner scanner;
	
	public Login() {
		scanner = new Scanner(System.in);
	}
	
	public User authenticate() {
		boolean authenticated = false;
		User user = null;
		System.out.println("Velkommen til systemet. Indtast dine oplysninger herunder");
		while(!authenticated) {
			System.out.print("Username> ");
			username = scanner.nextLine();
			System.out.print("Password> ");
			password = scanner.nextLine();
			if((user = DAOFactory.getInstance().getUserDAO().authenticate(username, password)) != null) {
				authenticated = true;
			} else {
				System.out.println("Ikke genkendt af systemet. Prøv igen. \n");
			}
		}
		return user;
	}

}
