package main.views;

import main.dto.User;
import main.utilities.SelectUtilities;



public class EditUser implements BaseView {

	User user;

	public EditUser(User user) {
		this.user = user;
	}

	@Override
	public void print() {
		System.out.println(	"[ 1] : Rediger navn" +
							"[ 2] : Rediger brugernavn" +
							"[ 3] : Rediger e-mail" +
							"[ 4] : Rediger password");

		int choice = SelectUtilities.selectChoice("Vælg fra ovenstående liste:");
		String text;

		switch(choice) {
		case 1:	text = "Dit nuværende navn er: " + user.getName() + "\nIndtast dit nye navn: ";
				user.setName(SelectUtilities.inputEdit(text));
			break;
		case 2: text = "Dit nuværende brugernavn er: " + user.getUserName() + "\nIndtast dit nye brugernavn: ";
				user.setUserName(SelectUtilities.inputEdit(text));
			break;
		case 3: text = "Dit nuværende e-mail er: " + user.getEmail() + "\nIndtast dit nye e-mail: ";
				user.setEmail(SelectUtilities.inputEdit(text));
			break;
		case 4: text = "Indtast dit nye password: ";
				user.setPassword(SelectUtilities.inputEdit(text));
			break;
		}
	}

	public User getUser() {
		return user;
	}
}