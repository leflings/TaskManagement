package Views;

import java.util.List;

import utilities.SelectUtilities;

import DataTransferObjects.User;


public class SelectUser implements BaseView {

	private List<User> users;

	public SelectUser(List<User> users){
		this.users = users;
	}
	
	@Override
	public void print() {
		
		for (int i = 0 ; i < users.size(); i++) {
			SelectUtilities.printSelectLine(i+1,users.get(i).getUserName() + " (id: " + users.get(i).getUserId() + ")");		
		}
		
		
	}

	
	public User getResult(){
		//TODO
		
		return null;
		
	}
	
}
