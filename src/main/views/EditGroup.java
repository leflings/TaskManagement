package main.views;

import main.dto.Group;

public class EditGroup {

	Group group;

	public EditGroup(Group group) {
		this.group = group;
	}
	
	public String editName() {
		return "Det nuværende gruppenavn er: " + group.getTitle() + "\nIndtast det nye gruppenavn";
	}
	
	public String editDescription() {
		return "Den nuværende beskrivelse af gruppen er: " + group.getDescription() + "\nIndtast din nye beskrivelse";
	}
	
	public void addUser() {
		System.out.println("Vælg hvem du ønsker at tilføje til gruppen");
	}
	
	public void removeUser() {
		System.out.println("Vælg hvem du ønsker at fjerne fra gruppen");
	}
	
	public void editOwner() {
		System.out.println("Den nuværende ejer af denne gruppe er: " + group.getOwner().getName() +
		"\nVælg hvem du ønsker den nye ejer skal være: ");
	}
	
	public void addProject() {
		System.out.println("Vælg hvilket projekt du ønsker at tilføje til gruppen");
	}
	
	public void removeProject() {
		System.out.println("Vælg hvilket projekt du ønsker at fjerne fra gruppen");
	}
	
	public void addTask() {
		System.out.println("Vælg hvilken opgave du ønsker at tilføje til gruppen");
	}
	
	public void removeTask() {
		System.out.println("Vælg hvilken opgave du ønsker at fjerne fra gruppen");
	}

	public String deleteGroup() {
		return "Er du sikker på at du ønsker at slette denne gruppe?";
	}
}
