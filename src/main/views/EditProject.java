package main.views;

import main.dto.Project;

public class EditProject {
	
	Project project;
	
	public EditProject(Project project) {
		this.project = project;
	}
	
	public String editName() {
		return "Det nuværende projektnavn er: " + project.getTitle() + "\nIndtast det nye projektnavn";
	}
	
	public String editDescription() {
		return "Den nuværende beskrivelse af projektet er: " + project.getDescription() + "\nIndtast din nye beskrivelse";
	}
	
	public void addUser() {
		System.out.println("Vælg hvem du ønsker at tilføje til projektet");
	}
	
	public void removeUser() {
		System.out.println("Vælg hvem du ønsker at fjerne fra projektet");
	}
	
	public void editOwner() {
		System.out.println("Den nuværende ejer af dette projekt er: " + project.getOwner().getName() +
		"\nVælg hvem du ønsker den nye ejer skal være: ");
	}
	
	public void addToGroup() {
		System.out.println("Vælg den gruppe du ønsker at knytte dette projekt til");
	}
	
	public String removeFromGroup() {
		return "Er du sikker på du vil fjerne dette projekt fra gruppen " + project.getGroup().getTitle();
	}
	
	public void addTask() {
		System.out.println("Vælg hvilken opgave du ønsker at tilføje projektet");
	}
	
	public void removeTask() {
		System.out.println("Vælg hvilken opgave du ønsker at fjerne fra projektet");
	}
}	