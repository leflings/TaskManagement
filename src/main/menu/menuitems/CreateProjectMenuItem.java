package main.menu.menuitems;

import main.Application;
import main.dto.Group;
import main.dto.Project;
import main.menu.TextMenuItem;
import main.utilities.UserIOUtil;

public class CreateProjectMenuItem extends TextMenuItem {

	private Group group;
	
	private Runnable exec = new Runnable() {
		
		@Override
		public void run() {
			String title, description;
			
			title = UserIOUtil.getNullableString("Indtast projektnavn (angiv blank projektnavn for at vende tilbage");
			
			if(title == null) { return; }
			
			description = UserIOUtil.getNonEmptyString("Indtast projekt beskrivelse");
			
			Project project = new Project();
			project.setTitle(title);
			project.setDescription(description);
			project.setOwner(Application.User());
			if (group != null)
				project.setGroup(group);
			
			project.save();
			
			System.out.println("Projektet er nu oprettet");	
		}
	};
	
	public CreateProjectMenuItem() {
		super("Opret projekt");
		setExec(exec);
	}
	
	public CreateProjectMenuItem(Group group) {
		this();
		this.group = group;
	}
}