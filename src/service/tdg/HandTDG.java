package service.tdg;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.dsrg.soenea.service.logging.SQLLogger;

import data.ConnectionManager;

public class HandTDG {

	public static int insert(long gameId, long playerId, long version) throws SQLException{
		Connection con = ConnectionManager.getConnection();
		String query = "INSERT INTO hand (gameid,playerid,version) VALUES (?,?,?);";
		PreparedStatement ps = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
		ps.setLong(1, gameId);
		ps.setLong(2, playerId);
		ps.setLong(3, version);
		int count = SQLLogger.processUpdate(ps);
		ps.close();
		con.close();
		return count;
	}
	
	public static int insert(long gameId, long playerId, long cardid, long version) throws SQLException{
		Connection con = ConnectionManager.getConnection();
		String query = "INSERT INTO hand (gameid,playerid,cardid,version) VALUES (?,?,?,?);";
		PreparedStatement ps = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
		ps.setLong(1, gameId);
		ps.setLong(2, playerId);
		ps.setLong(3, cardid);
		ps.setLong(4, version);
		int count = SQLLogger.processUpdate(ps);
		ps.close();
		con.close();
		return count;
	}
	
	public static int update(long gameId, long playerId, long cardid, long version) throws SQLException{
		Connection con = ConnectionManager.getConnection();
		String query = "UPDATE hand SET version=version+1, cardid = ? where gameid = ? AND playerid = ? AND version=?;";
		PreparedStatement ps = con.prepareStatement(query);
		ps.setLong(1, cardid);
		ps.setLong(2, gameId);
		ps.setLong(3, playerId);
		ps.setLong(4, version);
		int count = SQLLogger.processUpdate(ps);
		ps.close();
		con.close();
		return count;
	}
	
	public static int update(long gameId, long playerId, long version) throws SQLException{
		Connection con = ConnectionManager.getConnection();
		String query = "UPDATE hand SET version=version+1 where gameid = ? AND playerid = ? AND version=?;";
		PreparedStatement ps = con.prepareStatement(query);
		ps.setLong(1, gameId);
		ps.setLong(2, playerId);
		ps.setLong(3, version);
		int count = SQLLogger.processUpdate(ps);
		ps.close();
		con.close();
		return count;
	}
	
	public static int delete(long gameId, long playerId, long version) throws SQLException{
		Connection con = ConnectionManager.getConnection();
		String query = "DELETE FROM hand WHERE gameid=? AND playerid= ? AND version = ?;";
		PreparedStatement ps = con.prepareStatement(query);
		ps.setLong(1, gameId);
		ps.setLong(2, playerId);
		ps.setLong(3, version);
		int count = SQLLogger.processUpdate(ps);
		ps.close();
		con.close();
		return count;
	}
	
}
