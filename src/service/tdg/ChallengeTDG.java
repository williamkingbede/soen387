package service.tdg;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.dsrg.soenea.service.logging.SQLLogger;

import data.ConnectionManager;

public class ChallengeTDG {
	
	public static long insert(long version, long challengerId, long challengeeId, int status) throws SQLException{
		Connection con = ConnectionManager.getConnection();
		String query = "INSERT INTO challenge (version,challengerid,challengeeid,status) VALUES (?,?,?);";
		PreparedStatement ps = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
		ps.setLong(1, version);
		ps.setLong(2, challengerId);
		ps.setLong(3, challengeeId);
		ps.setInt(4, status);
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
	
	public static int update(long version,  long id, long challengerId, long challengeeId, int status) throws SQLException{
		Connection con = ConnectionManager.getConnection();
		String query = "UPDATE challenge SET version=version+1, challengerid = ?, challengeeid = ?, status = ? where challengeid = ? AND version = ?;";
		PreparedStatement ps = con.prepareStatement(query);
		ps.setLong(1, challengerId);
		ps.setLong(2, challengeeId);
		ps.setInt(3, status);
		ps.setLong(4, id);
		ps.setLong(5, version);
		int count = SQLLogger.processUpdate(ps);
		ps.close();
		con.close();
		return count;
	}
	
	public static int delete(long id) throws SQLException{
		Connection con = ConnectionManager.getConnection();
		String query = "DELETE FROM challenge WHERE challengeid=?;";
		PreparedStatement ps = con.prepareStatement(query);
		ps.setLong(1, id);
		int count = SQLLogger.processUpdate(ps);
		ps.close();
		con.close();
		return count;
	}
	
	public static int delete(long id, long version) throws SQLException{
		Connection con = ConnectionManager.getConnection();
		String query = "DELETE FROM game WHERE challengeid=? AND version=?;";
		PreparedStatement ps = con.prepareStatement(query);
		ps.setLong(1, id);
		ps.setLong(2, version);
		int count = SQLLogger.processUpdate(ps);
		ps.close();
		con.close();
		return count;
	}

}
