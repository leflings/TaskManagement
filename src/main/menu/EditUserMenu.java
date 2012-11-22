package main.menu;

import main.Application;
import main.utilities.SelectUtilities;
import main.views.EditUser;

public class EditUserMenu extends TextMenu {

	private EditUser edit;
	
	private TextMenuItem editName = new TextMenuItem("Rediger navn", new Runnable() {
		
		@Override
		public void run() {
			Application.User().setName(SelectUtilities.prompt(edit.editName()));
			Application.User().save();
		}
	});
	
	private TextMenuItem editUserName = new TextMenuItem("Rediger brugernavn", new Runnable() {
		
		@Override
		public void run() {
			Application.User().setUsername(SelectUtilities.prompt(edit.editUserName()));
			Application.User().save();
		}
	});
	
	private TextMenuItem editEmail = new TextMenuItem("Rediger e-mail", new Runnable() {
		
		@Override
		public void run() {
			Application.User().setEmail(SelectUtilities.prompt(edit.editEmail()));
			Application.User().save();
		}
	});
	
	private TextMenuItem editPassword = new TextMenuItem("Rediger password", new Runnable() {
		
		@Override
		public void run() {
			Application.User().setPassword(SelectUtilities.prompt(edit.editPassword()));
			Application.User().save();
		}
	});
	
	public EditUserMenu() {
		super("Rediger bruger", true, false);
		edit = new EditUser(Application.User());
		addItems(editName, editUserName, editEmail, editPassword);
	}
}
