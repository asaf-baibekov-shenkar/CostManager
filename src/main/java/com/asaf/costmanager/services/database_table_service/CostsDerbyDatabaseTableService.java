package com.asaf.costmanager.services.database_table_service;

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

public class CostsDerbyDatabaseTableService implements IDatabaseTableService<Cost> {
	
	private final IDatabaseConnectionService databaseConnectionService;
	
	public CostsDerbyDatabaseTableService(IDatabaseConnectionService databaseConnectionService) {
		this.databaseConnectionService = databaseConnectionService;
	}
	
	@Override
	public void createTable() throws CostManagerException {
		try (
			Connection connection = this.databaseConnectionService.connectIfNeeded();
			PreparedStatement ps = connection.prepareStatement("""
				CREATE TABLE costs (
					id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
					category_id INT,
					total_cost DOUBLE,
					currency VARCHAR(10),
					description VARCHAR(32768),
					date DATE,
					FOREIGN KEY (category_id) REFERENCES categories(id)
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
	
	@Override
	public void createTableIfNotExist() throws CostManagerException {
		try {
			this.createTable();
		} catch (DatabaseCostManagerException e) {
			if (e.getErrorType() != DatabaseTableErrorType.TABLE_ALREADY_EXISTS)
				throw e;
		}
	}
	
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
	
	@Override
	public void dropTableIfExist() throws CostManagerException {
		try {
			this.dropTable();
		} catch (DatabaseCostManagerException e) {
			if (e.getErrorType() != DatabaseTableErrorType.TABLE_DOES_NOT_EXIST)
				throw e;
		}
	}
	
	@Override
	public Cost insertRecord(Cost cost) throws CostManagerException {
		try (
			Connection conn = this.databaseConnectionService.connectIfNeeded();
			PreparedStatement ps = conn.prepareStatement(
				"INSERT INTO costs (category_id, total_cost, currency, description, date) VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS
			)
		) {
			ps.setInt(1, cost.getCategory().getId());
			ps.setDouble(2, cost.getTotalCost());
			ps.setString(3, cost.getCurrency());
			ps.setString(4, cost.getDescription());
			ps.setDate(5, new java.sql.Date(cost.getDate().getTime()));
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DatabaseCostManagerException(DatabaseTableErrorType.INSERT_FAILED, "Failed to insert record into costs table", e);
		} catch (ClassCastException e) {
			throw new DatabaseCostManagerException(DatabaseTableErrorType.INSERT_FAILED, "Cast exception while inserting record into costs table", e);
		}
		return null;
	}
	
	@Override
	public void updateRecord(int id, Cost cost) throws CostManagerException {
		try (
			Connection conn = this.databaseConnectionService.connectIfNeeded();
			PreparedStatement ps = conn.prepareStatement("""
				UPDATE costs
				SET category_id = ?, total_cost = ?, currency = ?, description = ?, date = ?
				WHERE id = ?
			""")
		) {
			ps.setInt(1, cost.getCategory().getId());
			ps.setDouble(2, cost.getTotalCost());
			ps.setString(3, cost.getCurrency());
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
	
	@Override
	public Cost fetchRecord(int id) throws CostManagerException {
		try (
			Connection conn = this.databaseConnectionService.connectIfNeeded();
			PreparedStatement ps = conn.prepareStatement("""
				SELECT costs.id, costs.total_cost, costs.currency, costs.description, costs.date, categories.id AS category_id, categories.name AS category_name
				FROM costs
				INNER JOIN categories ON costs.category_id = categories.id
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
				rs.getDouble("total_cost"),
				rs.getString("currency"),
				rs.getString("description"),
				rs.getDate("date")
			);
		} catch (SQLException e) {
			throw new DatabaseCostManagerException(DatabaseTableErrorType.FETCH_FAILED, "Failed to fetch cost record", e);
		}
	}
	
	@Override
	public List<Cost> fetchRecords() throws CostManagerException {
		List<Cost> records = new ArrayList<>();
		try (
			Connection conn = this.databaseConnectionService.connectIfNeeded();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("""
				SELECT costs.id, costs.total_cost, costs.currency, costs.description, costs.date, categories.id AS category_id, categories.name AS category_name
				FROM costs
				INNER JOIN categories ON costs.category_id = categories.id
			""")
		) {
			while (rs.next()) {
				records.add(new Cost(
					rs.getInt("id"),
					new Category(
						rs.getInt("category_id"),
						rs.getString("category_name")
					),
					rs.getDouble("total_cost"),
					rs.getString("currency"),
					rs.getString("description"),
					rs.getDate("date")
				));
			}
		} catch (SQLException e) {
			throw new DatabaseCostManagerException(DatabaseTableErrorType.FETCH_FAILED, "Failed to fetch costs records", e);
		}
		return records;
	}
	
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
