package main.views;

import java.util.List;

import main.dto.Task;
import main.utilities.SelectUtilities;



public class SelectTask implements BaseView{
	
	private List<Task> tasks;
	private int choice;

	public SelectTask(List<Task> tasks){
		this.tasks = tasks;
		
	}

	@Override
	public void print() {
	for (int i = 0 ; i < tasks.size() ; i++){
		SelectUtilities.printSelectLine(i + 1, tasks.get(i).getTitle() + " (id: " + tasks.get(i).getTaskId() + ")");
	}
	choice = SelectUtilities.selectChoice("Vælg fra ovenstående liste:");
		
	}
	
	public Task getResult(){
		Task selectedTask = tasks.get(choice - 1);
		return selectedTask;
	}
}
