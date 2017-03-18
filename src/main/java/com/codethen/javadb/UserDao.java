package com.codethen.javadb;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAOs (Data Access Objects) are responsible of talking with the database to read/write users
 */
public class UserDao {

	public List<User> findAll() {

		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			// get connection
			conn = DatabaseUtil.getConnection();

			// prepare and execute query
			stmt = conn.prepareStatement("select * from users");
			rs = stmt.executeQuery();

			List<User> users = new ArrayList<User>();

			while (rs.next()) {

				User user = getUser(rs);
				users.add(user);
			}

			return users;

		} catch(Exception e) {
			throw new RuntimeException("Error finding user", e);
		} finally {
			close(rs, stmt, conn);
		}
	}

	private void close(ResultSet rs, PreparedStatement stmt, Connection conn) {
		try {
			rs.close();
			stmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace(); // will print the exception stack trace
		}
	}

	public User findById(int id) {

		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			// get connection
			conn = DatabaseUtil.getConnection();

			// prepare and execute query
			stmt = conn.prepareStatement("select * from users where id = ?");
			stmt.setInt(1, id);
			rs = stmt.executeQuery();

			// if rs.next() is true, a user was found that matches the "where" condition
			if (rs.next()) {
				return getUser(rs);
			} else {
				return null;
			}

			// next code is boilerplate (exception handling and closing resources)

		} catch(Exception e) {
			throw new RuntimeException("Error finding user", e);
		} finally {
			close(rs, stmt, conn);
		}
	}

	private User getUser(ResultSet rs) throws SQLException
	{
		int userId = rs.getInt("id");
		String username = rs.getString("username");
		String name = rs.getString("name");
		String email = rs.getString("email");

		User user = new User();
		user.setId(userId);
		user.setUsername(username);
		user.setName(name);
		user.setEmail(email);

		return user;
	}
}
