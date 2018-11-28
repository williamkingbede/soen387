package data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CardRDG {
	
	private long id;
	private long deckId;
	private String type;
	private String name;
	
	public CardRDG(long id, long deckId, String type, String name) {
		this.id = id;
		this.deckId = deckId;
		this.type = type;
		this.name = name;
	}
	
	public CardRDG(long deckId, String type, String name) {
		this.deckId = deckId;
		this.type = type;
		this.name = name;
	}
	
	public CardRDG(String type, String name) {
		this.type = type;
		this.name = name;
	}

	public long getId() {
		return id;
	}
	
	public long getDeckId() {
		return deckId;
	}
	
	public void setDeckId(long deckId) {
		this.deckId = deckId;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public long insert() throws SQLException{
		Connection con = ConnectionManager.getConnection();
		String query = "INSERT INTO card (deckid,type,name) VALUES (?,?,?);";
		PreparedStatement ps = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
		ps.setLong(1, this.deckId);
		ps.setString(2, this.type);
		ps.setString(3, this.name);
		long id;
		int affectedRows = ps.executeUpdate();

        if (affectedRows == 0) {
            throw new SQLException("Creating card failed, no rows affected.");
        }

        try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                id = generatedKeys.getLong(1);
            }
            else {
                throw new SQLException("Creating card failed, no ID obtained.");
            }
        }
		ps.close();
		con.close();
		return id;
	}
	
	public void update() throws SQLException{
		Connection con = ConnectionManager.getConnection();
		String query = "UPDATE card SET deckid = ?, type = ?, name = ? where cardid = ?;";
		PreparedStatement ps = con.prepareStatement(query);
		ps.setLong(1, this.deckId);
		ps.setString(2, this.type);
		ps.setString(3, this.name);
		ps.setLong(4, this.deckId);
		ps.executeUpdate();
		ps.close();
		con.close();
	}
	
	public void update(long cardId) throws SQLException{
		Connection con = ConnectionManager.getConnection();
		String query = "UPDATE card SET type = ?, name = ? where cardid = ?;";
		PreparedStatement ps = con.prepareStatement(query);
		ps.setString(1, this.type);
		ps.setString(2, this.name);
		ps.setLong(3, cardId);
		ps.executeUpdate();
		ps.close();
		con.close();
	}
	
	
	public void updateSoftDelete() throws SQLException{
		Connection con = ConnectionManager.getConnection();
		String query = "UPDATE card SET deckid = 0 where cardid = ?;";
		PreparedStatement ps = con.prepareStatement(query);
		ps.setLong(1, this.id);
		ps.executeUpdate();
		ps.close();
		con.close();
	}
	
	public void delete() throws SQLException{
		Connection con = ConnectionManager.getConnection();
		String query = "DELETE FROM card WHERE cardid=?;";
		PreparedStatement ps = con.prepareStatement(query);
		ps.setLong(1, this.id);
		ps.executeUpdate();
		ps.close();
		con.close();
	}
	
	public static CardRDG findById(Long id) throws SQLException{
		Connection con = ConnectionManager.getConnection();
		String query = "SELECT * FROM card WHERE cardid=?;";
		PreparedStatement ps = con.prepareStatement(query);
		ps.setLong(1, id);
		ResultSet rs = ps.executeQuery();
		CardRDG c = null;
		if(rs.next()) {
			c = new CardRDG(rs.getLong("cardid"), rs.getLong("deckid"), rs.getString("type"), rs.getString("name"));
		} else {
			return null;
		}
		
		rs.close();
		ps.close();
		con.close();
		
		return c;
	}
	
	public static CardRDG findById(String id) throws SQLException{
		Connection con = ConnectionManager.getConnection();
		String query = "SELECT * FROM card WHERE cardid=?;";
		PreparedStatement ps = con.prepareStatement(query);
		ps.setString(1, id);
		ResultSet rs = ps.executeQuery();
		CardRDG c = null;
		if(rs.next()) {
			c = new CardRDG(rs.getLong("cardid"), rs.getLong("deckid"), rs.getString("type"), rs.getString("name"));
		} else {
			return null;
		}
		
		rs.close();
		ps.close();
		con.close();
		
		return c;
	}
	
	public static List<CardRDG> findByDeck(long deckid) throws SQLException{
		Connection con = ConnectionManager.getConnection();
		String query = "SELECT * FROM card WHERE deckid=?;";
		PreparedStatement ps = con.prepareStatement(query);
		ps.setLong(1, deckid);
		ResultSet rs = ps.executeQuery();
		List<CardRDG> cards = new ArrayList<CardRDG>();
		while(rs.next()) {
			cards.add(new CardRDG(rs.getLong("cardid"), rs.getLong("deckid"), rs.getString("type"), rs.getString("name")));
		}
		
		rs.close();
		ps.close();
		con.close();
		
		return cards;
	}

}
