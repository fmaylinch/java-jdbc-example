package com.codethen.javadb;

import java.util.List;

public class MainTestDao {

	public static void main(String[] args) {

		UserDaoComplex userDao = new UserDaoComplex();

		User user = userDao.findById(2);

		System.out.println( user );

		List<User> users = userDao.findAll();

		for (User u : users) {
			System.out.println(u);
		}

		User newUser = new User();
		newUser.setEmail("blas@test.com");
		newUser.setUsername("blas");

		// insert into users (username, name, email) values (?, ?, ?);
		// userDao.create(newUser);

		newUser.setName("taltal");
		// userDao.update(newUser);
	}
}
