package main;

import java.util.List;

import main.dao.DAOFactory;
import main.dao.GroupDAO;
import main.dao.UserDAO;
import main.dto.Group;
import main.dto.User;

public class Main {
	
	public static void main(String[] args) {
		DAOFactory javabase = DAOFactory.getInstance("javabase.jdbc");
		UserDAO users = javabase.getUserDAO();
		GroupDAO groups = javabase.getGroupDAO();
		
		for (User u : users.getAll()) {
			System.out.println(u.getName());
			for (Group g : u.getGroups()) {
				System.out.println("\t" + g.getName());
			}
		}
		
	}


}
