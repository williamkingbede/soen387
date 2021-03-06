package data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import domain.Card;

public class HandsizeRDG {

	private long gameId;
	private long playerId;
	private List<Card> cards;
	
	public HandsizeRDG(long gameId, long playerId, List<Card> cards) {
		this.gameId = gameId;
		this.playerId = playerId;
		this.cards = cards;
	}
	
	public HandsizeRDG(long gameId, long playerId) {
		this.gameId = gameId;
		this.playerId = playerId;
		this.cards = new ArrayList<Card>();
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

	public List<Card> getCards() {
		return cards;
	}

	public void setCards(List<Card> cards) {
		this.cards = cards;
	}
	
	public void insert() throws SQLException{
		Connection con = ConnectionManager.getConnection();
		String query = "INSERT INTO handsize (gameid,playerid) VALUES (?,?);";
		PreparedStatement ps = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
		ps.setLong(1, this.gameId);
		ps.setLong(2, this.playerId);
		ps.executeUpdate();
		ps.close();
		con.close();
	}
	
	public void insert(long cardid) throws SQLException{
		Connection con = ConnectionManager.getConnection();
		String query = "INSERT INTO handsize (gameid,playerid,cardid) VALUES (?,?,?);";
		PreparedStatement ps = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
		ps.setLong(1, this.gameId);
		ps.setLong(2, this.playerId);
		ps.setLong(3, cardid);
		ps.executeUpdate();
		ps.close();
		con.close();
	}
	
	public void update(long cardid) throws SQLException{
		Connection con = ConnectionManager.getConnection();
		String query = "UPDATE handsize SET cardid = ? where gameid = ? AND playerid = ?;";
		PreparedStatement ps = con.prepareStatement(query);
		ps.setLong(1, cardid);
		ps.setLong(2, this.gameId);
		ps.setLong(3, this.playerId);
		ps.executeUpdate();
		ps.close();
		con.close();
	}
	
	public void delete() throws SQLException{
		Connection con = ConnectionManager.getConnection();
		String query = "DELETE FROM handsize WHERE gameid=? AND playerid= ?;";
		PreparedStatement ps = con.prepareStatement(query);
		ps.setLong(1, this.gameId);
		ps.setLong(2, this.playerId);
		ps.executeUpdate();
		ps.close();
		con.close();
	}
	
	public static List<Card> getCardsInHand(long gameId, long playerId) throws SQLException{
		Connection con = ConnectionManager.getConnection();
		String query = "SELECT cardid FROM handsize WHERE gameid=? AND playerid=?;";
		PreparedStatement ps = con.prepareStatement(query);
		ps.setLong(1, gameId);
		ps.setLong(2, playerId);
		ResultSet rs = ps.executeQuery();
		List<Card> cardsInHand = new ArrayList<Card>();
		while(rs.next()) {
			 cardsInHand.add(new Card(rs.getLong("cardid")));
		}
		rs.close();
		ps.close();
		con.close();
		
		return cardsInHand;
	}
	
	public static List<Card> getCardsInHand(String gameId, String playerId) throws SQLException{
		Connection con = ConnectionManager.getConnection();
		String query = "SELECT cardid FROM handsize WHERE gameid=? AND playerid=?;";
		PreparedStatement ps = con.prepareStatement(query);
		ps.setString(1, gameId);
		ps.setString(2, playerId);
		ResultSet rs = ps.executeQuery();
		List<Card> cardsInHand = new ArrayList<Card>();
		while(rs.next()) {
			 cardsInHand.add(new Card(rs.getLong("cardid")));
		}
		rs.close();
		ps.close();
		con.close();
		
		return cardsInHand;
	}
	
}
