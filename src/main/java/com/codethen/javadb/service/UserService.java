package com.codethen.javadb.service;

import com.codethen.javadb.dao.UserDao;
import com.codethen.javadb.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserService {

	private UserDao userDao;
	// private MailService mailService;

	public UserService(UserDao userDao) {
		this.userDao = userDao;
	}

	public void create(User user) {
		userDao.create(user);
		// mailService.sendWelcomeEmail(user.getEmail());
	}

	public List<User> findAll() {
		return userDao.findAll();
	}

	public List<User> findByEmailDomain(String domain) {

		// This method could be done by the DAO but for learning purposes
		// let's suppose we have to code the logic here (so we can test it)

		List<User> all = findAll();

		List<User> result = new ArrayList<>();

		for (User user : all) {
			if (user.getEmail().endsWith(domain)) {
				result.add(user);
			}
		}

		return result;
	}
}
