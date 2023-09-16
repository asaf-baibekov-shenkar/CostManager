package com.asaf.costmanager.services.database_table_service;

import com.asaf.costmanager.exceptions.CostManagerException;
import com.asaf.costmanager.models.Currency;
import com.asaf.costmanager.services.database_connection_service.interfaces.IDatabaseConnectionService;
import com.asaf.costmanager.services.database_table_service.exceptions.DatabaseCostManagerException;
import com.asaf.costmanager.services.database_table_service.exceptions.DatabaseTableErrorType;
import com.asaf.costmanager.services.database_table_service.interfaces.IDatabaseTableService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CurrenciesDerbyDatabaseTableService implements IDatabaseTableService<Currency> {
	
	private final IDatabaseConnectionService databaseConnectionService;
	
	public CurrenciesDerbyDatabaseTableService(IDatabaseConnectionService databaseConnectionService) {
		this.databaseConnectionService = databaseConnectionService;
	}
	
	@Override
	public void createTable() throws CostManagerException {
		try (
			Connection conn = this.databaseConnectionService.connectIfNeeded();
			PreparedStatement ps = conn.prepareStatement("""
				CREATE TABLE currencies (
					id INT GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) PRIMARY KEY,
					name VARCHAR(255),
					symbol VARCHAR(255)
				)
			""")
		) {
			ps.executeUpdate();
		} catch (SQLException e) {
			if ("X0Y32".equals(e.getSQLState()))
				throw new DatabaseCostManagerException(DatabaseTableErrorType.TABLE_ALREADY_EXISTS, "Table currencies already exists", e);
			throw new DatabaseCostManagerException(DatabaseTableErrorType.CREATE_TABLE_FAILED, "Failed to create currencies table", e);
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
			Connection conn = this.databaseConnectionService.connectIfNeeded();
			PreparedStatement ps = conn.prepareStatement(
				"DROP TABLE currencies"
			)
		) {
			ps.executeUpdate();
		} catch (SQLException e) {
			if ("42Y55".equals(e.getSQLState()))
				throw new DatabaseCostManagerException(DatabaseTableErrorType.TABLE_DOES_NOT_EXIST, "Table currencies does not exist", e);
			throw new DatabaseCostManagerException(DatabaseTableErrorType.DROP_TABLE_FAILED, "Failed to drop currencies table", e);
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
	public void insertRecord(Currency currency) throws CostManagerException {
		try (
			Connection conn = this.databaseConnectionService.connectIfNeeded();
			PreparedStatement ps = conn.prepareStatement(
				"INSERT INTO currencies (name, symbol) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS
			)
		) {
			ps.setString(1, currency.getName());
			ps.setString(2, currency.getSymbol());
			ps.executeUpdate();
			try (ResultSet rs = ps.getGeneratedKeys()) {
				if (rs.next())
					currency.setId(rs.getInt(1));
			}
		} catch (SQLException e) {
			throw new DatabaseCostManagerException(DatabaseTableErrorType.INSERT_FAILED, "Failed to insert record into currencies table", e);
		} catch (ClassCastException e) {
			throw new DatabaseCostManagerException(DatabaseTableErrorType.INSERT_FAILED, "Cast exception while inserting record into currencies table", e);
		}
	}
	
	@Override
	public void updateRecord(int id, Currency currency) throws CostManagerException {
		try (
			Connection conn = this.databaseConnectionService.connectIfNeeded();
			PreparedStatement ps = conn.prepareStatement(
				"UPDATE currencies SET name = ?, symbol = ? WHERE id = ?"
			)
		) {
			ps.setString(1, currency.getName());
			ps.setString(2, currency.getSymbol());
			ps.setInt(4, id);
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DatabaseCostManagerException(DatabaseTableErrorType.UPDATE_FAILED, "Failed to update record in currencies table", e);
		} catch (ClassCastException e) {
			throw new DatabaseCostManagerException(DatabaseTableErrorType.UPDATE_FAILED, "Cast exception while updating record in currencies table", e);
		}
	}
	
	@Override
	public Currency fetchRecord(int id) throws CostManagerException {
		try (
			Connection conn = this.databaseConnectionService.connectIfNeeded();
			PreparedStatement ps = conn.prepareStatement("""
				SELECT * FROM currencies WHERE id = ?
			""")
		) {
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (!rs.next()) return null;
			return new Currency(
				rs.getInt("id"),
				rs.getString("name"),
				rs.getString("symbol")
			);
		} catch (SQLException e) {
			throw new DatabaseCostManagerException(DatabaseTableErrorType.FETCH_FAILED, "Failed to fetch cost record", e);
		}
	}
	
	@Override
	public List<Currency> fetchRecords() throws CostManagerException {
		List<Currency> records = new ArrayList<>();
		try (
			Connection conn = this.databaseConnectionService.connectIfNeeded();
			PreparedStatement ps = conn.prepareStatement("""
				SELECT * FROM currencies
			""")
		) {
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String symbol = rs.getString("symbol");
				
				records.add(new Currency(
					id,
					name,
					symbol
				));
			}
		} catch (SQLException e) {
			throw new DatabaseCostManagerException(DatabaseTableErrorType.FETCH_FAILED, "Failed to fetch currencies records", e);
		}
		return records;
	}
	
	@Override
	public Currency deleteRecord(int id) throws CostManagerException {
		Currency cost = this.fetchRecord(id);
		if (cost == null) return null;
		try (
			Connection conn = this.databaseConnectionService.connectIfNeeded();
			PreparedStatement ps = conn.prepareStatement(
				"DELETE FROM currencies WHERE id = ?"
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
