package main.views;

import main.dto.Task;
import main.utilities.UserIOUtil;

public class ViewTask {

	private Task task;
	
	public ViewTask(Task task) {
		this.task = task;
	}
	
	public void displayShortInfo() {
		System.out.println();
		System.out.format("Titel: %-50s%n", task.getTitle());
		System.out.format("Id: %d%nEjer: %s%n", task.getId(), task.getOwner().getName());
		System.out.format("Oprettet: %s%n", UserIOUtil.printTimeAndDate(task.getCreatedAt()));
		System.out.format("Medlemmer: %s%n", UserIOUtil.join(task.getCollaborators().toArray(), ","));
	}
	
	public void displayAllInfo() {
	}

}
