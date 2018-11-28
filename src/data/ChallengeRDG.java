package data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ChallengeRDG {
	
	private long id;
	private long challengerId;
	private long challengeeId;
	private int status;
	
	public ChallengeRDG(long challengerId, long challengeeId, int status) {
		this.challengerId = challengerId;
		this.challengeeId = challengeeId;
		this.status = status;
	}

	public ChallengeRDG(long id, long challengerId, long challengeeId, int status) {
		this.id = id;
		this.challengerId = challengerId;
		this.challengeeId = challengeeId;
		this.status = status;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
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
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	public long insert() throws SQLException{
		Connection con = ConnectionManager.getConnection();
		String query = "INSERT INTO challenge (challengerid,challengeeid,status) VALUES (?,?,?);";
		PreparedStatement ps = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
		ps.setLong(1, this.challengerId);
		ps.setLong(2, this.challengeeId);
		ps.setInt(3, this.status);
		long id;
		int affectedRows = ps.executeUpdate();

        if (affectedRows == 0) {
            throw new SQLException("Creating challenge failed, no rows affected.");
        }

        try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                id = generatedKeys.getLong(1);
            }
            else {
                throw new SQLException("Creating challenge failed, no ID obtained.");
            }
        }
		ps.close();
		con.close();
		return id;
	}
	
	public void update() throws SQLException{
		Connection con = ConnectionManager.getConnection();
		String query = "UPDATE challenge SET challengerid = ?, challengeeid = ?, status = ? where challengeid = ?;";
		PreparedStatement ps = con.prepareStatement(query);
		ps.setLong(1, this.challengerId);
		ps.setLong(2, this.challengeeId);
		ps.setInt(3, this.status);
		ps.setLong(4, this.id);
		ps.executeUpdate();
		ps.close();
		con.close();
	}
	
	public void delete() throws SQLException{
		Connection con = ConnectionManager.getConnection();
		String query = "DELETE FROM challenge WHERE challengeid=?;";
		PreparedStatement ps = con.prepareStatement(query);
		ps.setLong(1, this.id);
		ps.executeUpdate();
		ps.close();
		con.close();
	}
	
	public static ChallengeRDG findById(Long id) throws SQLException{
		Connection con = ConnectionManager.getConnection();
		String query = "SELECT * FROM challenge WHERE challengeid=?;";
		PreparedStatement ps = con.prepareStatement(query);
		ps.setLong(1, id);
		ResultSet rs = ps.executeQuery();
		ChallengeRDG c = null;
		if(rs.next()) {
			c = new ChallengeRDG(rs.getLong("challengeid"), rs.getLong("challengerid"), rs.getLong("challengeeid"), rs.getInt("status"));
		} else {
			return null;
		}
		
		rs.close();
		ps.close();
		con.close();
		
		return c;
	}
	
	public static ChallengeRDG findById(String id) throws SQLException{
		Connection con = ConnectionManager.getConnection();
		String query = "SELECT * FROM challenge WHERE challengeid=?;";
		PreparedStatement ps = con.prepareStatement(query);
		ps.setString(1, id);
		ResultSet rs = ps.executeQuery();
		ChallengeRDG c = null;
		if(rs.next()) {
			c = new ChallengeRDG(rs.getLong("challengeid"), rs.getLong("challengerid"), rs.getLong("challengeeid"), rs.getInt("status"));
		} else {
			return null;
		}
		
		rs.close();
		ps.close();
		con.close();
		
		return c;
	}
	
	public static List<ChallengeRDG> findOpenByChallenger(long challengerid) throws SQLException{
		Connection con = ConnectionManager.getConnection();
		String query = "SELECT * FROM challenge WHERE challengerid=? AND status=0;";
		PreparedStatement ps = con.prepareStatement(query);
		ps.setLong(1, challengerid);
		ResultSet rs = ps.executeQuery();
		List<ChallengeRDG> challenges = new ArrayList<ChallengeRDG>();
		while(rs.next()) {
			challenges.add(new ChallengeRDG(rs.getLong("challengeid"), rs.getLong("challengerid"), rs.getLong("challengeeid"), rs.getInt("status")));
		}
		
		rs.close();
		ps.close();
		con.close();
		
		return challenges;
	}
	
	public static List<ChallengeRDG> findOpenByChallengee(long challengeeid) throws SQLException{
		Connection con = ConnectionManager.getConnection();
		String query = "SELECT * FROM challenge WHERE challengeeid=? AND status=0;";
		PreparedStatement ps = con.prepareStatement(query);
		ps.setLong(1, challengeeid);
		ResultSet rs = ps.executeQuery();
		List<ChallengeRDG> challenges = new ArrayList<ChallengeRDG>();
		while(rs.next()) {
			challenges.add(new ChallengeRDG(rs.getLong("challengeid"), rs.getLong("challengerid"), rs.getLong("challengeeid"), rs.getInt("status")));
		}
		
		rs.close();
		ps.close();
		con.close();
		
		return challenges;
	}
	
	public static List<ChallengeRDG> findOpenByPlayer(long playerid) throws SQLException{
		Connection con = ConnectionManager.getConnection();
		String query = "SELECT * FROM challenge WHERE (challengerid=? OR challengeeid=?) AND status=0;";
		PreparedStatement ps = con.prepareStatement(query);
		ps.setLong(1, playerid);
		ps.setLong(2, playerid);
		ResultSet rs = ps.executeQuery();
		List<ChallengeRDG> challenges = new ArrayList<ChallengeRDG>();
		while(rs.next()) {
			challenges.add(new ChallengeRDG(rs.getLong("challengeid"), rs.getLong("challengerid"), rs.getLong("challengeeid"), rs.getInt("status")));
		}
		
		rs.close();
		ps.close();
		con.close();
		
		return challenges;
	}
	
	public static List<ChallengeRDG> findAll() throws SQLException{
		Connection con = ConnectionManager.getConnection();
		String query = "SELECT * FROM challenge;";
		PreparedStatement ps = con.prepareStatement(query);
		ResultSet rs = ps.executeQuery();
		List<ChallengeRDG> challenges = new ArrayList<ChallengeRDG>();
		while(rs.next()) {
			challenges.add(new ChallengeRDG(rs.getLong("challengeid"), rs.getLong("challengerid"), rs.getLong("challengeeid"), rs.getInt("status")));
		}
		
		rs.close();
		ps.close();
		con.close();
		
		return challenges;
	}
	
	public static List<ChallengeRDG> findAllOpen() throws SQLException{
		Connection con = ConnectionManager.getConnection();
		String query = "SELECT * FROM challenge WHERE status=0;";
		PreparedStatement ps = con.prepareStatement(query);
		ResultSet rs = ps.executeQuery();
		List<ChallengeRDG> challenges = new ArrayList<ChallengeRDG>();
		while(rs.next()) {
			challenges.add(new ChallengeRDG(rs.getLong("challengeid"), rs.getLong("challengerid"), rs.getLong("challengeeid"), rs.getInt("status")));
		}
		
		rs.close();
		ps.close();
		con.close();
		
		return challenges;
	}


}
