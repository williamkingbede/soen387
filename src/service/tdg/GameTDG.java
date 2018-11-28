package service.tdg;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.dsrg.soenea.service.logging.SQLLogger;

import data.ConnectionManager;

public class GameTDG {

	public static long insert(long version, long challengerId, long challengeeId) throws SQLException{
		Connection con = ConnectionManager.getConnection();
		String query = "INSERT INTO game (version,challengerid,challengeeid) VALUES (?,?,?);";
		PreparedStatement ps = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
		ps.setLong(1, version);
		ps.setLong(2, challengerId);
		ps.setLong(3, challengeeId);
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
	
	public static int update(long id, long version, long challengerId, long challengeeId) throws SQLException{
		Connection con = ConnectionManager.getConnection();
		String query = "UPDATE game SET version=version+1, challengerid = ?, "
				+ "challengeeid = ? where gameid = ? AND version=?;";
		PreparedStatement ps = con.prepareStatement(query);
		ps.setLong(1, challengerId);
		ps.setLong(2, challengeeId);
		ps.setLong(3, id);
		ps.setLong(4, version);
		int count = SQLLogger.processUpdate(ps);
		ps.close();
		con.close();
		return count;
	}
	
	public static int delete(long id, long version) throws SQLException{
		Connection con = ConnectionManager.getConnection();
		String query = "DELETE FROM game WHERE gameid=? AND version=?;";
		PreparedStatement ps = con.prepareStatement(query);
		ps.setLong(1, id);
		ps.setLong(2, version);
		int count = SQLLogger.processUpdate(ps);
		ps.close();
		con.close();
		return count;
	}
	
}
