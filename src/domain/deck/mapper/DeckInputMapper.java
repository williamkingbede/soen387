package domain.deck.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.domain.mapper.DomainObjectNotFoundException;
import org.dsrg.soenea.domain.mapper.IdentityMap;
import org.dsrg.soenea.domain.producer.IdentityBasedProducer;
import org.dsrg.soenea.domain.producer.IdentityBasedProducerMethod;

import domain.deck.Deck;
import domain.deck.DeckFactory;
import domain.deck.DeckProxy;
import domain.deck.IDeck;
import service.tdg.finder.DeckFinder;

public class DeckInputMapper  implements IdentityBasedProducer{
	
	/*public static List<IDeck> findAll() throws SQLException, MapperException {
		ArrayList<IDeck> games = new ArrayList<IDeck>();
		
		ResultSet rs = DeckFinder.findAll();
		while(rs.next()) {
			try {
				games.add(IdentityMap.get(rs.getLong("id"), Deck.class));
				continue;
			} catch (DomainObjectNotFoundException e) {	}
			games.add(new DeckProxy(rs.getLong("id")));
		}
		
		return games;
	}*/

	@IdentityBasedProducerMethod
	public static Deck find(long id)  throws SQLException, MapperException {
		try {
			return IdentityMap.get(id, Deck.class);
		} catch (DomainObjectNotFoundException e) {
		}
		ResultSet rs = DeckFinder.findById(id);
		if(!rs.next()) throw new MapperException("Deck with id " + id + " does not exist.");
		return getDeck(rs);
	}
	
	public static Deck find(String id)  throws SQLException, MapperException {
		ResultSet rs = DeckFinder.findById(id);
		if(!rs.next()) throw new MapperException("Deck with id " + id + " does not exist.");
		
		try {
			return IdentityMap.get(rs.getLong("id"), Deck.class);
		} catch (DomainObjectNotFoundException e) {
		}
		return getDeck(rs);
	}

	public static Deck findByPlayer(long playerId) throws SQLException, MapperException {
		ResultSet rs = DeckFinder.findByPlayer(playerId);
		if(!rs.next()) throw new MapperException("Deck with player id " + playerId + " doesn't exist!");
		
		try {
			return IdentityMap.get(rs.getLong("id"), Deck.class);
		} catch (DomainObjectNotFoundException e) {
		}
		return getDeck(rs);
		
	}

	private static Deck getDeck(ResultSet rs) throws SQLException, MapperException {
		return DeckFactory.createClean(rs.getLong("id"), 
				rs.getLong("version"), 
				rs.getLong("playerId"));
	}

}
