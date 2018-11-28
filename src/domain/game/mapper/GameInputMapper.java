package domain.game.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.domain.mapper.DomainObjectNotFoundException;
import org.dsrg.soenea.domain.mapper.IdentityMap;
import org.dsrg.soenea.domain.producer.IdentityBasedProducer;
import org.dsrg.soenea.domain.producer.IdentityBasedProducerMethod;

import domain.game.Game;
import domain.game.GameFactory;
import domain.game.GameProxy;
import domain.game.IGame;
import service.tdg.finder.GameFinder;

public class GameInputMapper implements IdentityBasedProducer{
	
	public static List<IGame> findAll() throws SQLException, MapperException {
		ArrayList<IGame> games = new ArrayList<IGame>();
		
		ResultSet rs = GameFinder.findAll();
		while(rs.next()) {
			try {
				games.add(IdentityMap.get(rs.getLong("id"), Game.class));
				continue;
			} catch (DomainObjectNotFoundException e) {	}
			games.add(new GameProxy(rs.getLong("id")));
		}
		
		return games;
	}

	@IdentityBasedProducerMethod
	public static Game find(long id)  throws SQLException, MapperException {
		try {
			return IdentityMap.get(id, Game.class);
		} catch (DomainObjectNotFoundException e) {
		}
		ResultSet rs = GameFinder.findById(id);
		if(!rs.next()) throw new MapperException("Game with id " + id + " does not exist.");
		return getGame(rs);
	}
	
	public static Game findById(String id)  throws SQLException, MapperException {
		ResultSet rs = GameFinder.findById(id);
		if(!rs.next()) throw new MapperException("Game with id " + id + " does not exist.");
		
		try {
			return IdentityMap.get(rs.getLong("id"), Game.class);
		} catch (DomainObjectNotFoundException e) {
		}
		return getGame(rs);
	}
	
	/**
	 * This map function matches on both username AND password. It is commonly used to verify authentication.
	 */

	public static Game findByPlayer(long playerId) throws SQLException, MapperException {
		ResultSet rs = GameFinder.findByPlayer(playerId);
		if(!rs.next()) throw new MapperException("Game with that Gamename/Password doesn't exist!");
		
		try {
			return IdentityMap.get(rs.getLong("id"), Game.class);
		} catch (DomainObjectNotFoundException e) {
		}
		return getGame(rs);
		
	}

	private static Game getGame(ResultSet rs) throws SQLException, MapperException {
		return GameFactory.createClean(rs.getLong("id"), 
				rs.getLong("version"), 
				rs.getLong("challengerid"), rs.getLong("challengeeid"));
	}

}

