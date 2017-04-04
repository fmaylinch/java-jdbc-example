package com.codethen.javadb.model;

// Follows the convention of Java beans (more or less) so we can use BeanPropertyRowMapper

public class User {

	private int id;
	private String username;
	private String name;
	private String email;


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	@Override
	public String toString() {
		return id + " " + username + " " + email;
	}
}
