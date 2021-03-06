package main.menu.menuitems;

import main.Application;
import main.dto.Group;
import main.menu.TextMenuItem;
import main.utilities.UserIOUtil;

public class CreateGroupMenuItem extends TextMenuItem {

	private Runnable exec = new Runnable() {
		public void run() {
			String title, description;
			
			title = UserIOUtil.getNullableString("Indtast gruppenavn (angiv blank gruppenavn for at vende tilbage");
			
			if(title == null) { return; }
			
			description = UserIOUtil.getNonEmptyString("Indtast gruppe beskrivelse");
			
			Group group = new Group();
			group.setTitle(title);
			group.setDescription(description);
			group.setOwner(Application.User());
			
			group.save();
			
			System.out.println("Gruppen er nu oprettet");
		}
	};
	
	public CreateGroupMenuItem() {
		super("Opret gruppe");
		setExec(exec);
	}
}
