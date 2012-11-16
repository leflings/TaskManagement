package main.menu.menuitems;

import main.Application;
import main.dao.DAOFactory;
import main.dto.User;
import main.menu.MainMenu;
import main.menu.TextMenuItem;
import main.utilities.UserIOUtil;

public class LoginMenuItem extends TextMenuItem {

	private TextMenuItem login = new TextMenuItem("Login", new Runnable() {
		@Override
		public void run() {
			boolean authenticated = false;
			String username, password;
			User user = null;
			System.out.println("Indtast dine oplysninger herunder. Angiv blankt brugernavn for at vende tilbage");
			while (!authenticated) {
				username = UserIOUtil.getNullableString("Brugernavn");
				if(username == null) {
					return;
				}
				password = UserIOUtil.getNonEmptyString("Password");
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
	
	public LoginMenuItem() {
		super("Login");
		setExec(login);
	}

}
