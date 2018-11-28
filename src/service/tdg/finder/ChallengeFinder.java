package service.tdg.finder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dsrg.soenea.service.logging.SQLLogger;

import data.ConnectionManager;

public class ChallengeFinder {
	
	public static ResultSet findById(Long id) throws SQLException{
		Connection con = ConnectionManager.getConnection();
		String query = "SELECT * FROM challenge WHERE challengeid=?;";
		PreparedStatement ps = con.prepareStatement(query);
		ps.setLong(1, id);
		return SQLLogger.processQuery(ps);
	}
	
	public static ResultSet findById(String id) throws SQLException{
		Connection con = ConnectionManager.getConnection();
		String query = "SELECT * FROM challenge WHERE challengeid=?;";
		PreparedStatement ps = con.prepareStatement(query);
		ps.setString(1, id);
		return SQLLogger.processQuery(ps);
	}
	
	public static ResultSet findOpenByChallenger(long challengerid) throws SQLException{
		Connection con = ConnectionManager.getConnection();
		String query = "SELECT * FROM challenge WHERE challengerid=? AND status=0;";
		PreparedStatement ps = con.prepareStatement(query);
		ps.setLong(1, challengerid);
		return SQLLogger.processQuery(ps);
	}
	
	public static ResultSet findOpenByChallengee(long challengeeid) throws SQLException{
		Connection con = ConnectionManager.getConnection();
		String query = "SELECT * FROM challenge WHERE challengeeid=? AND status=0;";
		PreparedStatement ps = con.prepareStatement(query);
		ps.setLong(1, challengeeid);
		return SQLLogger.processQuery(ps);
	}
	
	public static ResultSet findOpenByPlayer(long playerid) throws SQLException{
		Connection con = ConnectionManager.getConnection();
		String query = "SELECT * FROM challenge WHERE (challengerid=? OR challengeeid=?) AND status=0;";
		PreparedStatement ps = con.prepareStatement(query);
		ps.setLong(1, playerid);
		ps.setLong(2, playerid);
		return SQLLogger.processQuery(ps);
	}
	
	public static ResultSet findAll() throws SQLException{
		Connection con = ConnectionManager.getConnection();
		String query = "SELECT * FROM challenge;";
		PreparedStatement ps = con.prepareStatement(query);
		return SQLLogger.processQuery(ps);
	}
	
	public static ResultSet findAllOpen() throws SQLException{
		Connection con = ConnectionManager.getConnection();
		String query = "SELECT * FROM challenge WHERE status=0;";
		PreparedStatement ps = con.prepareStatement(query);
		return SQLLogger.processQuery(ps);
	}

}
