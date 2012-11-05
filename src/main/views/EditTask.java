package main.views;

import java.util.List;

import main.dto.Task;
import main.dto.User;
import main.utilities.SelectUtilities;


public class EditTask implements BaseView {

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
		task.setGroup(value);
		task.setOwner(value);
		task.setParentTask(value);
		task.setProject(value);
		
		int choice = SelectUtilities.selectChoice("Vælg fra ovenstående liste:");
		String text;

		switch(choice) {
		case 1:	text = "Det nuværende opgave navn er: " + task.getTaskName() + "\nIndtast det nye opgave navn: ";
				task.setTaskName(SelectUtilities.inputEdit(text));
			break;
		case 2: text = "Den nuværende beskrivelse af opgaven er: " + task.getDescription() + "\nIndtast din nye beskrivelse: ";
				task.setDescription(SelectUtilities.inputEdit(text));
			break;
		case 3: text = "Dit nuværende e-mail er: " + task.getEmail() + "\nIndtast dit nye e-mail: ";
				task.setEmail(SelectUtilities.inputEdit(text));
			break;
		case 4: text = "Indtast dit nye password: ";
				task.setPassword(SelectUtilities.inputEdit(text));
			break;
		}
	}

	public Task getTask() {
		return task;
	}
}
