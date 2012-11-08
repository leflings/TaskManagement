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
		return "Det nuværende opgave navn er: " + task.getTaskName() + "\nIndtast det nye opgave navn: ";
	}

	public String editDescription() {
		return "Den nuværende beskrivelse af opgaven er: " + task.getDescription() + "\nIndtast din nye beskrivelse: ";
	}

	
	
	public Task getTask() {
		return task;
	}

	public void addUser() {
		// TODO Auto-generated method stub
		
	}

	public void removeUser() {
		// TODO Auto-generated method stub
		
	}

	public void editOwner() {
		// TODO Auto-generated method stub
		
	}

	public void addToGroup() {
		// TODO Auto-generated method stub
		
	}

	public String removeFromGroup() {
		// TODO Auto-generated method stub
		return null;
	}

	public void addToProject() {
		// TODO Auto-generated method stub
		
	}
	
	public String removeFromProject() {
		// TODO Auto-generated method stub
		return null;
	}

	public void addChildTask() {
		// TODO Auto-generated method stub
		
	}
	
	public void removeChildTask() {
		// TODO Auto-generated method stub
		
	}

	public void addToParentTask() {
		// TODO Auto-generated method stub
		
	}

	public String removeFromParentTask() {
		// TODO Auto-generated method stub
		return null;
	}

	

	
}