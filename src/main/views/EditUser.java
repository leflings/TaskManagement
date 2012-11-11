package main.views;

import main.dto.User;
import main.utilities.SelectUtilities;



public class EditUser implements BaseView {

	User user;

	public EditUser(User user) {
		this.user = user;
	}

	public void print() {
		System.out.println(	"[ 1] : Rediger navn" +
							"[ 2] : Rediger brugernavn" +
							"[ 3] : Rediger e-mail" +
							"[ 4] : Rediger password");
	}
		
	public int choose() {
		return SelectUtilities.selectChoice("Vælg fra ovenstående liste:");
	}
	
	public String editName() {
		return "Dit nuværende navn er: " + user.getName() + "\nIndtast dit nye navn: ";
	}
	
	public String editUserName() {
		return "Dit nuværende brugernavn er: " + user.getUsername() + "\nIndtast dit nye brugernavn: ";
	}
	
	public String editEmail() {
		return "Dit nuværende e-mail er: " + user.getEmail() + "\nIndtast dit nye e-mail: ";
	}
	
	public String editPassword() {
		return "Indtast dit nye password: ";
	}
}