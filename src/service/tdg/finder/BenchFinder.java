package service.tdg.finder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.dsrg.soenea.service.logging.SQLLogger;

import data.ConnectionManager;

public class BenchFinder {
	
	public static ResultSet getCardsInBench(long gameId, long playerId) throws SQLException{
		Connection con = ConnectionManager.getConnection();
		String query = "SELECT cardid FROM bench WHERE gameid=? AND playerid=?;";
		PreparedStatement ps = con.prepareStatement(query);
		ps.setLong(1, gameId);
		ps.setLong(2, playerId);
		return SQLLogger.processQuery(ps);
	}
	
	public static ResultSet getCardsInBench(String gameId, String playerId) throws SQLException{
		Connection con = ConnectionManager.getConnection();
		String query = "SELECT cardid FROM bench WHERE gameid=? AND playerid=?;";
		PreparedStatement ps = con.prepareStatement(query);
		ps.setString(1, gameId);
		ps.setString(2, playerId);
		return SQLLogger.processQuery(ps);
	}

}
