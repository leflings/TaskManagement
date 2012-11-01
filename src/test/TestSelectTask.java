package test;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import org.junit.*;

import Views.SelectTask;

import DataTransferObjects.*;

public class TestSelectTask {

	ByteArrayInputStream in = new ByteArrayInputStream("1".getBytes());
	List<Task> taskList;

	@Before
	public void setup() {
		Task task1 = new Task();
		Task task2 = new Task();
		Task task3 = new Task();

		task1.setTaskName("gør rent");
		task1.setTaskId(19);

		task2.setTaskName("støvsug");
		task2.setTaskId(32193218);

		task3.setTaskName("mug ud i stalden");
		task3.setTaskId(1);

		taskList = new ArrayList<Task>();
		taskList.add(task1);
		taskList.add(task2);
		taskList.add(task3);

	}

	@Test
	public void	testSelectTask(){

		SelectTask taskTest = new SelectTask(taskList);
		taskTest.print();
		Task selectedTask = taskTest.getResult();
		System.out.println(selectedTask.getTaskName());
	}
}
