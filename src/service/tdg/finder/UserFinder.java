package service.tdg.finder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.dsrg.soenea.service.logging.SQLLogger;

import data.ConnectionManager;

public class UserFinder {

	public static ResultSet findById(Long id) throws SQLException{
		Connection con = ConnectionManager.getConnection();
		String query = "SELECT * FROM users WHERE userid=?;";
		PreparedStatement ps = con.prepareStatement(query);
		ps.setLong(1, id);
		return SQLLogger.processQuery(ps);
	}
	
	public static ResultSet findById(String id) throws SQLException{
		Connection con = ConnectionManager.getConnection();
		String query = "SELECT * FROM users WHERE userid=?;";
		PreparedStatement ps = con.prepareStatement(query);
		ps.setString(1, id);
		return SQLLogger.processQuery(ps);
	}
	
	
	public static ResultSet findByUsername(String username) throws SQLException{
		Connection con = ConnectionManager.getConnection();
		String query = "SELECT * FROM users WHERE username=?;";
		PreparedStatement ps = con.prepareStatement(query);
		ps.setString(1, username);
		return SQLLogger.processQuery(ps);
	}
	
	
	public static ResultSet findByUsernameAndPassword(String username, String password) throws SQLException{
		Connection con = ConnectionManager.getConnection();
		String query = "SELECT * FROM users WHERE username=? AND pass=?;";
		PreparedStatement ps = con.prepareStatement(query);
		ps.setString(1, username);
		ps.setString(2, password);
		return SQLLogger.processQuery(ps);
	}
	
	public static ResultSet findAll() throws SQLException{
		Connection con = ConnectionManager.getConnection();
		String query = "SELECT * FROM users;";
		PreparedStatement ps = con.prepareStatement(query);
		return SQLLogger.processQuery(ps);
	}
	
}
