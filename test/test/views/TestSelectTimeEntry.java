package test.views;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import main.dto.*;
import main.views.SelectTimeEntry;

import org.junit.*;




public class TestSelectTimeEntry {

	private List<TimeEntry> entryList;
	private ByteArrayInputStream in = new ByteArrayInputStream("1".getBytes());

	@Before
	public void setup() {
		TimeEntry entry1 = new TimeEntry();
		TimeEntry entry2 = new TimeEntry();
	
		Task task = new Task();
		User user = new User();
		
		user.setName("Karl Johan");
		task.setTitle("Køb fisk");
		
		String strDate = "12-12-2012";
		entry1.setDate(Date.valueOf(strDate));
		entry1.setDuration(3);
		entry1.setTask(task);
		entry1.setUser(user);
		
		String strDate2 = "14-08-1999";
		entry2.setDate(Date.valueOf(strDate2));
		entry2.setDuration(11);
		entry2.setTask(task);
		entry2.setUser(user);
		
		 entryList = new ArrayList<TimeEntry>();
		entryList.add(entry1);
		entryList.add(entry2);
		
		
	}
	
	@Test
	public void	testSelectTimeEntry(){
		
		SelectTimeEntry timeEntry = new SelectTimeEntry(entryList);
		System.setIn(in);
		timeEntry.print();

		TimeEntry selectedTimeEntry = timeEntry.getResult();
		
		assertEquals("Karl Johan", selectedTimeEntry.getUser().getName());
		
		
	
		
		
			
		}
}
