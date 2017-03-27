package com.codethen.javadb;


import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAOs (Data Access Objects) are responsible of talking with the database to read/write objects
 */
public abstract class GenericDao<T> {

	private String tableName;
	private Class<T> type;


	public GenericDao(String tableName, Class<T> type) {
		this.tableName = tableName;
		this.type = type;
	}


	public List<T> findAll() {

		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			// get connection
			conn = DatabaseUtil.getConnection();

			// prepare and execute query
			stmt = conn.prepareStatement("select * from " + tableName);
			rs = stmt.executeQuery();

			List<T> objects = new ArrayList<T>();

			while (rs.next()) {
				T object = getObject(rs);
				objects.add(object);
			}

			return objects;

		} catch (Exception e) {
			throw new RuntimeException("Error finding object", e);
		} finally {
			DatabaseUtil.close(rs, stmt, conn);
		}
	}

	public T findById(int id) {

		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			// get connection
			conn = DatabaseUtil.getConnection();

			// prepare and execute query
			stmt = conn.prepareStatement("select * from " + tableName + " where id = ?");
			stmt.setInt(1, id);
			rs = stmt.executeQuery();

			if (rs.next()) {
				return getObject(rs);
			} else {
				return null;
			}

			// next code is boilerplate (exception handling and closing resources)

		} catch (Exception e) {
			throw new RuntimeException("Error finding object", e);
		} finally {
			DatabaseUtil.close(rs, stmt, conn);
		}
	}

	public void create(T object) {

		Connection conn = null;
		PreparedStatement stmt = null;

		try {

			conn = DatabaseUtil.getConnection();

			List<String> columnNames = getColumnNames();

			String columnNamesStr = StringUtils.join(getColumnNames(), ", ");
			String questionMarks = StringUtils.repeat("?", ", ", columnNames.size());

			String sqlStatement = "insert into " + tableName + "(" + columnNamesStr + ") values (" + questionMarks + ")";

			stmt = conn.prepareStatement(sqlStatement);

			setValues(object, stmt, false);

			stmt.executeUpdate();

		} catch(Exception e) {
			throw new RuntimeException("error creating object", e);
		} finally {
			DatabaseUtil.close(null, stmt, conn);
		}
	}

	public void update(T object) {

		Connection conn = null;
		PreparedStatement stmt = null;

		try {

			conn = DatabaseUtil.getConnection();

			// TODO: make this generic, so it works with any model class, not only User
			stmt = conn.prepareStatement("update " + tableName + " set username = ?, name = ?, email = ? where id = ?");

			setValues(object, stmt, true);

			stmt.executeUpdate();

		} catch (Exception e) {
			throw new RuntimeException("error updating object", e);
		} finally {
			DatabaseUtil.close(null, stmt, conn);
		}
	}

	public void delete(int id) {

		Connection conn = null;
		PreparedStatement stmt = null;

		try {

			conn = DatabaseUtil.getConnection();

			stmt = conn.prepareStatement("delete from " + tableName + " where id = ?");
			stmt.setInt(1, id);

			stmt.executeUpdate();

		} catch (Exception e) {
			throw new RuntimeException("error deleting object", e);
		} finally {
			DatabaseUtil.close(null, stmt, conn);
		}
	}



	/** Retrieve data from the ResultSet (same columns as {@link #getColumnNames()}) and create object */
	protected T getObject(ResultSet rs) throws SQLException {

		try {
			// create instance of the model class
			T object = type.newInstance();

			// sets values from rs to the instance
			Field[] fields = type.getDeclaredFields();
			for (Field field : fields) {
				field.setAccessible(true);
				Object value = rs.getObject(field.getName());
				setValue(object, field, value);
			}

			// return instance
			return object;

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void setValue(T object, Field field, Object value) throws Exception
	{
		String methodName = "set" + StringUtils.capitalize( field.getName() );
		Method setter = type.getMethod(methodName, field.getType());
		setter.invoke(object, value);
	}

	/** Returns the column names, in the same order you set them in {@link #setValues(Object, PreparedStatement, boolean)} */
	protected abstract List<String> getColumnNames();

	/**
	 * Set the object values into the statement, in the same order of {@link #getColumnNames()}.
	 *
	 * @param needsId  if true, set the id into the statement, at the index after the other columns
	 */
	protected abstract void setValues(T object, PreparedStatement stmt, boolean needsId) throws SQLException;
}
