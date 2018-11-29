package service.tdg;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.dsrg.soenea.service.logging.SQLLogger;

import data.ConnectionManager;

public class CardTDG {
	
	public static long insert(long version, long deckId, String type, String name) throws SQLException{
		Connection con = ConnectionManager.getConnection();
		String query = "INSERT INTO card (version,deckid,type,name) VALUES (?,?,?,?);";
		PreparedStatement ps = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
		ps.setLong(1, version);
		ps.setLong(2, deckId);
		ps.setString(3, type);
		ps.setString(4, name);
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
	
	public static int update(long cardId, long deckId, long version, String type, String name) throws SQLException{
		Connection con = ConnectionManager.getConnection();
		String query = "UPDATE card SET version=version+1,, deckid = ?, type = ?, name = ? where cardid = ? AND version = ?;";
		PreparedStatement ps = con.prepareStatement(query);
		ps.setLong(1, deckId);
		ps.setString(2, type);
		ps.setString(3, name);
		ps.setLong(4, cardId);
		ps.setLong(5, version);
		int count = SQLLogger.processUpdate(ps);
		ps.close();
		con.close();
		return count;
	}
	
	public static int update(long cardId, long version, String type, String name) throws SQLException{
		Connection con = ConnectionManager.getConnection();
		String query = "UPDATE card SET version=version+1, type = ?, name = ? where cardid = ? AND version= ?;";
		PreparedStatement ps = con.prepareStatement(query);
		ps.setString(1, type);
		ps.setString(2, name);
		ps.setLong(3, cardId);
		ps.setLong(4, version);
		int count = SQLLogger.processUpdate(ps);
		ps.close();
		con.close();
		return count;
	}
	
	public static int updateSoftDelete(long id, long version) throws SQLException{
		Connection con = ConnectionManager.getConnection();
		String query = "UPDATE card SET deckid = 0 where cardid = ? AND version = ?;";
		PreparedStatement ps = con.prepareStatement(query);
		ps.setLong(1, id);
		ps.setLong(2, version);
		int count = SQLLogger.processUpdate(ps);
		ps.close();
		con.close();
		return count;
	}
	
	public static int delete(long id, long version) throws SQLException{
		Connection con = ConnectionManager.getConnection();
		String query = "DELETE FROM card WHERE cardid=? AND version = ?;";
		PreparedStatement ps = con.prepareStatement(query);
		ps.setLong(1, id);
		ps.setLong(2, version);
		int count = SQLLogger.processUpdate(ps);
		ps.close();
		con.close();
		return count;
	}

}
