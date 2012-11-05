package main.views;

import java.util.List;

import main.dto.TimeEntry;;
import main.utilities.SelectUtilities;


public class EditTimeEntry implements BaseView {

	List<TimeEntry> users;
	TimeEntry selectedUser;;


	public EditTimeEntry(List<TimeEntry> timeEntries) {
		this.users = timeEntries;
	}

	@Override
	public void print() {
		for (int i = 0; i < users.size(); i++) {
			SelectUtilities.printSelectLine(i+1, users.get(i) + " (ID: " + users.get(i) + ")" );
		}
		selectedUser = users.get(SelectUtilities.selectChoice("Vælg fra ovenstående liste: ")+1);
	}

	public TimeEntry getSelectedTimeEntry() {
		return selectedUser;
	}
}
