package test.utilities;

import main.dao.DAOFactory;
import main.dao.TaskDAO;
import main.dto.Task;

public class TaskTreeUtil {

	public static void main(String[] args) {
		TaskDAO tdao = DAOFactory.getInstance().getTaskDAO();
		Task root = tdao.getById(7);
		main.utilities.TaskTreeUtil tree = new main.utilities.TaskTreeUtil(root);
		tree.printTaskTree();
	}

}
