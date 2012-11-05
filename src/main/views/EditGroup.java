package main.views;

import java.util.List;

import main.dto.Group;
import main.utilities.SelectUtilities;


public class EditGroup implements BaseView {

	Group group;

	public EditGroup(Group group) {
		this.group = group;
	}
	
	@Override
	public void print() {
		System.out.println(	"[ 1] : Rediger gruppens navn\n" +
							"[ 2] : Rediger beskrivelsen af gruppen\n" +
							"[ 3] : Tilføj bruger til gruppen\n" +
							"[ 4] : Fjern bruger fra gruppen\n" +
							"[ 5] : Vælg en ny ejer af gruppen\n");
		
		
	}
	
	public int choose() {
		return SelectUtilities.selectChoice("Vælg fra ovenstående liste:");
	}
	
	public String editName() {
		return "Det nuværende gruppenavn er: " + group.getName() + "\nIndtast det nye gruppenavn: ";
	}
	
	public String editDescription() {
		return "Den nuværende beskrivelse af gruppen er: " + group.getDescription() + "\nIndtast din nye beskrivelse: ";
	}
	
	public String addUser() {
		return "Vælg hvem du ønsker at tilføje til gruppen";
	}
	
	public String removeUser() {
		return "Vælg hvem du ønsker at fjerne fra gruppen";
	}
	
	public String editOwner() {
		return "Den nuværende ejer af denne gruppe er: " + group.getOwner().getName() +
		"\nVælg hvem du ønsker den nye ejer skal være: ";
	}
}
