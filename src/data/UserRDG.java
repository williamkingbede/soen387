package data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class UserRDG {

	private long id;
	private String username;
	private String password;
	
	public UserRDG(long id, String username, String password) {
		this.id = id;
		this.username = username;
		this.password = password;
	}
	
	public UserRDG(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	public long getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public long insert() throws SQLException{
		Connection con = ConnectionManager.getConnection();
		String query = "INSERT INTO users (username,pass) VALUES (?,?);";
		PreparedStatement ps = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
		ps.setString(1, this.username);
		ps.setString(2, this.password);
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
	
	public void update() throws SQLException{
		Connection con = ConnectionManager.getConnection();
		String query = "UPDATE users SET username = ?, pass = ? where userid = ?;";
		PreparedStatement ps = con.prepareStatement(query);
		ps.setString(1, this.username);
		ps.setString(2, this.password);
		ps.setLong(3, this.id);
		ps.executeUpdate();
		ps.close();
		con.close();
	}
	
	public void delete() throws SQLException{
		Connection con = ConnectionManager.getConnection();
		String query = "DELETE FROM users WHERE userid=?;";
		PreparedStatement ps = con.prepareStatement(query);
		ps.setLong(1, this.id);
		ps.executeUpdate();
		ps.close();
		con.close();
	}
	
	public static UserRDG findById(Long id) throws SQLException{
		Connection con = ConnectionManager.getConnection();
		String query = "SELECT * FROM users WHERE userid=?;";
		PreparedStatement ps = con.prepareStatement(query);
		ps.setLong(1, id);
		ResultSet rs = ps.executeQuery();
		UserRDG u = null;
		if(rs.next()) {
			u = new UserRDG(rs.getLong("userid"), rs.getString("username"), rs.getString("pass"));
		
		} else {
			return null;
		}
		
		rs.close();
		ps.close();
		con.close();
		
		return u;
	}
	
	public static UserRDG findById(String id) throws SQLException{
		Connection con = ConnectionManager.getConnection();
		String query = "SELECT * FROM users WHERE userid=?;";
		PreparedStatement ps = con.prepareStatement(query);
		ps.setString(1, id);
		ResultSet rs = ps.executeQuery();
		UserRDG u = null;
		if(rs.next()) {
			u = new UserRDG(rs.getLong("userid"), rs.getString("username"), rs.getString("pass"));
		
		} else {
			return null;
		}
		
		rs.close();
		ps.close();
		con.close();
		
		return u;
	}
	
	
	public static UserRDG findByUsername(String username) throws SQLException{
		Connection con = ConnectionManager.getConnection();
		String query = "SELECT * FROM users WHERE username=?;";
		PreparedStatement ps = con.prepareStatement(query);
		ps.setString(1, username);
		ResultSet rs = ps.executeQuery();
		UserRDG u = null;
		if(rs.next()) {
			u = new UserRDG(rs.getLong("userid"), rs.getString("username"), rs.getString("pass"));
			
		} else {
			return null;
		}
		
		rs.close();
		ps.close();
		con.close();
		
		return u;
	}
	
	
	public static UserRDG findByUsernameAndPassword(String username, String password) throws SQLException{
		Connection con = ConnectionManager.getConnection();
		String query = "SELECT * FROM users WHERE username=? AND pass=?;";
		PreparedStatement ps = con.prepareStatement(query);
		ps.setString(1, username);
		ps.setString(2, password);
		ResultSet rs = ps.executeQuery();
		UserRDG u = null;
		if(rs.next()) {
			u = new UserRDG(rs.getLong("userid"), rs.getString("username"), rs.getString("pass"));
			
		} else {
			return null;
		}
		
		rs.close();
		ps.close();
		con.close();
		
		return u;
	}
	
	public static List<UserRDG> findAll() throws SQLException{
		Connection con = ConnectionManager.getConnection();
		String query = "SELECT * FROM users;";
		PreparedStatement ps = con.prepareStatement(query);
		ResultSet rs = ps.executeQuery();
		List<UserRDG> users = new ArrayList<UserRDG>();
		while(rs.next()) {
			users.add(new UserRDG(rs.getLong("userid"), rs.getString("username"), rs.getString("pass")));
		}
		rs.close();
		ps.close();
		con.close();
		
		return users;
	}
	
}
