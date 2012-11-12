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
			Application.getAuthenticatedUser().setName(SelectUtilities.inputEdit(edit.editName()));
			DAOFactory.getInstance().getUserDAO().update(Application.getAuthenticatedUser());
		}
	});
	
	private TextMenuItem editUserName = new TextMenuItem("Rediger brugernavn", new Runnable() {
		
		@Override
		public void run() {
			Application.getAuthenticatedUser().setName(SelectUtilities.inputEdit(edit.editUserName()));
			DAOFactory.getInstance().getUserDAO().update(Application.getAuthenticatedUser());
		}
	});
	
	private TextMenuItem editEmail = new TextMenuItem("Rediger e-mail", new Runnable() {
		
		@Override
		public void run() {
			Application.getAuthenticatedUser().setName(SelectUtilities.inputEdit(edit.editEmail()));
			DAOFactory.getInstance().getUserDAO().update(Application.getAuthenticatedUser());
		}
	});
	
	private TextMenuItem editPassword = new TextMenuItem("Rediger password", new Runnable() {
		
		@Override
		public void run() {
			Application.getAuthenticatedUser().setName(SelectUtilities.inputEdit(edit.editPassword()));
			DAOFactory.getInstance().getUserDAO().update(Application.getAuthenticatedUser());
		}
	});
	
	public EditUserMenu() {
		super("User menu", true, false);
		edit = new EditUser(Application.getAuthenticatedUser());
		addItems(editName, editUserName, editEmail, editPassword);
	}
}
