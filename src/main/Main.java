package main;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import main.dao.DAOFactory;
import main.dao.GroupDAO;
import main.dao.ProjectDAO;
import main.dao.TaskDAO;
import main.dao.UserDAO;
import main.dto.Project;
import main.dto.Task;
import main.dto.User;

public class Main {

	public static void main(String[] args) {
		
		Application app = new Application();
		app.Run();

	}

}
