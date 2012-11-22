package main.views;

import main.dto.Task;

public class EditTask {		//TODO overvej om edit views skal implementere BaseView

	Task task;

	public EditTask(Task task) {
		this.task = task;
	}

	public String editName() {
		return "Det nuværende opgave navn er: " + task.getTitle() + "\nIndtast det nye opgave navn";
	}

	public String editDescription() {
		return "Den nuværende beskrivelse af opgaven er: " + task.getDescription() + "\nIndtast din nye beskrivelse";
	}

	public void addUser() {
		System.out.println("Vælg hvem du ønsker at tilføje til opgaven");
	}

	public void removeUser() {
		System.out.println("Vælg hvem du ønsker at fjerne fra opgaven");
	}

	public void editOwner() {
		System.out.println("Den nuværende ejer af opgaven er: " + task.getOwner() + 
				"\nVælg hvem du ønsker den nye ejer skal være: ");
	}

	public void addToGroup() {
		System.out.println("Vælg hvilken gruppe du ønsker at knytte denne opgave til");
	}

	public String removeFromGroup() {
		return "Er du sikker på du vil fjerne denne opgave fra gruppen: " + task.getGroup().getTitle();
	}

	public void addToProject() {
		System.out.println("Vælg hvilket projekt du ønsker at knytte denne opgave til");
	}
	
	public String removeFromProject() {
		return "Er du sikker på du vil fjerne denne opgave fra projektet" + task.getProject().getTitle();
	}

	public String deleteTask() {
		return "Er du sikker på du ønsker at slette denne opgave?";
	}

	public void editStatus() {
		System.out.println("Den nuværende status på opgaven er: " + task.getStatus().toString());
	}

	public void editDeadline() {
		System.out.println("Den nuværende deadline for opgaven er: " + task.getDeadline());
	}

	public void editPriority() {
		System.out.println("Den nuværende prioritet for opgaven er: " + task.getPriority());
	}
}