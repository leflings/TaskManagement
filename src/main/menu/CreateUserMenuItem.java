package main.menu;

import java.util.Scanner;

import main.dao.DAOFactory;
import main.dao.UserDAO;
import main.dto.User;

public class CreateUserMenuItem extends TextMenuItem {

	private Runnable exec = new Runnable() {
		public void run() {
			UserDAO udao = DAOFactory.getInstance().getUserDAO();
			Scanner scanner = new Scanner(System.in);
			String username, name, email, password;
			System.out.print("Indtast brugernavn >");
			username = scanner.nextLine();
			while(username.length() == 0 || udao.getByUsername(username) != null) {
				System.out.print("Brugernavn allerede taget. Indtast brugernavn >");
				username = scanner.nextLine();
			}
			System.out.print("Indtast password >");
			password = scanner.nextLine();
			System.out.print("Indtast fulde navn >");
			name = scanner.nextLine();
			System.out.print("Indtast email-adresse >");
			email = scanner.nextLine();
			
			User user = new User();
			user.setUsername(username);
			user.setName(name);
			user.setPassword(password);
			user.setEmail(email);
			
			udao.insert(user);
			
			System.out.println("Bruger oprettet");
		}
	};
	
	public CreateUserMenuItem() {
		super("Opret bruger");
		setExec(exec);
	}

}
