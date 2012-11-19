package main.menu.menuitems;

import java.util.Date;

import main.Application;
import main.dao.DAOFactory;
import main.dto.Task;
import main.dto.TimeEntry;
import main.menu.TextMenuItem;
import main.utilities.UserIOUtil;
import main.views.SelectItem;

public class NewTimeEntryMenuItem extends TextMenuItem {
	
	public NewTimeEntryMenuItem() {
		super("Indtast timeregistrering");
		setExec(exec);
	}
	
	private Runnable exec = new Runnable() {
		
		@Override
		public void run() {
			TimeEntry te = new TimeEntry();
			Task task = SelectItem.getSelection(Application.User().getTasks());
			if(task == null) {
				return;
			} else {
				Date date;
				if((date = UserIOUtil.askForDate()) == null) {
					return;
				}
				int duration = UserIOUtil.askForDuration();
				te.setTask(task);
				te.setDate(date);
				te.setDuration(duration);
				te.setUser(Application.User());
				DAOFactory.getInstance().getTimeEntryDAO().insert(te);
			}
		}
	};
	
}
