package test.views;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import main.dto.Task;
import main.views.SelectTask;

import org.junit.Before;
import org.junit.Test;

public class TestSelectTask {

	ByteArrayInputStream in = new ByteArrayInputStream("1".getBytes());
	List<Task> taskList;

	@Before
	public void setup() {
		Task task1 = new Task(19);
		Task task2 = new Task(2131);
		Task task3 = new Task(1231);

		task1.setTitle("gør rent");

		task2.setTitle("støvsug");

		task3.setTitle("mug ud i stalden");

		taskList = new ArrayList<Task>();
		taskList.add(task1);
		taskList.add(task2);
		taskList.add(task3);

	}

	@Test
	public void testSelectTask() {

		SelectTask taskTest = new SelectTask(taskList);
		taskTest.print();
		Task selectedTask = taskTest.getResult();
		System.out.println(selectedTask.getTitle());
	}
}
