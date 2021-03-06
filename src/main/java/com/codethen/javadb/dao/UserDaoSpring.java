package com.codethen.javadb.dao;

import com.codethen.javadb.model.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.util.List;

public class UserDaoSpring implements UserDao {

	private JdbcTemplate template;


	public UserDaoSpring(DataSource dataSource) {

		template = new JdbcTemplate(dataSource);
	}


	@Override
	public List<User> findAll() {

		return null; // TODO
	}

	@Override
	public User findById(int id) {

		String sql = "select * from users where id = ?";

		Object[] args = { id };

		return template.queryForObject(sql, args, rowMapper);
	}

	@Override
	public void create(User user) {

		template.update(
			"insert into users (username, name, email) values (?, ?, ?)",
			user.getUsername(), user.getName(), user.getEmail());
	}

	@Override
	public void update(User user) {

		// TODO
	}

	@Override
	public void delete(int id) {

		template.update("delete from users where id = ?", id);
	}

	private RowMapper<User> rowMapper = new BeanPropertyRowMapper<>(User.class);
}
