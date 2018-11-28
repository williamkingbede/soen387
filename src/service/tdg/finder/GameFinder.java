package service.tdg.finder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.dsrg.soenea.service.logging.SQLLogger;

import data.ConnectionManager;

public class GameFinder {
	
	public static ResultSet findById(Long id) throws SQLException{
		Connection con = ConnectionManager.getConnection();
		String query = "SELECT * FROM game WHERE gameid=?;";
		PreparedStatement ps = con.prepareStatement(query);
		ps.setLong(1, id);
		return SQLLogger.processQuery(ps);
	}
	
	public static ResultSet findById(String id) throws SQLException{
		Connection con = ConnectionManager.getConnection();
		String query = "SELECT * FROM game WHERE gameid=?;";
		PreparedStatement ps = con.prepareStatement(query);
		ps.setString(1, id);
		return SQLLogger.processQuery(ps);
	}
	
	public static ResultSet findByPlayer(long playerid) throws SQLException{
		Connection con = ConnectionManager.getConnection();
		String query = "SELECT * FROM game WHERE (challengerid=? OR challengeeid=?)";
		PreparedStatement ps = con.prepareStatement(query);
		ps.setLong(1, playerid);
		ps.setLong(2, playerid);
		return SQLLogger.processQuery(ps);
	}
	
	public static ResultSet findAll() throws SQLException{
		Connection con = ConnectionManager.getConnection();
		String query = "SELECT * FROM game;";
		PreparedStatement ps = con.prepareStatement(query);
		ResultSet rs = ps.executeQuery();
		return SQLLogger.processQuery(ps);
	}

}
