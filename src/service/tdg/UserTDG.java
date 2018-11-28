package service.tdg;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.dsrg.soenea.service.logging.SQLLogger;

import data.ConnectionManager;

public class UserTDG {

	public static long insert(long version, String username, String password) throws SQLException{
		Connection con = ConnectionManager.getConnection();
		String query = "INSERT INTO users (version,username,pass) VALUES (?,?,?);";
		PreparedStatement ps = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
		ps.setLong(1, version);
		ps.setString(2, username);
		ps.setString(3, password);
		long id;
		int affectedRows = ps.executeUpdate();

        if (affectedRows == 0) {
            throw new SQLException("Creating user failed, no rows affected.");
        }

        try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                id = generatedKeys.getLong(1);
            }
            else {
                throw new SQLException("Creating user failed, no ID obtained.");
            }
        }
		ps.close();
		con.close();
		return id;
	}
	
	public static int update(long id, long version, String username, String password) throws SQLException{
		Connection con = ConnectionManager.getConnection();
		String query = "UPDATE users SET version=version+1, username = ?, pass = ? "
				+ "where userid = ? AND version=?;";
		PreparedStatement ps = con.prepareStatement(query);
		ps.setString(1, username);
		ps.setString(2, password);
		ps.setLong(3, id);
		ps.setLong(4, version);
		int count = SQLLogger.processUpdate(ps);
		ps.close();
		con.close();
		return count;
	}
	
	public static int update( long id, long version, String username) throws SQLException{
		Connection con = ConnectionManager.getConnection();
		String query = "UPDATE users SET version=version+1, username = ? where userid = ? AND version=?;";
		PreparedStatement ps = con.prepareStatement(query);
		ps.setString(1, username);
		ps.setLong(2, id);
		ps.setLong(3, version);
		int count = SQLLogger.processUpdate(ps);
		ps.close();
		con.close();
		return count;
	}
	
	public static int delete(long id, long version) throws SQLException{
		Connection con = ConnectionManager.getConnection();
		String query = "DELETE FROM users WHERE userid=? AND version=?;";
		PreparedStatement ps = con.prepareStatement(query);
		ps.setLong(1, id);
		ps.setLong(2, version);
		int count = SQLLogger.processUpdate(ps);
		ps.close();
		con.close();
		return count;
	}
	
}
