package data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StatusRDG {

	private long gameId;
	private long playerId;
	private int status;
	
	public StatusRDG(long gameId, long playerId, int status) {
		this.gameId = gameId;
		this.playerId = playerId;
		this.status = status;
	}

	public long getGameId() {
		return gameId;
	}
	
	public void setGameId(long gameId) {
		this.gameId = gameId;
	}
	
	public long getPlayerId() {
		return playerId;
	}
	
	public void setPlayerId(long playerId) {
		this.playerId = playerId;
	}
	
	public int getStatus() {
		return status;
	}
	
	public void setStatus(int status) {
		this.status = status;
	}
	
	public void insert() throws SQLException{
		Connection con = ConnectionManager.getConnection();
		String query = "INSERT INTO status (gameid,playerid,status) VALUES (?,?,?);";
		PreparedStatement ps = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
		ps.setLong(1, this.gameId);
		ps.setLong(2, this.playerId);
		ps.setInt(3, this.status);
		ps.executeUpdate();
		ps.close();
		con.close();
	}
	
	public void update() throws SQLException{
		Connection con = ConnectionManager.getConnection();
		String query = "UPDATE status SET status = ? where gameid = ? AND playerid = ?;";
		PreparedStatement ps = con.prepareStatement(query);
		ps.setInt(1, this.status);
		ps.setLong(2, this.gameId);
		ps.setLong(3, this.playerId);
		ps.executeUpdate();
		ps.close();
		con.close();
	}
	
	public void delete() throws SQLException{
		Connection con = ConnectionManager.getConnection();
		String query = "DELETE FROM status WHERE gameid=? AND playerid= ?;";
		PreparedStatement ps = con.prepareStatement(query);
		ps.setLong(1, this.gameId);
		ps.setLong(2, this.playerId);
		ps.executeUpdate();
		ps.close();
		con.close();
	}
	
	public static StatusRDG find(long gameId, long playerId) throws SQLException{
		Connection con = ConnectionManager.getConnection();
		String query = "SELECT * FROM status WHERE gameid=? AND playerid=?;";
		PreparedStatement ps = con.prepareStatement(query);
		ps.setLong(1, gameId);
		ps.setLong(2, playerId);
		ResultSet rs = ps.executeQuery();
		StatusRDG h = null;
		if(rs.next()) {
			h = new StatusRDG(rs.getLong("gameid"), rs.getLong("playerid"), rs.getInt("status"));
		} else {
			return null;
		}
		
		rs.close();
		ps.close();
		con.close();
		
		return h;
	}
	
	public static StatusRDG find(String gameId, String playerId) throws SQLException{
		Connection con = ConnectionManager.getConnection();
		String query = "SELECT * FROM status WHERE gameid=? AND playerid=?;";
		PreparedStatement ps = con.prepareStatement(query);
		ps.setString(1, gameId);
		ps.setString(2, playerId);
		ResultSet rs = ps.executeQuery();
		StatusRDG h = null;
		if(rs.next()) {
			h = new StatusRDG(rs.getLong("gameid"), rs.getLong("playerid"), rs.getInt("status"));
		} else {
			return null;
		}
		
		rs.close();
		ps.close();
		con.close();
		
		return h;
	}
}
