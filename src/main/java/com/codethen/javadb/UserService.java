package com.codethen.javadb;

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
}
