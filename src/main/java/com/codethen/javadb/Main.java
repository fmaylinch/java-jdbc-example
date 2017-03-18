package com.codethen.javadb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Main {

	public static void main(String[] args) throws Exception {

		// Sometimes this sentence is needed to load the driver properly
		Class.forName("com.mysql.jdbc.Driver").newInstance();

		// Necessary data to connect to a schema of the database server
		String host = "localhost";
		String schema = "test";
		String user = "root";
		String pwd = "maysicuel";

		// Obtain a connection
		Connection conn = DriverManager.getConnection(
			"jdbc:mysql://" + host + "/" + schema + "?user=" + user + "&password=" + pwd);


		// Let's find a user in the users table
		String userToFind = "may";

		// danger of sql-injection attack
		//Statement stmt = conn.createStatement();
		//ResultSet rs = stmt.executeQuery("select * from users where username = '" + userToFind + "'");

		// prepared statements can't be attacked with the sql-injection
		PreparedStatement stmt = conn.prepareStatement("select * from users where username = ?");
		stmt.setString(1, userToFind);
		ResultSet rs = stmt.executeQuery();

		// Iterate the results
		while (rs.next()) {

			// For each row, get the user data of the columns we need
			int id = rs.getInt("id");
			String username = rs.getString("username");
			String name = rs.getString("name");
			String email = rs.getString("email");

			// In this example we just print the data
			System.out.println(id + ", " + username + ", " + name + ", " + email);
		}

		// Close the resources at the end
		rs.close();
		stmt.close();
		conn.close();
	}
}
