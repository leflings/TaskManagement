package main;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import main.dao.DAOFactory;
import main.dao.GroupDAO;
import main.dao.ProjectDAO;
import main.dao.TaskDAO;
import main.dao.UserDAO;
import main.dto.Project;
import main.dto.Task;
import main.dto.User;

public class Main {

	public static void main(String[] args) {
		DAOFactory javabase = DAOFactory.getInstance();
		UserDAO users = javabase.getUserDAO();
		GroupDAO groups = javabase.getGroupDAO();
		TaskDAO tasks = javabase.getTaskDAO();
		ProjectDAO projects = javabase.getProjectDAO();
		
//		User user = users.getById(1);
//		List<Project> flemmingProjects = user.getProjects();
//		List<Task> flemmingTasks = user.getTasks();
		
		Task task = tasks.getById(1);
		System.out.println(task.getTitle());
//		task.setEstimatedTime(10);
//		tasks.update(task);
//		task = tasks.getById(1);
//		System.out.println(task.getTitle());
		
//		Task task = new Task();
//		task.setTitle("Oprettet via program");
//		task.setOwner(1);
//		task.setDeadline(new Date());
//		task.setProject(1);
//		tasks.insert(task);
//		System.out.println(task.getId());
		
		
//		System.out.println(user.getName());
//		for (Project project : flemmingProjects) {
//			System.out.println(project.getProjectName());
//		}
//		
//		for (Task task : flemmingTasks) {
//			System.out.println(task.getTitle() + " - CreatedAt:" + DateFormat.getDateTimeInstance().format(task.getCreatedAt()));
//		}
		
		
//		User user = users.getById(3);
//		Group group = groups.getById(2);
//		javabase.getGroupMembershipDAO().addMember(group, user);
		
//		for (Group g : groups.getAll()) {
//			System.out.println(g.getName());
//			for (User u : g.getMembers()) {
//				if(g.getOwner().equals(u)) {
//					System.out.println("\t* "+u.getName());
//				} else {
//					System.out.println("\t"+u.getName());
//				}
//			}
//		}
//		
//		System.out.println("\n");
//		
//		for (User u : users.getAll()) {
//			System.out.println(u.getName());
//			for (Group g : u.getGroups()) {
//				System.out.println("\t" + g.getName());
//			}
//		}
		
	}

}
