package com.asaf.costmanager.services.database_table_service;

import com.asaf.costmanager.models.Currency;
import com.asaf.costmanager.services.database_table_service.exceptions.DatabaseCostManagerException;
import com.asaf.costmanager.services.database_table_service.exceptions.DatabaseTableErrorType;
import com.asaf.costmanager.exceptions.CostManagerException;
import com.asaf.costmanager.models.Category;
import com.asaf.costmanager.models.Cost;
import com.asaf.costmanager.services.database_connection_service.interfaces.IDatabaseConnectionService;
import com.asaf.costmanager.services.database_table_service.interfaces.IDatabaseTableService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Service class for managing the 'costs' table in the Derby database.
 * Implements IDatabaseTableService to manage records in the 'costs' table.
 * Relies on IDatabaseConnectionService for database connections.
 * The 'costs' table references the 'categories' and 'currencies' tables.
 */
public class CostsDerbyDatabaseTableService implements IDatabaseTableService<Cost> {
	
	private final IDatabaseConnectionService databaseConnectionService;
	
	
	/**
	 * Constructor for CostsDerbyDatabaseTableService.
	 * @param databaseConnectionService an implementation of IDatabaseConnectionService to manage database connections.
	 */
	public CostsDerbyDatabaseTableService(IDatabaseConnectionService databaseConnectionService) {
		this.databaseConnectionService = databaseConnectionService;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void createTable() throws CostManagerException {
		try (
			Connection connection = this.databaseConnectionService.connectIfNeeded();
			PreparedStatement ps = connection.prepareStatement("""
				CREATE TABLE costs (
					id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) PRIMARY KEY,
					category_id INT,
					currency_id INT,
					total_cost DOUBLE,
					description VARCHAR(32672),
					date DATE,
					FOREIGN KEY (category_id) REFERENCES categories(id),
					FOREIGN KEY (currency_id) REFERENCES currencies(id)
				)
			""")
		) {
			ps.executeUpdate();
		} catch (SQLException e) {
			if ("X0Y32".equals(e.getSQLState()))
				throw new DatabaseCostManagerException(DatabaseTableErrorType.TABLE_ALREADY_EXISTS, "Table costs already exists", e);
			throw new DatabaseCostManagerException(DatabaseTableErrorType.CREATE_TABLE_FAILED, "Failed to create costs table", e);
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
			Connection connection = this.databaseConnectionService.connectIfNeeded();
			PreparedStatement ps = connection.prepareStatement("DROP TABLE costs")
		) {
			ps.executeUpdate();
		} catch (SQLException e) {
			if ("42Y55".equals(e.getSQLState()))
				throw new DatabaseCostManagerException(DatabaseTableErrorType.TABLE_DOES_NOT_EXIST, "Table costs does not exist", e);
			throw new DatabaseCostManagerException(DatabaseTableErrorType.DROP_TABLE_FAILED, "Failed to drop costs table", e);
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
	public void insertRecord(Cost cost) throws CostManagerException {
		try (
			Connection conn = this.databaseConnectionService.connectIfNeeded();
			PreparedStatement ps = conn.prepareStatement(
				"INSERT INTO costs (category_id, currency_id, total_cost, description, date) VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS
			)
		) {
			ps.setInt(1, cost.getCategory().getId());
			ps.setInt(2, cost.getCurrency().getId());
			ps.setDouble(3, cost.getTotalCost());
			ps.setString(4, cost.getDescription());
			ps.setDate(5, new java.sql.Date(cost.getDate().getTime()));
			ps.executeUpdate();
			try (ResultSet rs = ps.getGeneratedKeys()) {
				if (rs.next())
					cost.setId(rs.getInt(1));
			}
		} catch (SQLException e) {
			throw new DatabaseCostManagerException(DatabaseTableErrorType.INSERT_FAILED, "Failed to insert record into costs table", e);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateRecord(int id, Cost cost) throws CostManagerException {
		try (
			Connection conn = this.databaseConnectionService.connectIfNeeded();
			PreparedStatement ps = conn.prepareStatement("""
				UPDATE costs
				SET category_id = ?, currency_id = ?, total_cost = ?, description = ?, date = ?
				WHERE id = ?
			""")
		) {
			ps.setInt(1, cost.getCategory().getId());
			ps.setInt(2, cost.getCurrency().getId());
			ps.setDouble(3, cost.getTotalCost());
			ps.setString(4, cost.getDescription());
			ps.setDate(5, new java.sql.Date(cost.getDate().getTime()));
			ps.setInt(6, id);
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DatabaseCostManagerException(DatabaseTableErrorType.UPDATE_FAILED, "Failed to update record in costs table", e);
		} catch (ClassCastException e) {
			throw new DatabaseCostManagerException(DatabaseTableErrorType.UPDATE_FAILED, "Cast exception while updating record in costs table", e);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Cost fetchRecord(int id) throws CostManagerException {
		try (
			Connection conn = this.databaseConnectionService.connectIfNeeded();
			PreparedStatement ps = conn.prepareStatement("""
				SELECT
					costs.id,
					costs.total_cost,
					costs.description,
					costs.date,
					categories.id AS category_id,
					categories.name AS category_name
					currencies.id AS currency_id,
					currencies.name AS currency_name,
					currencies.symbol AS currency_symbol
				FROM costs
				INNER JOIN categories ON costs.category_id = categories.id
				INNER JOIN currencies ON costs.currency_id = currencies.id
				WHERE costs.id = ?
			""")
		) {
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (!rs.next()) return null;
			return new Cost(
				rs.getInt("id"),
				new Category(
					rs.getInt("category_id"),
					rs.getString("category_name")
				),
				new Currency(
					rs.getInt("currency_id"),
					rs.getString("currency_name"),
					rs.getString("currency_symbol")
				),
				rs.getDouble("total_cost"),
				rs.getString("description"),
				rs.getDate("date")
			);
		} catch (SQLException e) {
			throw new DatabaseCostManagerException(DatabaseTableErrorType.FETCH_FAILED, "Failed to fetch cost record", e);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Cost> fetchRecords() throws CostManagerException {
		List<Cost> records = new ArrayList<>();
		try (
			Connection conn = this.databaseConnectionService.connectIfNeeded();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("""
				SELECT
					costs.id,
					costs.total_cost,
					costs.description,
					costs.date,
					categories.id AS category_id,
					categories.name AS category_name,
					currencies.id AS currency_id,
					currencies.name AS currency_name,
					currencies.symbol AS currency_symbol
				FROM costs
				INNER JOIN categories ON costs.category_id = categories.id
				INNER JOIN currencies ON costs.currency_id = currencies.id
			""")
		) {
			while (rs.next()) {
				records.add(new Cost(
					rs.getInt("id"),
					new Category(
						rs.getInt("category_id"),
						rs.getString("category_name")
					),
					new Currency(
						rs.getInt("currency_id"),
						rs.getString("currency_name"),
						rs.getString("currency_symbol")
					),
					rs.getDouble("total_cost"),
					rs.getString("description"),
					rs.getDate("date")
				));
			}
		} catch (SQLException e) {
			throw new DatabaseCostManagerException(DatabaseTableErrorType.FETCH_FAILED, "Failed to fetch costs records", e);
		}
		return records;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Cost deleteRecord(int id) throws CostManagerException {
		Cost cost = this.fetchRecord(id);
		if (cost == null) return null;
		try (
			Connection conn = this.databaseConnectionService.connectIfNeeded();
			PreparedStatement ps = conn.prepareStatement(
				"DELETE FROM costs WHERE id = ?"
			)
		) {
			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DatabaseCostManagerException(DatabaseTableErrorType.DELETE_FAILED, "Failed to delete record from costs table", e);
		}
		return cost;
	}
}
