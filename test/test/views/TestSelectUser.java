package test.views;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import main.dto.User;
import main.views.SelectUser;

import org.junit.*;



public class TestSelectUser {

	ByteArrayInputStream in = new ByteArrayInputStream("1".getBytes());
	List<User> userList;
	
	@Before
	public void setup() {
		User user1 = new User();
		User user2 = new User();
		User user3 = new User();
		
		user1.setName("Robin");
		user1.setUserId(45);
		
		user2.setName("Gunnar Fjogborg");
		user2.setUserId(10);
		
		user3.setName("Linse Kessler");
		user3.setUserId(3);
		
		userList = new ArrayList<User>();
		userList.add(user1);
		userList.add(user2);
		userList.add(user3);
	}
	
	@Test
	public void	testSelectUser(){
		SelectUser userTest = new SelectUser(userList);
		System.setIn(in);

		userTest.print();
		User selectedUser = userTest.getResult();
		assertEquals("Robin",selectedUser.getName());
	}
}
