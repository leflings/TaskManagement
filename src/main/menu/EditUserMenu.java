package main.menu;

import main.Application;
import main.controllers.EditUserController;
import main.dao.DAOFactory;

public class EditUserMenu extends TextMenu {

	private TextMenuItem editUsername = new TextMenuItem("Edit user", new Runnable() {
		
		@Override
		public void run() {
			EditUserController euc = new EditUserController(Application.getAuthenticatedUser());
			euc.editUser();
			DAOFactory.getInstance().getUserDAO().update(Application.getAuthenticatedUser());
		}
	});
	
	public EditUserMenu() {
		super("User menu", true, false);
		addItems(editUsername);
	}

}
