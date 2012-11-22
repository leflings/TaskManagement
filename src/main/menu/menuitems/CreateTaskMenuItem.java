package main.menu.menuitems;

import java.util.Date;

import main.Application;
import main.dto.Group;
import main.dto.Project;
import main.dto.Task;
import main.enums.Priority;
import main.enums.Status;
import main.menu.TextMenuItem;
import main.utilities.UserIOUtil;
import main.views.SelectEnum;

public class CreateTaskMenuItem extends TextMenuItem {

	private Group group;
	private Project project;
	private Task parentTask;
	private Task rootTask;
	
	private Runnable exec = new Runnable() {
		
		@Override
		public void run() {
			String title, description;
			Priority priority;
			Status status;
			Date deadline;
			
			title = UserIOUtil.getNullableString("Indtast opgavenavn (angiv blank opgavenavn for at vende tilbage");
			if(title == null) { return; }
			
			description = UserIOUtil.getNonEmptyString("Indtast opgave beskrivelse");
			deadline = UserIOUtil.askForDateAndTime();
			priority = SelectEnum.getPriority();
			status = SelectEnum.getStatus();
			
			
			Task task = new Task();
			task.setTitle(title);
			task.setDescription(description);
			task.setOwner(Application.User());
			task.setPriority(priority);
			task.setStatus(status);
			task.setDeadline(deadline);
			if (group != null)
				task.setGroup(group);
			if (project != null)
				task.setProject(project);
			if (parentTask != null) {
				task.setParentTask(parentTask);
				task.setRootTask(rootTask);
			}
			
			task.save();
			
			System.out.println("Opgaven er nu oprettet");
		}
	};
	
	public CreateTaskMenuItem() {
		super("Opret opgave");
		setExec(exec);
	}
	
	public CreateTaskMenuItem(Group group) {
		this();
		this.group = group;
	}
	
	public CreateTaskMenuItem(Project project) {
		this();
		this.project = project;
		if (project.getGroup() != null)
			group = project.getGroup();
	}
	
	public CreateTaskMenuItem(Task task) {
		this();
		this.parentTask = task;
		this.rootTask = (parentTask.getRootTask() == null) ? task : parentTask.getRootTask();
		if (task.getGroup() != null)
			group = task.getGroup();
		if (task.getProject() != null)
			project = task.getProject();
	}
}
