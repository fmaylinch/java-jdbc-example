package com.codethen.javadb.dao;

import com.codethen.javadb.model.User;

import java.util.List;

public interface UserDao {

	/** finds all users */
	List<User> findAll();

	User findById(int id);

	void create(User user);

	void update(User user);

	void delete(int id);
}
