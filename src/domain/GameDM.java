package domain;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import data.GameRDG;

public class GameDM {


	public static long insert(Game game) throws SQLException {
		GameRDG gameRdg = new GameRDG(game.getId(), game.getChallengerId(), game.getChallengeeId());
		return gameRdg.insert();
	}
	
	public static void update(Game game) throws SQLException {
		GameRDG gameRdg = new GameRDG(game.getId(), game.getChallengerId(), game.getChallengeeId());
		gameRdg.update();
	}
	
	public static void delete(Game game) throws SQLException {
		GameRDG gameRdg = new GameRDG(game.getId(), game.getChallengerId(), game.getChallengeeId());
		gameRdg.delete();
	}
	
	public static Game findById(long id) throws SQLException {
		GameRDG gameRdg = GameRDG.findById(id);
		if(gameRdg != null) {
			Game game = new Game(gameRdg.getId(), gameRdg.getChallengerId(), gameRdg.getChallengeeId());
			return game;
		}
		else 
			return null;
	}
	
	public static Game findById(String id) throws SQLException {
		GameRDG gameRdg = GameRDG.findById(id);
		if(gameRdg != null) {
			Game game = new Game(gameRdg.getId(), gameRdg.getChallengerId(), gameRdg.getChallengeeId());
			return game;
		}
		else 
			return null;
	}
	
	public static List<Game> findByPlayer(long playerid) throws SQLException {
		List<GameRDG> gamesRdg = GameRDG.findByPlayer(playerid);
		List<Game> games = new ArrayList<Game>();
		if(!gamesRdg.isEmpty()) {
			for(GameRDG gameRdg : gamesRdg) {
				Game game = new Game(gameRdg.getId(), gameRdg.getChallengerId(), gameRdg.getChallengeeId());
				games.add(game);	
			}
			return games;
		}
		else
			return null;
	}
	
	public static List<Game> findAll() throws SQLException{
		List<GameRDG> gamesRdg = GameRDG.findAll();
		List<Game> games = new ArrayList<Game>();
		if(!gamesRdg.isEmpty()) {
			for(GameRDG gameRdg : gamesRdg) {
				Game game = new Game(gameRdg.getId(), gameRdg.getChallengerId(), gameRdg.getChallengeeId());
				games.add(game);	
			}
			return games;
		}
		else
			return null;
	}
	
}
