package test.dao;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Date;

import main.dao.DAOFactory;
import main.dto.Task;
import main.dto.TimeEntry;
import main.dto.User;
import main.exceptions.DAOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TimeEntryDAOTest {

	private DAOFactory factory;
	private User user;
	private Task task;
	private TimeEntry te;
	
	@Before
	public void setUp() throws Exception {
		factory = DAOFactory.getInstance();
		user = factory.getUserDAO().getByUsername("flemming");
		task = user.getTasks().get(0);
		te = new TimeEntry();
		te.setUser(user);
		te.setTask(task);
		te.setDate(new Date());
		te.setDuration(50);
	}

	@Test
	public void testThatNewTimeEntryWillBeCreated() {
		te.save();
		
		assertTrue(te.getTimeEntryId() != 0);
		
		assertTrue(factory.getTimeEntryDAO().get(te).equals(te));
	}
	
	@Test
	public void testThatEntryAppearsOnUser() {
		int x = te.getId();
		int b = x;
		assertTrue(user.getTimeEntries().contains(te));
	}
	
	@Test
	public void testThatEntryAppearsOnTask() {
		assertTrue(task.getTimeEntries().contains(te));
	}
	
	@Test
	public void testThatEntryGetsDeleted() {
		factory.getTimeEntryDAO().delete(te);
		assertTrue(factory.getTimeEntryDAO().get(te) == null);
	}
	
	@After
	public void tearDown() throws Exception {
		try {
			factory.getTimeEntryDAO().delete(te);
		} catch (DAOException e) {
			//this should happen
		}
	}


}
