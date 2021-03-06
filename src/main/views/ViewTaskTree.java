package main.views;

import main.dto.Task;

public class ViewTaskTree {

	private Task selectedTask;
	
	public ViewTaskTree(Task task) {
		this.selectedTask = task;
	}
	
	public void printTaskTree() {
		System.out.println("\n=== Opgave hieraki ===");
		Task rootTask = (selectedTask.getRootTask() == null) ? selectedTask : selectedTask.getRootTask();
		printChildTasks(rootTask, 0);
	}
	
	private void printChildTasks(Task anchor, int level) {
		printTask(anchor, level);
		for(Task task : anchor.getChildTasks()) {
			printChildTasks(task, level+1);
		}
	}
	
	private void printTask(Task task, int level) {
		String format = "%-" + (2 + level*4) + "s%s%n";
		System.out.format(format, selectedTask.equals(task) ? ">>" : "", task.getTitle());
	}
	
}
