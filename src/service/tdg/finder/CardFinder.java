package service.tdg.finder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dsrg.soenea.service.logging.SQLLogger;

import data.ConnectionManager;

public class CardFinder {
	
	public static ResultSet findById(Long id) throws SQLException{
		Connection con = ConnectionManager.getConnection();
		String query = "SELECT * FROM card WHERE cardid=?;";
		PreparedStatement ps = con.prepareStatement(query);
		ps.setLong(1, id);
		return SQLLogger.processQuery(ps);
	}
	
	public static ResultSet findById(String id) throws SQLException{
		Connection con = ConnectionManager.getConnection();
		String query = "SELECT * FROM card WHERE cardid=?;";
		PreparedStatement ps = con.prepareStatement(query);
		ps.setString(1, id);
		return SQLLogger.processQuery(ps);
	}
	
	public static ResultSet findByDeck(long deckid) throws SQLException{
		Connection con = ConnectionManager.getConnection();
		String query = "SELECT * FROM card WHERE deckid=?;";
		PreparedStatement ps = con.prepareStatement(query);
		ps.setLong(1, deckid);
		return SQLLogger.processQuery(ps);
	}

}
