package main.views;

import java.util.List;

import main.dto.Task;
import main.dto.User;
import main.utilities.SelectUtilities;


public class EditTask implements BaseView {		//TODO overvej om edit views skal implementere BaseView

	Task task;

	public EditTask(Task task) {
		this.task = task;
	}

	@Override
	public void print() {
		System.out.println(	"[ 1] : Rediger navn" +
							"[ 2] : Rediger beskrivelsen af opgaven" +
							"[ 3] : Rediger e-mail" +
							"[ 4] : Rediger password");
	}

	public int choose() {
		return SelectUtilities.selectChoice("Vælg fra ovenstående liste:");
	}

	public String editName() {
		return "Det nuværende opgave navn er: " + task.getTitle() + "\nIndtast det nye opgave navn: ";
	}

	public String editDescription() {
		return "Den nuværende beskrivelse af opgaven er: " + task.getDescription() + "\nIndtast din nye beskrivelse: ";
	}

	
	
	public Task getTask() {
		return task;
	}

	public void addUser() {
		
	}

	public void removeUser() {
	}

	public void editOwner() {
	}

	public void addToGroup() {
	}

	public String removeFromGroup() {
		return null;
	}

	public void addToProject() {
	}
	
	public String removeFromProject() {
		return null;
	}

	public void addChildTask() {
	}
	
	public void removeChildTask() {
	}

	public void addToParentTask() {
	}

	public void removeFromParentTask() {
	}
}