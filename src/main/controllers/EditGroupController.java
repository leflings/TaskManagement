package main.controllers;

import main.dto.Group;
import main.utilities.SelectUtilities;
import main.views.EditGroup;
import main.views.SelectUser;

public class EditGroupController {
	
	Group group;
	EditGroup edit;
	
	public EditGroupController(Group group) {
		this.group = group;
		edit = new EditGroup(group);
	}
	
	
	public void sdasdasdasd() {
		
		int choice = edit.choose();
		SelectUser su;

		switch(choice) {
		case 1:
			group.setName(SelectUtilities.inputEdit(edit.editName()));
			break;
		case 2: 
			group.setDescription(SelectUtilities.inputEdit(edit.editDescription()));
			break;
		case 3:
			edit.addUser();
			su = new SelectUser(dao.allUsers().removeAll(group.getMembers()));		//TODO dao.allUsers() returnerer alle users i en List
			su.print();
			group.addMember(su.getResult());	//TODO tilføj metode addMember i group
			break;
		case 4:
			edit.removeUser();
			su = new SelectUser(group.getMembers());
			su.print();
			group.removeMember(su.getResult());		//TODO tilføj metode removeMember i group
			break;
		case 5:
			edit.editOwner();
			su = new SelectUser(group.getMembers());	//TODO lav members i group til List i stedet for set
			su.print();
			group.setOwner(su.getResult());
			break;
		}
	}
	
	public Group getGroup(){
		return group;
	}

}
