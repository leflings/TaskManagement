package main.menu;

import main.Application;
import main.dao.DAOFactory;
import main.dto.TimeEntry;
import main.menu.menuitems.NewTimeEntryMenuItem;
import main.utilities.SelectUtilities;
import main.views.SelectItem;

public class ManageTimeEntryMenu extends TextMenu {

	private TextMenuItem deleteTimeEntry = new TextMenuItem("Slet timeregistrering", new Runnable() {
		
		@Override
		public void run() {
			TimeEntry timeEntry = SelectItem.getSelection(Application.User().getTimeEntries());
			if(timeEntry != null && SelectUtilities.confirm("Vil du virkelig slette denne timeregistrering?")) {
				DAOFactory.getInstance().getTimeEntryDAO().delete(timeEntry);
			}
			
		}
	});
	
	public ManageTimeEntryMenu() {
		super("Adminstrer timeregistreringer", true, false);
		addItems(new NewTimeEntryMenuItem(), deleteTimeEntry);
	}

}
