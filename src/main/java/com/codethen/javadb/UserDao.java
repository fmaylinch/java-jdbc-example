package com.codethen.javadb;

import java.util.List;

public interface UserDao {

	/** finds all users */
	List<User> findAll();

	User findById(int id);

	void create(User user);

	void update(User user);

	void delete(int id);
}
