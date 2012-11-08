package main;

import java.util.List;

import main.dao.DAOFactory;
import main.dao.DAOProperties;
import main.dao.GroupDAO;
import main.dao.UserDAO;
import main.dto.Group;
import main.dto.User;

public class Main {
	
	public static void main(String[] args) {
		DAOFactory javabase = DAOFactory.getInstance();
		UserDAO users = javabase.getUserDAO();
		GroupDAO groups = javabase.getGroupDAO();
		
//		User user = users.getById(3);
//		Group group = groups.getById(2);
//		javabase.getGroupMembershipDAO().addMember(group, user);
		
		for (Group g : groups.getAll()) {
			System.out.println(g.getName());
			for (User u : g.getMembers()) {
				if(g.getOwner().equals(u)) {
					System.out.println("\t* "+u.getName());
				} else {
					System.out.println("\t"+u.getName());
				}
			}
		}
		
		System.out.println("\n");
		
		for (User u : users.getAll()) {
			System.out.println(u.getName());
			for (Group g : u.getGroups()) {
				System.out.println("\t" + g.getName());
			}
		}
		
	}


}
