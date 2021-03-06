package main.menu.menuitems;

import main.dao.DAOFactory;
import main.dto.User;
import main.menu.TextMenuItem;
import main.utilities.UserIOUtil;

public class CreateUserMenuItem extends TextMenuItem {

	private Runnable exec = new Runnable() {
		public void run() {
			String username, name, email, password;
			
			username = UserIOUtil.getNullableString("Indtast brugernavn (angiv blank brugernavn for at vende tilbage");
			while(username != null && DAOFactory.getInstance().getUserDAO().getByUsername(username) != null) {
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
			
			user.save();
			
			System.out.println("Bruger oprettet");
		}
	};
	
	public CreateUserMenuItem() {
		super("Opret bruger");
		setExec(exec);
	}

}
