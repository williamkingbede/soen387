package service.tdg.finder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.dsrg.soenea.service.logging.SQLLogger;

import data.ConnectionManager;

public class DeckFinder {

	public static ResultSet findById(Long id) throws SQLException{
		Connection con = ConnectionManager.getConnection();
		String query = "SELECT * FROM deck WHERE deckid=?;";
		PreparedStatement ps = con.prepareStatement(query);
		ps.setLong(1, id);
		return SQLLogger.processQuery(ps);
	}
	
	public static ResultSet findById(String id) throws SQLException{
		Connection con = ConnectionManager.getConnection();
		String query = "SELECT * FROM deck WHERE deckid=?;";
		PreparedStatement ps = con.prepareStatement(query);
		ps.setString(1, id);
		return SQLLogger.processQuery(ps);
	}
	
	public static ResultSet findByPlayer(long playerid) throws SQLException{
		Connection con = ConnectionManager.getConnection();
		String query = "SELECT * FROM deck WHERE playerid=?;";
		PreparedStatement ps = con.prepareStatement(query);
		ps.setLong(1, playerid);
		return SQLLogger.processQuery(ps);
	}
	
}
