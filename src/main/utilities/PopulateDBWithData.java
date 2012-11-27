package main.utilities;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import main.dao.DAOFactory;
import main.dto.Group;
import main.dto.Project;
import main.dto.Task;
import main.dto.TimeEntry;
import main.dto.User;
import main.enums.Priority;
import main.enums.Status;

public class PopulateDBWithData {

	public static void main(String[] args) {
		new PopulateDBWithData().run();
	}

	public PopulateDBWithData() {

	}

	public void run() {
		System.out.println("Er du sikker på at du vil populate databasen?");
		new Scanner(System.in).nextLine();
		List<User> users = DAOFactory.getInstance().getUserDAO().getAll();
		List<Project> projects = DAOFactory.getInstance().getProjectDAO().getAll();
		List<Group> groups = DAOFactory.getInstance().getGroupDAO().getAll();
		Random r = new Random();
		
		String source = "Arbejdssituationerne accentuerer udtalt basal koordinering, som trods dette måske afmystificerer nogle frugtbare kvaliteter. Således understøtter det generelle projekt de tværfaglige virkninger. Det er bevist at en anvendt og kompetent undersøgelse fornyer udvalget, såfremt ledersynsvinklerne svækker en økonomisk tilpasning. Vi slutter uden videre, at politikken delvis angår ekspertiserne, og at konklusionerne støtter en helt vidensbaseret og særlig dimension. Da de private softwareanvendelser kun sjældent erstatter edb- eller informationssystemerne, skal det understreges at dette accentuerer teoriens konceptuelle og samfundsmæssige resultater. Organisationerne afmystificerer de velstrukturerede dimensioner. Ikke mindst fordi organisatorisk koordinering klarlægger de udpræget tværfaglige paradigmer, bør vi beklage at centrene modsvarer forskningsprojekterne. Det er velkendt at et tværfagligt problemområde beskriver universitetet. Det følger da, at foregangslandet måske besværliggør ressourcen.".replaceAll(",", "").replaceAll("\\.", "");
		String[] words = source.split(" ");
		
		Task task;
		for (int i = 0; i < 25; i++) {
			task = new Task();
			task.setOwner(users.get(r.nextInt(users.size())));
			Calendar c = Calendar.getInstance();
			c.add(Calendar.HOUR, 24 * r.nextInt(60));
			task.setDeadline(c.getTime());
			int sel = r.nextInt(3);
			switch (sel) {
			case 0:
				task.setGroup(groups.get(r.nextInt(groups.size())));
				break;
			case 1:
				task.setProject(projects.get(r.nextInt(projects.size())));
			default:
				break;
			}
			task.setTitle(words[r.nextInt(words.length)] + " " + words[r.nextInt(words.length)]);
			task.setPriority(Priority.values()[r.nextInt(4)]);
			task.setStatus(Status.values()[r.nextInt(5)]);
			task.save();
		}
		
		List<Task> tasks = DAOFactory.getInstance().getTaskDAO().getAll();
		Date date;
		TimeEntry te;
		for (int i = 0; i < 100; i++) {
			te = new TimeEntry();
			te.setTask(tasks.get(r.nextInt(tasks.size())));
			te.setUser(users.get(r.nextInt(users.size())));
			te.setDuration(r.nextInt(200));
			Calendar c = Calendar.getInstance();
			c.add(Calendar.HOUR, -24 * r.nextInt(60));
			date = c.getTime();
			te.setDate(date);
			te.save();
		}
		

	}

}
