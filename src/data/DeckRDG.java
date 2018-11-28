package data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import domain.Card;

public class DeckRDG {
	
	private long id;
	private long playerId;
	private List<Card> cards;
	
	public DeckRDG(long id, long playerId, List<Card> cards) {
		this.id = id;
		this.playerId = playerId;
		this.cards = cards;
	}
	
	public DeckRDG(long id, long playerId) {
		this.id = id;
		this.playerId = playerId;
		this.cards = new ArrayList<Card>();
	}
	
	public DeckRDG(long playerId, List<Card> cards) {
		this.playerId = playerId;
		this.cards = cards;
	}
	
	public DeckRDG(long playerId) {
		this.playerId = playerId;
		this.cards = new ArrayList<Card>();
	}

	public long getId() {
		return id;
	}

	public long getPlayerId() {
		return playerId;
	}

	public void setPlayerId(long playerId) {
		this.playerId = playerId;
	}

	public List<Card> getCards() {
		return cards;
	}

	public void setCards(List<Card> cards) {
		this.cards = cards;
	}
	
	public long insert() throws SQLException{
		Connection con = ConnectionManager.getConnection();
		String query = "INSERT INTO deck (playerid) VALUES (?);";
		PreparedStatement ps = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
		ps.setLong(1, this.playerId);
		long id;
		int affectedRows = ps.executeUpdate();

        if (affectedRows == 0) {
            throw new SQLException("Creating deck failed, no rows affected.");
        }

        try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                id = generatedKeys.getLong(1);
            }
            else {
                throw new SQLException("Creating deck failed, no ID obtained.");
            }
        }
		ps.close();
		con.close();
		return id;
	}
	
	public void update() throws SQLException{
		Connection con = ConnectionManager.getConnection();
		String query = "UPDATE deck SET playerid = ? where deckid = ?;";
		PreparedStatement ps = con.prepareStatement(query);
		ps.setLong(1, this.playerId);
		ps.setLong(2, this.id);
		ps.executeUpdate();
		ps.close();
		con.close();
	}
	
	public void delete() throws SQLException{
		Connection con = ConnectionManager.getConnection();
		String query = "DELETE FROM deck WHERE deckid=?;";
		PreparedStatement ps = con.prepareStatement(query);
		ps.setLong(1, this.id);
		ps.executeUpdate();
		ps.close();
		con.close();
	}
	
	public static DeckRDG findById(Long id) throws SQLException{
		Connection con = ConnectionManager.getConnection();
		String query = "SELECT * FROM deck WHERE deckid=?;";
		PreparedStatement ps = con.prepareStatement(query);
		ps.setLong(1, id);
		ResultSet rs = ps.executeQuery();
		DeckRDG d = null;
		if(rs.next()) {
			d = new DeckRDG(rs.getLong("deckid"), rs.getLong("playerid"));
		} else {
			return null;
		}
		
		rs.close();
		ps.close();
		con.close();
		
		return d;
	}
	
	public static DeckRDG findById(String id) throws SQLException{
		Connection con = ConnectionManager.getConnection();
		String query = "SELECT * FROM deck WHERE deckid=?;";
		PreparedStatement ps = con.prepareStatement(query);
		ps.setString(1, id);
		ResultSet rs = ps.executeQuery();
		DeckRDG d = null;
		if(rs.next()) {
			d = new DeckRDG(rs.getLong("deckid"), rs.getLong("playerid"));
		} else {
			return null;
		}
		
		rs.close();
		ps.close();
		con.close();
		
		return d;
	}
	
	public static DeckRDG findByPlayer(long playerid) throws SQLException{
		Connection con = ConnectionManager.getConnection();
		String query = "SELECT * FROM deck WHERE playerid=?;";
		PreparedStatement ps = con.prepareStatement(query);
		ps.setLong(1, playerid);
		ResultSet rs = ps.executeQuery();
		DeckRDG d = null;
		if(rs.next()) {
			d = new DeckRDG(rs.getLong("deckid"), rs.getLong("playerid"));
		} else {
			return null;
		}
		
		rs.close();
		ps.close();
		con.close();
		
		return d;
	}

}
