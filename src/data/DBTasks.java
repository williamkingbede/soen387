package data;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DBTasks {
	
	//Database name
	public static final String DB = "pokemon";
	
	//Table names 
	public static final String TABLE_NAME_USERS = "users";
	public static final String TABLE_NAME_CHALLENGE = "challenge";
	public static final String TABLE_NAME_GAME = "game";
	public static final String TABLE_NAME_DECK = "deck";
	public static final String TABLE_NAME_CARD = "card";
	public static final String TABLE_NAME_HAND = "hand";
	public static final String TABLE_NAME_STATUS = "status";
	public static final String TABLE_NAME_BENCH = "bench";
	
	//create table statements
	private static final String CREATE_TABLE_USERS = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_USERS + " (\r\n" + 
			"    userid INT NOT NULL AUTO_INCREMENT PRIMARY KEY,\r\n" + 
			"    version INT,\r\n" +
			"    username VARCHAR(256) unique,\r\n" + 
			"    pass VARCHAR(256)\r\n" + 
			");";
	
	private static final String CREATE_TABLE_CHALLENGE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_CHALLENGE + "(\r\n" + 
			"	challengeid INT NOT NULL AUTO_INCREMENT PRIMARY KEY,\r\n" + 
			"    version INT,\r\n" +
			"    challengerid INT,\r\n" + 
			"    challengeeid INT,\r\n" + 
			"    status INT(1),\r\n" + 
			"    \r\n" + 
			"    FOREIGN KEY (challengerid) REFERENCES users(userid),\r\n" + 
			"    FOREIGN KEY (challengeeid) REFERENCES users(userid)\r\n" + 
			");";
	
	private static final String CREATE_TABLE_GAME = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_GAME + "(\r\n" + 
			"	gameid INT NOT NULL AUTO_INCREMENT PRIMARY KEY,\r\n" + 
			"    version INT,\r\n" +
			"    challengerid INT,\r\n" + 
			"    challengeeid INT,\r\n" + 
			"    \r\n" + 
			"    FOREIGN KEY (challengerid) REFERENCES users(userid), \r\n" + 
			"	FOREIGN KEY (challengeeid) REFERENCES users(userid)\r\n" + 
			");";
	
	private static final String CREATE_TABLE_DECK = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_DECK + "(\r\n" + 
			"	deckid INT NOT NULL AUTO_INCREMENT PRIMARY KEY,\r\n" + 
			"    version INT,\r\n" +
			"    playerid INT,\r\n" + 
			"    \r\n" + 
			"    FOREIGN KEY (playerid) REFERENCES users(userid)\r\n" + 
			");";
	
	private static final String CREATE_TABLE_CARD = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_CARD + "(\r\n" + 
			"	cardid INT NOT NULL AUTO_INCREMENT PRIMARY KEY,\r\n" + 
			"    version INT,\r\n" +
			"    deckid INT,\r\n" + 
			"    type VARCHAR(1),\r\n" + 
			"    name VARCHAR(30)\r\n" + 
			");";
	
	private static final String CREATE_TABLE_HAND = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_HAND + "(\r\n" + 
			"	gameid INT,\r\n" + 
			"    playerid INT,\r\n" + 
			"    version INT,\r\n" +
			"    cardid INT,\r\n" + 
			"    \r\n" + 
			"    FOREIGN KEY (gameid) REFERENCES game(gameid),\r\n" + 
			"    FOREIGN KEY (playerid) REFERENCES users(userid),\r\n" + 
			"    FOREIGN KEY (cardid) REFERENCES card(cardid)\r\n" +
			");";
	
	private static final String CREATE_TABLE_STATUS = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_STATUS + "(\r\n" + 
			"	gameid INT,\r\n" + 
			"    playerid INT,\r\n" + 
			"    version INT,\r\n" +
			"    status INT,\r\n" + 
			"    \r\n" + 
			"    FOREIGN KEY (gameid) REFERENCES game(gameid),\r\n" + 
			"    FOREIGN KEY (playerid) REFERENCES users(userid)\r\n" + 
			");";
	
	private static final String CREATE_TABLE_BENCH = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_BENCH + "(\r\n" + 
			"	gameid INT,\r\n" + 
			"    playerid INT,\r\n" + 
			"    cardid INT,\r\n" + 
			"    version INT,\r\n" +
			"    \r\n" + 
			"    FOREIGN KEY (gameid) REFERENCES game(gameid),\r\n" + 
			"    FOREIGN KEY (playerid) REFERENCES users(userid),\r\n" + 
			"    FOREIGN KEY (cardid) REFERENCES card(cardid)\r\n" +
			");";
	
	//drop table statement
	private static final String DROP_TABLE = "DROP TABLE ";
	
	//delete statement
	private static final String DELETE = "DELETE FROM ";
	
	//drop database statement
	private static final String DROP_DB = "DROP DATABASE " + DB + ";";
	
	//All create table statements
	private static final String[] ALL_CREATE = {
			CREATE_TABLE_USERS, CREATE_TABLE_CHALLENGE, CREATE_TABLE_GAME, 
			CREATE_TABLE_DECK, CREATE_TABLE_CARD, CREATE_TABLE_HAND, 
			CREATE_TABLE_STATUS, CREATE_TABLE_BENCH
	};
	
	//All tables in database
	private static final String[] ALL_TABLES = {
			TABLE_NAME_USERS, TABLE_NAME_CHALLENGE, TABLE_NAME_GAME,
			TABLE_NAME_DECK, TABLE_NAME_CARD, TABLE_NAME_HAND, TABLE_NAME_STATUS,
			TABLE_NAME_BENCH
	};
	
	public static void createDatabase() throws SQLException {
		ConnectionManager.setUrl("jdbc:mysql://localhost/");
		Connection con = ConnectionManager.getConnection();
		String query = "CREATE DATABASE IF NOT EXISTS " + DB + ";";
		Statement st = con.createStatement();
		st.executeUpdate(query);
		st.close();
		ConnectionManager.setUrl("jdbc:mysql://localhost/" + DB);
		con.close();
	} 
	
	public static void createTableUsers() throws SQLException{
		Connection con = ConnectionManager.getConnection();
		Statement st = con.createStatement();
		st.executeUpdate(CREATE_TABLE_USERS);
		st.close();
		con.close();
	}
	
	public static void deleteAllUsers() throws SQLException{
		Connection con = ConnectionManager.getConnection();
		Statement st = con.createStatement();
		st.executeUpdate(DELETE + TABLE_NAME_USERS + ";");
		st.close();
		con.close();
	}
	
	public static void dropTableUsers() throws SQLException{
		Connection con = ConnectionManager.getConnection();
		Statement st = con.createStatement();
		st.executeUpdate(DROP_TABLE + TABLE_NAME_USERS + ";");
		st.close();
		con.close();
	}
	
	public static void createTableChallenge() throws SQLException{
		Connection con = ConnectionManager.getConnection();
		Statement st = con.createStatement();
		st.executeUpdate(CREATE_TABLE_CHALLENGE);
		st.close();
		con.close();
	}
	
	public static void deleteAllChallenges() throws SQLException{
		Connection con = ConnectionManager.getConnection();
		Statement st = con.createStatement();
		st.executeUpdate(DELETE + TABLE_NAME_CHALLENGE + ";");
		st.close();
		con.close();
	}
	
	public static void dropTableChallenge() throws SQLException{
		Connection con = ConnectionManager.getConnection();
		Statement st = con.createStatement();
		st.executeUpdate(DROP_TABLE + TABLE_NAME_CHALLENGE + ";");
		st.close();
		con.close();
	}
	
	public static void createTableGame() throws SQLException{
		Connection con = ConnectionManager.getConnection();
		Statement st = con.createStatement();
		st.executeUpdate(CREATE_TABLE_GAME);
		st.close();
		con.close();
	}
	
	public static void deleteAllGames() throws SQLException{
		Connection con = ConnectionManager.getConnection();
		Statement st = con.createStatement();
		st.executeUpdate(DELETE + TABLE_NAME_GAME + ";");
		st.close();
		con.close();
	}
	
	public static void dropTableGame() throws SQLException{
		Connection con = ConnectionManager.getConnection();
		Statement st = con.createStatement();
		st.executeUpdate(DROP_TABLE + TABLE_NAME_GAME + ";");
		st.close();
		con.close();
	}
	
	public static void createTableDeck() throws SQLException{
		Connection con = ConnectionManager.getConnection();
		Statement st = con.createStatement();
		st.executeUpdate(CREATE_TABLE_DECK);
		st.close();
		con.close();
	}
	
	public static void deleteAllDecks() throws SQLException{
		Connection con = ConnectionManager.getConnection();
		Statement st = con.createStatement();
		st.executeUpdate(DELETE + TABLE_NAME_DECK + ";");
		st.close();
		con.close();
	}
	
	public static void dropTableDeck() throws SQLException{
		Connection con = ConnectionManager.getConnection();
		Statement st = con.createStatement();
		st.executeUpdate(DROP_TABLE + TABLE_NAME_DECK + ";");
		st.close();
		con.close();
	}
	
	public static void createTableCard() throws SQLException{
		Connection con = ConnectionManager.getConnection();
		Statement st = con.createStatement();
		st.executeUpdate(CREATE_TABLE_CARD);
		st.close();
		con.close();
	}
	
	public static void deleteAllCards() throws SQLException{
		Connection con = ConnectionManager.getConnection();
		Statement st = con.createStatement();
		st.executeUpdate(DELETE + TABLE_NAME_CARD + ";");
		st.close();
		con.close();
	}
	
	public static void dropTableCard() throws SQLException{
		Connection con = ConnectionManager.getConnection();
		Statement st = con.createStatement();
		st.executeUpdate(DROP_TABLE + TABLE_NAME_CARD + ";");
		st.close();
		con.close();
	}
	
	public static void createTableHandsize() throws SQLException{
		Connection con = ConnectionManager.getConnection();
		Statement st = con.createStatement();
		st.executeUpdate(CREATE_TABLE_HAND);
		st.close();
		con.close();
	}
	
	public static void deleteAllHandsize() throws SQLException{
		Connection con = ConnectionManager.getConnection();
		Statement st = con.createStatement();
		st.executeUpdate(DELETE + TABLE_NAME_HAND + ";");
		st.close();
		con.close();
	}
	
	public static void dropTableHandsize() throws SQLException{
		Connection con = ConnectionManager.getConnection();
		Statement st = con.createStatement();
		st.executeUpdate(DROP_TABLE + TABLE_NAME_HAND + ";");
		st.close();
		con.close();
	}
	
	public static void createTableStatus() throws SQLException{
		Connection con = ConnectionManager.getConnection();
		Statement st = con.createStatement();
		st.executeUpdate(CREATE_TABLE_STATUS);
		st.close();
		con.close();
	}
	
	public static void deleteAllStatus() throws SQLException{
		Connection con = ConnectionManager.getConnection();
		Statement st = con.createStatement();
		st.executeUpdate(DELETE + TABLE_NAME_STATUS + ";");
		st.close();
		con.close();
	}
	
	public static void dropTableStatus() throws SQLException{
		Connection con = ConnectionManager.getConnection();
		Statement st = con.createStatement();
		st.executeUpdate(DROP_TABLE + TABLE_NAME_STATUS + ";");
		st.close();
		con.close();
	}
	
	public static void createTableBench() throws SQLException{
		Connection con = ConnectionManager.getConnection();
		Statement st = con.createStatement();
		st.executeUpdate(CREATE_TABLE_BENCH);
		st.close();
		con.close();
	}
	
	public static void deleteAllBench() throws SQLException{
		Connection con = ConnectionManager.getConnection();
		Statement st = con.createStatement();
		st.executeUpdate(DELETE + TABLE_NAME_BENCH + ";");
		st.close();
		con.close();
	}
	
	public static void dropTableBench() throws SQLException{
		Connection con = ConnectionManager.getConnection();
		Statement st = con.createStatement();
		st.executeUpdate(DROP_TABLE + TABLE_NAME_BENCH + ";");
		st.close();
		con.close();
	}
	
	public static void createTableByName(String tableName) throws SQLException{
		for(int i = 0; i < ALL_TABLES.length; i++) {
			if(ALL_TABLES[i] == tableName) {
				Connection con = ConnectionManager.getConnection();
				Statement st = con.createStatement();
				st.executeUpdate(ALL_CREATE[i]);
				st.close();
				con.close();
			}
		}
	}
	
	public static void dropTableByName(String tableName) throws SQLException{
		for(int i = 0; i < ALL_TABLES.length; i++) {
			if(ALL_TABLES[i] == tableName) {
				Connection con = ConnectionManager.getConnection();
				Statement st = con.createStatement();
				st.executeUpdate(DELETE + ALL_TABLES[i] + ";");
				st.close();
				con.close();
			}
		}
	}
	
	public static void createAllTables() throws SQLException{
		Connection con = ConnectionManager.getConnection();
		Statement st = con.createStatement();
		for(int i = 0; i < ALL_CREATE.length; i++) {
			st.executeUpdate(ALL_CREATE[i]);
		}
		st.close();
		con.close();
	}
	
	public static void deleteAllRecords() throws SQLException{
		Connection con = ConnectionManager.getConnection();
		Statement st = con.createStatement();
		for(int i = 0; i < ALL_TABLES.length; i++) {
			st.executeUpdate(DELETE + ALL_TABLES[i] + ";");
		}
		st.close();
		con.close();
	}
	
	public static void dropAllTables() throws SQLException{
		Connection con = ConnectionManager.getConnection();
		Statement st = con.createStatement();
		for(int i = 0; i < ALL_TABLES.length; i++) {
			st.executeUpdate(DROP_TABLE + ALL_TABLES[i] + ";");
		}
		st.close();
		con.close();
	}
	
	public static void dropDatabase() throws SQLException{
		Connection con = ConnectionManager.getConnection();
		Statement st = con.createStatement();
		st.executeUpdate(DROP_DB);
		st.close();
		con.close();
	}

}
