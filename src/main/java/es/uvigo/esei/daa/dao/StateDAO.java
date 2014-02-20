package es.uvigo.esei.daa.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import es.uvigo.esei.daa.entities.Address;


public class StateDAO extends DAO {
	public Address get(int id)
	throws DAOException, IllegalArgumentException {
		try (final Connection conn = this.getConnection()) {
			final String query = "SELECT * FROM state WHERE id=?";
			
			try (PreparedStatement statement = conn.prepareStatement(query)) {
				statement.setInt(1, id);
				
				try (ResultSet result = statement.executeQuery()) {
					if (result.next()) {
						return new Address(
							result.getInt("id"),
							result.getString("street"),
							result.getInt("number"),
							result.getString("locality"),
							result.getString("province")
						);
					} else {
						throw new IllegalArgumentException("Invalid id");
					}
				}
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}
	
	public List<Address> list() throws DAOException {
		try (final Connection conn = this.getConnection()) {
			try (Statement statement = conn.createStatement()) {
				try (ResultSet result = statement.executeQuery("SELECT * FROM state")) {
					final List<Address> state = new LinkedList<>();
					
					while (result.next()) {
						state.add(new Address(
							result.getInt("id"),
							result.getString("street"),
							result.getInt("number"),
							result.getString("locality"),
							result.getString("province")
						));
					}
					
					return state;
				}
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}
	
	public void delete(int id)
	throws DAOException, IllegalArgumentException {
		try (final Connection conn = this.getConnection()) {
			final String query = "DELETE FROM state WHERE id=?";
			
			try (PreparedStatement statement = conn.prepareStatement(query)) {
				statement.setInt(1, id);
				
				if (statement.executeUpdate() != 1) {
					throw new IllegalArgumentException("Invalid id");
				}
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}
	
	public Address modify(int id, String street, Integer number, String locality, String province)
	throws DAOException, IllegalArgumentException {
		if (street == null || number == null || locality == null || province == null) {
			throw new IllegalArgumentException("Data can't be null");
		}
		
		try (final Connection conn = this.getConnection()) {
			final String query = "UPDATE state SET street=?, number=?, locality=?, province=? WHERE id=?";
			
			try (PreparedStatement statement = conn.prepareStatement(query)) {
				statement.setString(1, street);
				statement.setInt(2, number);
				statement.setString(3, locality);
				statement.setString(4, province);
				statement.setInt(5, id);
				
				if (statement.executeUpdate() == 1) {
					return new Address(id, street, number, locality, province); 
				} else {
					throw new IllegalArgumentException("data can't be null");
				}
			}
		} catch (SQLException e) {
			throw new DAOException();
		}
	}
	
	public Address add(String street, Integer number, String locality, String province)
	throws DAOException, IllegalArgumentException {
		if (street == null || number == null || locality == null || province == null) {
			throw new IllegalArgumentException("data can't be null");
		}
		
		try (final Connection conn = this.getConnection()) {
			final String query = "INSERT INTO state VALUES(null, ?, ?, ?, ?)";
			
			try (PreparedStatement statement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
				statement.setString(1, street);
				statement.setInt(2, number);
				statement.setString(3, locality);
				statement.setString(4, province);
				
				if (statement.executeUpdate() == 1) {
					try (ResultSet resultKeys = statement.getGeneratedKeys()) {
						if (resultKeys.next()) {
							return new Address(resultKeys.getInt(1), street, number, locality, province);
						} else {
							throw new SQLException("Error retrieving inserted id");
						}
					}
				} else {
					throw new SQLException("Error inserting value");
				}
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}
}
