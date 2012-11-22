package main.menu.menuitems;

import main.Application;
import main.dao.DAOFactory;
import main.dao.ProjectDAO;
import main.dto.Group;
import main.dto.Project;
import main.menu.TextMenuItem;
import main.utilities.UserIOUtil;

public class CreateProjectMenuItem extends TextMenuItem {

	private Group group;
	
	private Runnable exec = new Runnable() {
		
		@Override
		public void run() {
			ProjectDAO pdao = DAOFactory.getInstance().getProjectDAO();
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
			
			pdao.insert(project);
			
			System.out.println("Projektet er nu oprettet");	
		}
	};
	
	public CreateProjectMenuItem() {
		super("Opret projekt");
		setExec(exec);
	}
	
	public CreateProjectMenuItem(Group group) {
		super("Opret projekt");
		this.group = group;
		setExec(exec);
	}
}