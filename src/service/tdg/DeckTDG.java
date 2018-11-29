package service.tdg;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.dsrg.soenea.service.logging.SQLLogger;

import data.ConnectionManager;

public class DeckTDG {

	public static long insert(long version, long playerId) throws SQLException{
		Connection con = ConnectionManager.getConnection();
		String query = "INSERT INTO deck (version,playerid) VALUES (?,?);";
		PreparedStatement ps = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
		ps.setLong(1, version);
		ps.setLong(2, playerId);
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
	
	public static int update(long id, long version, long playerId) throws SQLException{
		Connection con = ConnectionManager.getConnection();
		String query = "UPDATE deck SET version=version+1, playerid = ? where deckid = ? AND version = ?;";
		PreparedStatement ps = con.prepareStatement(query);
		ps.setLong(1, playerId);
		ps.setLong(2, id);
		ps.setLong(3, version);
		int count = SQLLogger.processUpdate(ps);
		ps.close();
		con.close();
		return count;
	}
	
	public static int delete(long id, long version) throws SQLException{
		Connection con = ConnectionManager.getConnection();
		String query = "DELETE FROM deck WHERE deckid=? AND version =?;";
		PreparedStatement ps = con.prepareStatement(query);
		ps.setLong(1, id);
		ps.setLong(2, version);
		int count = SQLLogger.processUpdate(ps);
		ps.close();
		con.close();
		return count;
	}
	
}
