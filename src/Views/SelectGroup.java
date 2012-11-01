package Views;

import java.util.List;

import DataTransferObjects.Group;
import utilities.SelectUtilities;


public class SelectGroup implements BaseView {
	
	private List<Group> groups;
	private int choice;

	public SelectGroup(List<Group> groups){
		this.groups = groups;
	}

	@Override
	public void print() {
		for (int i = 0 ; i < groups.size(); i++) {
			SelectUtilities.printSelectLine(i+1,groups.get(i).getName() + " (id: " + groups.get(i).getGroupId() + ")");		
		}
		choice = SelectUtilities.selectChoice("Vælg fra ovenstående liste:");	
	}
	
	public Group getResult(){
		Group selectedGroup = groups.get(choice - 1);
		return selectedGroup;
	}
	
}
