package main.views;

import main.dto.User;

public class EditUser {

	User user;

	public EditUser(User user) {
		this.user = user;
	}
	
	public String editName() {
		return "Dit nuværende navn er: " + user.getName() + "\nIndtast dit nye navn";
	}
	
	public String editUserName() {
		return "Dit nuværende brugernavn er: " + user.getUsername() + "\nIndtast dit nye brugernavn";
	}
	
	public String editEmail() {
		return "Dit nuværende e-mail er: " + user.getEmail() + "\nIndtast dit nye e-mail";
	}
	
	public String editPassword() {
		return "Indtast dit nye password";
	}
}