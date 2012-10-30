package Views;

import java.util.List;

import utilities.SelectUtilities;

import DataTransferObjects.User;


public class SelectUser implements BaseView {

	private List<User> users;
	private int choice;

	public SelectUser(List<User> users){
		this.users = users;
	}
	
	@Override
	public void print() {
		
		for (int i = 0 ; i < users.size(); i++) {
			SelectUtilities.printSelectLine(i+1,users.get(i).getUserName() + " (id: " + users.get(i).getUserId() + ")");		
		}
		choice = SelectUtilities.selectChoice();
		
	}

	
	public User getResult(){
		User selectedUser = users.get(choice - 1); //Trækker en fra for at hente den rigtige user, da vi lægger én til i tidl.
		
		return selectedUser;
		
	}
	
}
