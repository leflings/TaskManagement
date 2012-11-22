package main.menu;

import main.Application;
import main.dao.DAOFactory;
import main.utilities.SelectUtilities;
import main.views.EditUser;

public class EditUserMenu extends TextMenu {

	private EditUser edit;
	
	private TextMenuItem editName = new TextMenuItem("Rediger navn", new Runnable() {
		
		@Override
		public void run() {
//			EditUserController euc = new EditUserController(Application.getAuthenticatedUser());
//			euc.editUser();
			Application.User().setName(SelectUtilities.inputEdit(edit.editName()));
			DAOFactory.getInstance().getUserDAO().update(Application.User());
		}
	});
	
	private TextMenuItem editUserName = new TextMenuItem("Rediger brugernavn", new Runnable() {
		
		@Override
		public void run() {
			Application.User().setUsername(SelectUtilities.inputEdit(edit.editUserName()));
			DAOFactory.getInstance().getUserDAO().update(Application.User());
		}
	});
	
	private TextMenuItem editEmail = new TextMenuItem("Rediger e-mail", new Runnable() {
		
		@Override
		public void run() {
			Application.User().setEmail(SelectUtilities.inputEdit(edit.editEmail()));
			DAOFactory.getInstance().getUserDAO().update(Application.User());
		}
	});
	
	private TextMenuItem editPassword = new TextMenuItem("Rediger password", new Runnable() {
		
		@Override
		public void run() {
			Application.User().setPassword(SelectUtilities.inputEdit(edit.editPassword()));
			DAOFactory.getInstance().getUserDAO().update(Application.User());
		}
	});
	
	public EditUserMenu() {
		super("User menu", true, false);
		edit = new EditUser(Application.User());
		addItems(editName, editUserName, editEmail, editPassword);
	}
}
