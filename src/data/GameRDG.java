package data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GameRDG {
	
	private long id;
	private long challengerId;
	private long challengeeId;
	
	public GameRDG(long id, long challengerId, long challengeeId) {
		this.id = id;
		this.challengerId = challengerId;
		this.challengeeId = challengeeId;
	}
	
	public GameRDG(long challengerId, long challengeeId) {
		this.challengerId = challengerId;
		this.challengeeId = challengeeId;
	}

	public long getId() {
		return id;
	}

	public long getChallengerId() {
		return challengerId;
	}

	public void setChallengerId(long challengerId) {
		this.challengerId = challengerId;
	}

	public long getChallengeeId() {
		return challengeeId;
	}

	public void setChallengeeId(long challengeeId) {
		this.challengeeId = challengeeId;
	}
	
	public long insert() throws SQLException{
		Connection con = ConnectionManager.getConnection();
		String query = "INSERT INTO game (challengerid,challengeeid) VALUES (?,?);";
		PreparedStatement ps = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
		ps.setLong(1, this.challengerId);
		ps.setLong(2, this.challengeeId);
		long id;
		int affectedRows = ps.executeUpdate();

        if (affectedRows == 0) {
            throw new SQLException("Creating game failed, no rows affected.");
        }

        try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                id = generatedKeys.getLong(1);
            }
            else {
                throw new SQLException("Creating game failed, no ID obtained.");
            }
        }
		ps.close();
		con.close();
		return id;
	}
	
	public void update() throws SQLException{
		Connection con = ConnectionManager.getConnection();
		String query = "UPDATE game SET challengerid = ?, challengeeid = ? where gameid = ?;";
		PreparedStatement ps = con.prepareStatement(query);
		ps.setLong(1, this.challengerId);
		ps.setLong(2, this.challengeeId);
		ps.setLong(3, this.id);
		ps.executeUpdate();
		ps.close();
		con.close();
	}
	
	public void delete() throws SQLException{
		Connection con = ConnectionManager.getConnection();
		String query = "DELETE FROM game WHERE gameid=?;";
		PreparedStatement ps = con.prepareStatement(query);
		ps.setLong(1, this.id);
		ps.executeUpdate();
		ps.close();
		con.close();
	}
	
	public static GameRDG findById(Long id) throws SQLException{
		Connection con = ConnectionManager.getConnection();
		String query = "SELECT * FROM game WHERE gameid=?;";
		PreparedStatement ps = con.prepareStatement(query);
		ps.setLong(1, id);
		ResultSet rs = ps.executeQuery();
		GameRDG c = null;
		if(rs.next()) {
			c = new GameRDG(rs.getLong("gameid"), rs.getLong("challengerid"), rs.getLong("challengeeid"));
		} else {
			return null;
		}
		
		rs.close();
		ps.close();
		con.close();
		
		return c;
	}
	
	public static GameRDG findById(String id) throws SQLException{
		Connection con = ConnectionManager.getConnection();
		String query = "SELECT * FROM game WHERE gameid=?;";
		PreparedStatement ps = con.prepareStatement(query);
		ps.setString(1, id);
		ResultSet rs = ps.executeQuery();
		GameRDG c = null;
		if(rs.next()) {
			c = new GameRDG(rs.getLong("gameid"), rs.getLong("challengerid"), rs.getLong("challengeeid"));
		} else {
			return null;
		}
		
		rs.close();
		ps.close();
		con.close();
		
		return c;
	}
	
	public static List<GameRDG> findByPlayer(long playerid) throws SQLException{
		Connection con = ConnectionManager.getConnection();
		String query = "SELECT * FROM game WHERE (challengerid=? OR challengeeid=?)";
		PreparedStatement ps = con.prepareStatement(query);
		ps.setLong(1, playerid);
		ps.setLong(2, playerid);
		ResultSet rs = ps.executeQuery();
		List<GameRDG> games = new ArrayList<GameRDG>();
		while(rs.next()) {
			games.add(new GameRDG(rs.getLong("gameid"), rs.getLong("challengerid"), rs.getLong("challengeeid")));
		}
		
		rs.close();
		ps.close();
		con.close();
		
		return games;
	}
	
	public static List<GameRDG> findAll() throws SQLException{
		Connection con = ConnectionManager.getConnection();
		String query = "SELECT * FROM game;";
		PreparedStatement ps = con.prepareStatement(query);
		ResultSet rs = ps.executeQuery();
		List<GameRDG> games = new ArrayList<GameRDG>();
		while(rs.next()) {
			games.add(new GameRDG(rs.getLong("gameid"), rs.getLong("challengerid"), rs.getLong("challengeeid")));
		}
		
		rs.close();
		ps.close();
		con.close();
		
		return games;
	}

}
