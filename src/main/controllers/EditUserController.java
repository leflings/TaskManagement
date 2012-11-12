package main.controllers;

import main.dto.User;
import main.utilities.SelectUtilities;
import main.views.EditUser;

public class EditUserController {

	User user;
	EditUser edit;

	public EditUserController(User user) {
		this.user = user;
		edit = new EditUser(user);
	}


	public void editUser() {
		
		edit.print();
		int choice = edit.choose();

		switch(choice) {
		case 1:
			setName();
			break;
		case 2:
			setUsername();
			break;
		case 3:
			setEmail();
			break;
		case 4:
			setPassword();
			break;
		}
	}
	
	private void setName() {
		user.setName(SelectUtilities.inputEdit(edit.editName()));
	}
	
	private void setUsername() {
		user.setUsername(SelectUtilities.inputEdit(edit.editUserName()));
	}
	
	private void setEmail() {
		user.setEmail(SelectUtilities.inputEdit(edit.editEmail()));
	}
	
	private void setPassword() {
		user.setPassword(SelectUtilities.inputEdit(edit.editPassword()));
	}

	public User getUser() {
		return user;
	}

}
