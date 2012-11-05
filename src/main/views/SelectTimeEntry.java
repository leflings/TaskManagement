package main.views;

import java.util.List;

import main.dto.TimeEntry;
import main.utilities.SelectUtilities;




public class SelectTimeEntry implements BaseView{
	
	private List<TimeEntry> timeEntries;
	private int choice;

	public SelectTimeEntry(List<TimeEntry> timeEntries){
		this.timeEntries = timeEntries;
	}

	@Override
	public void print() {
		for (int i = 0 ; i < timeEntries.size() ; i++){
			String task = timeEntries.get(i).getTask().getTaskName();
			String user = timeEntries.get(i).getUser().getName();
			String date = timeEntries.get(i).getDate();
			int duration = timeEntries.get(i).getDuration();
			SelectUtilities.printSelectLine(i +1, "taskname: " + task + "\tUsername: " + user + "\tDate: " + date +
					"\tDuration: " + duration);			
				}
		choice = SelectUtilities.selectChoice("Vælg fra ovenstående liste:");

		
	}
	
	public TimeEntry getResult(){
		TimeEntry selectedTimeEntry = timeEntries.get(choice - 1);
		return selectedTimeEntry;
	}
}
