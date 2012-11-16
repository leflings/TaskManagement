package main.menu;

import java.util.Scanner;

import main.dao.DAOFactory;
import main.dao.UserDAO;
import main.dto.User;
import main.utilities.UserIOUtil;

public class CreateUserMenuItem extends TextMenuItem {

	private Runnable exec = new Runnable() {
		public void run() {
			UserDAO udao = DAOFactory.getInstance().getUserDAO();
			Scanner scanner = new Scanner(System.in);
			String username, name, email, password;
			
			username = UserIOUtil.getNullableString("Indtast brugernavn (angiv blank brugernavn for at vende tilbage");
			while(username != null && udao.getByUsername(username) != null) {
				username = UserIOUtil.getNullableString("Brugernavn allerede taget. Indtast brugernavn");
			}
			if(username == null) { return; }
			
			password = UserIOUtil.getNonEmptyString("Indtast password");
			name = UserIOUtil.getNonEmptyString("Indtast fulde navn");
			email = UserIOUtil.getNonEmptyString("Indtast email-adresse");
			
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
