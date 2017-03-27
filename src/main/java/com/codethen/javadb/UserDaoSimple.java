package com.codethen.javadb;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

/**
 * This DAO is much simpler than {@link UserDaoComplex} because it relies on {@link GenericDao}.
 */
public class UserDaoSimple extends GenericDao<User> implements UserDao {

	public UserDaoSimple() {
		super("users", User.class);
	}


	// TODO: try to move the following methods to GenericDao:

	@Override
	protected List<String> getColumnNames() {
		return Arrays.asList("username", "name", "email");
	}

	@Override
	protected void setValues(User user, PreparedStatement stmt, boolean needsId) throws SQLException {

		stmt.setString(1, user.getUsername());
		stmt.setString(2, user.getName());
		stmt.setString(3, user.getEmail());

		if (needsId) {
			stmt.setInt(4, user.getId());
		}
	}
}
