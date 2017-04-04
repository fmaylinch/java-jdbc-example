package com.codethen.javadb.service;

import com.codethen.javadb.dao.UserDao;
import com.codethen.javadb.dao.UserDaoSpring;
import com.codethen.javadb.model.User;
import com.codethen.javadb.util.DatabaseUtil;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class UserServiceTest {

	@Test
	public void testFindByDomain() {

		// Mock UserDao so it returns some users

		UserDao userDao = Mockito.mock(UserDao.class);

		List<User> fakeUsers = Arrays.asList(
			createUser("pepe@gmail.com"),
			createUser("jordi@hotmail.com"),
			createUser("marta@gmail.com") );

		Mockito.when( userDao.findAll() ).thenReturn( fakeUsers );


		// Test UserService method (test is isolated because UserDao is mocked)

		UserService userService = new UserService(userDao);

		List<User> users = userService.findByEmailDomain("gmail.com");

		Assert.assertEquals(2, users.size());
		// TODO: check more things, like emails
	}

	@Test
	public void testFindByDomainNoUsers() {

		// Mock UserDao so it returns some users

		UserDao userDao = Mockito.mock(UserDao.class);

		Mockito.when( userDao.findAll() ).thenReturn( Collections.emptyList() );


		// Test UserService method (test is isolated because UserDao is mocked)

		UserService userService = new UserService(userDao);

		List<User> users = userService.findByEmailDomain("gmail.com");

		Assert.assertEquals(0, users.size());
	}

	private User createUser(String email) {
		User user = new User();
		user.setEmail(email);
		String[] parts = email.split("@");
		user.setUsername(parts[0]);
		return user;
	}


	// This is not a unit test since it uses multiple services.
	// In unit tests we only test one class (the others are mocked if necessary).
	// When multiple services are tested we say it's an integration test.
	// In integration tests we usually test a whole operation of our business.
	//
	// Besides, this method has a problem: it connects to the real database.
	// Tests usually should not use the real database.
	//
	// @Test // <-- uncomment to enable (you'll need it to test your DAO)
	//
	public void simpleIntegrationTest() throws Exception {

		DataSource dataSource = DatabaseUtil.getDataSource();

		UserDao userDao = new UserDaoSpring(dataSource);

		UserService userService = new UserService(userDao);

		// Create users into DB

		User user = new User();
		user.setUsername("u1");
		user.setEmail("u1@test.com");
		userService.create(user);

		User user2 = new User();
		user2.setUsername("u2");
		user2.setEmail("u2@test.com");
		userService.create(user2);

		// Get users from DB

		List<User> users = userService.findAll();

		Assert.assertEquals(2, users.size());
		Assert.assertEquals( "u1", users.get(0).getUsername() );
		Assert.assertEquals( "u1@test.com", users.get(0).getEmail() );
		Assert.assertEquals( "u2", users.get(1).getUsername() );
		Assert.assertEquals( "u2@test.com", users.get(1).getEmail() );

		// remove created users
		for (User u : users) {
			userDao.delete(u.getId());
		}
	}
}
