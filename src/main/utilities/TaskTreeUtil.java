package main.utilities;

import java.util.List;
import java.util.Stack;

import main.dao.DAOFactory;
import main.dto.Task;

public class TaskTreeUtil {

	private Task task;
	
	public TaskTreeUtil(Task task) {
		this.task = task;
	}
	
	public void printTaskTree() {
		Task rootTask = (task.getRootTask() == null) ? task : task.getRootTask();
		printChildTasks(rootTask, 0);
	}
	
	private void printChildTasks(Task anchor, int level) {
		printTask(anchor, level);
		for(Task task : anchor.getChildTasks()) {
			printChildTasks(task, level+1);
		}
	}
	
	private void printTask(Task task, int level) {
		String format = "%" + (1 + level*10) + "s%s%n";
		System.out.format(format, "", task.getTitle());
	}
	
}
