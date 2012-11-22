package main.views;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import main.enums.IEnum;
import main.enums.PermissionLevel;
import main.enums.Priority;
import main.enums.Status;
import main.utilities.SelectUtilities;

public class SelectEnum {

	private static Scanner scanner = new Scanner(System.in);
	
	public static PermissionLevel getPermissionLevel() {
		List<PermissionLevel> l = new ArrayList<PermissionLevel>();
		for(PermissionLevel pl : PermissionLevel.values()) {
			if(pl != PermissionLevel.OWNER) {
				l.add(pl);
			}
		}
		return getSelection(l);
	}
	
	public static Status getStatus() {
		List<Status> l = new ArrayList<Status>();
		for(Status s : Status.values()) {
			l.add(s);
		}
		return getSelection(l);
	}
	
	public static Priority getPriority() {
		List<Priority> l = new ArrayList<Priority>();
		for (Priority p : Priority.values()) {
			l.add(p);
		}
		return getSelection(l);
	}
	
	public static <T extends IEnum> T getSelection(List<T> items) {
		for (T t : items) {
			SelectUtilities.printSelectLine(t.getCode(), t.toString());
		}
		
		String answer;
		while(!(answer = ask()).equals("")) {
			int choice;
			try {
				choice = Integer.parseInt(answer);
			} catch (NumberFormatException e) {
				System.out.print("Ugyldigt valg. ");
				continue;
			}
			if(choice > 0 && choice < items.size()) {
				return (T) items.get(choice);
			} else {
				System.out.print("Ugyldigt valg. ");
			}
		}
		return null;
	}
	private static String ask() {
		System.out.print("Vælg fra listen (angiv blankt input for at gå tilbage) >");
		return scanner.nextLine();
	}
	

}
