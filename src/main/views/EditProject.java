package main.views;

import java.util.List;

import main.dto.Project;
import main.utilities.SelectUtilities;


public class EditProject implements BaseView {
	
	Project project;
	
	public EditProject(Project project) {
		this.project = project;
	}
	
	@Override
	public void print() {		
		System.out.println(	"[ 1] : Rediger projektets navn\n" +
							"[ 2] : Rediger beskrivelsen af projektet\n" +
							"[ 3] : Tilføj bruger til projektet\n" +
							"[ 4] : Fjern bruger fra projektet\n" +
							"[ 5] : Vælg en ny ejer af projektet\n" +
							"[ 6] : Knyt projektet til en gruppe\n" +			//TODO skal nok håndteres på en pænere måde
							"[ 7] : Fjern knytningen til en gruppe\n" +
							"[ 8] : Tilføj opgave til projektet\n" +
							"[ 9] : Fjern opgave fra projektet\n");}
	
	public int choose() {
		return SelectUtilities.selectChoice("Vælg fra ovenstående liste:");
	}
	
	public String editName() {
		return "Det nuværende projektnavn er: " + project.getProjectName() + "\nIndtast det nye projektnavn: ";
	}
	
	public String editDescription() {
		return "Den nuværende beskrivelse af projektet er: " + project.getDescription() + "\nIndtast din nye beskrivelse: ";
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
		return "Er du sikker på du vil fjerne dette projekt fra gruppen: " + project.getGroup().getName();
	}
	
	public void addTask() {
		System.out.println("Vælg hvilken opgave du ønsker at tilføje projektet");
	}
	
	public void removeTask() {
		System.out.println("Vælg hvilken opgave du ønsker at fjerne fra projektet");
	}
}	