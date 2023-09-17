package com.asaf.costmanager.services.database_table_service;

import com.asaf.costmanager.services.database_table_service.exceptions.DatabaseCostManagerException;
import com.asaf.costmanager.services.database_table_service.exceptions.DatabaseTableErrorType;
import com.asaf.costmanager.exceptions.CostManagerException;
import com.asaf.costmanager.models.Category;
import com.asaf.costmanager.services.database_connection_service.interfaces.IDatabaseConnectionService;
import com.asaf.costmanager.services.database_table_service.interfaces.IDatabaseTableService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Implementation of the IDatabaseTableService interface for Category objects.
 * This class manages the connection to a local database, specifically for the 'categories' table.
 * It provides methods to create, drop, insert, update, fetch and delete records in the 'categories' table.
 * The class leverages the database connection service to connect to the database as needed.
 */
public class CategoriesDerbyDatabaseTableService implements IDatabaseTableService<Category> {
	
	private final IDatabaseConnectionService databaseConnectionService;
	
	/**
	 * Constructor for CategoriesDerbyDatabaseTableService.
	 * @param databaseConnectionService an implementation of IDatabaseConnectionService to manage database connections.
	 */
	public CategoriesDerbyDatabaseTableService(IDatabaseConnectionService databaseConnectionService) {
		this.databaseConnectionService = databaseConnectionService;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void createTable() throws CostManagerException {
		try (
			Connection conn = this.databaseConnectionService.connectIfNeeded();
			PreparedStatement ps = conn.prepareStatement("""
				CREATE TABLE categories (
					id INT GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) PRIMARY KEY,
					name VARCHAR(255)
				)
			""")
		) {
			ps.executeUpdate();
		} catch (SQLException e) {
			if ("X0Y32".equals(e.getSQLState()))
				throw new DatabaseCostManagerException(DatabaseTableErrorType.TABLE_ALREADY_EXISTS, "Table categories already exists", e);
			throw new DatabaseCostManagerException(DatabaseTableErrorType.CREATE_TABLE_FAILED, "Failed to create categories table", e);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void createTableIfNotExist() throws CostManagerException {
		try {
			this.createTable();
		} catch (DatabaseCostManagerException e) {
			if (e.getErrorType() != DatabaseTableErrorType.TABLE_ALREADY_EXISTS)
				throw e;
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void dropTable() throws CostManagerException {
		try (
			Connection conn = this.databaseConnectionService.connectIfNeeded();
			PreparedStatement ps = conn.prepareStatement(
				"DROP TABLE categories"
			)
		) {
			ps.executeUpdate();
		} catch (SQLException e) {
			if ("42Y55".equals(e.getSQLState()))
				throw new DatabaseCostManagerException(DatabaseTableErrorType.TABLE_DOES_NOT_EXIST, "Table categories does not exist", e);
			throw new DatabaseCostManagerException(DatabaseTableErrorType.DROP_TABLE_FAILED, "Failed to drop categories table", e);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void dropTableIfExist() throws CostManagerException {
		try {
			this.dropTable();
		} catch (DatabaseCostManagerException e) {
			if (e.getErrorType() != DatabaseTableErrorType.TABLE_DOES_NOT_EXIST)
				throw e;
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void insertRecord(Category category) throws CostManagerException {
		try (
			Connection conn = this.databaseConnectionService.connectIfNeeded();
			PreparedStatement ps = conn.prepareStatement(
				"INSERT INTO categories (name) VALUES (?)", Statement.RETURN_GENERATED_KEYS
			)
		) {
			ps.setString(1, category.getName());
			ps.executeUpdate();
			try (ResultSet rs = ps.getGeneratedKeys()) {
				if (rs.next())
					category.setId(rs.getInt(1));
			}
		} catch (SQLException e) {
			throw new DatabaseCostManagerException(DatabaseTableErrorType.INSERT_FAILED, "Failed to insert record into categories table", e);
		} catch (ClassCastException e) {
			throw new DatabaseCostManagerException(DatabaseTableErrorType.INSERT_FAILED, "Cast exception while inserting record into categories table", e);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateRecord(int id, Category category) throws CostManagerException {
		try (
			Connection conn = this.databaseConnectionService.connectIfNeeded();
			PreparedStatement ps = conn.prepareStatement(
				"UPDATE categories SET name = ? WHERE id = ?"
			)
		) {
			ps.setString(1, category.getName());
			ps.setInt(2, id);
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DatabaseCostManagerException(DatabaseTableErrorType.UPDATE_FAILED, "Failed to update record in categories table", e);
		} catch (ClassCastException e) {
			throw new DatabaseCostManagerException(DatabaseTableErrorType.UPDATE_FAILED, "Cast exception while updating record in categories table", e);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Category fetchRecord(int id) throws CostManagerException {
		try (
			Connection conn = this.databaseConnectionService.connectIfNeeded();
			PreparedStatement ps = conn.prepareStatement(
				"SELECT * FROM categories WHERE id = ?"
			)
		) {
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (!rs.next()) return null;
			return new Category(
				rs.getInt("id"),
				rs.getString("name")
			);
		} catch (SQLException e) {
			throw new DatabaseCostManagerException(DatabaseTableErrorType.FETCH_FAILED, "Failed to fetch record from categories table", e);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Category> fetchRecords() throws CostManagerException {
		List<Category> records = new ArrayList<>();
		try (
			Connection conn = this.databaseConnectionService.connectIfNeeded();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM categories")
		) {
			while (rs.next()) {
				records.add(new Category(
					rs.getInt("id"),
					rs.getString("name")
				));
			}
		} catch (SQLException e) {
			throw new DatabaseCostManagerException(DatabaseTableErrorType.FETCH_FAILED, "Failed to fetch categories records", e);
		}
		return records;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Category deleteRecord(int id) throws CostManagerException {
		Category category = this.fetchRecord(id);
		if (category == null) return null;
		try (
			Connection conn = this.databaseConnectionService.connectIfNeeded();
			PreparedStatement ps = conn.prepareStatement(
				"DELETE FROM categories WHERE id = ?"
			)
		) {
			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DatabaseCostManagerException(DatabaseTableErrorType.DELETE_FAILED, "Failed to delete record from categories table", e);
		}
		return category;
	}
}
