package domain.card.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.domain.mapper.DomainObjectNotFoundException;
import org.dsrg.soenea.domain.mapper.IdentityMap;
import org.dsrg.soenea.domain.producer.IdentityBasedProducer;
import org.dsrg.soenea.domain.producer.IdentityBasedProducerMethod;

import domain.card.Card;
import domain.card.CardFactory;
import domain.card.CardProxy;
import domain.card.ICard;
import service.tdg.finder.CardFinder;

public class CardInputMapper implements IdentityBasedProducer{
	
	public static List<ICard> findByDeck(long deck) throws SQLException, MapperException {
		ArrayList<ICard> cards = new ArrayList<ICard>();
		
		ResultSet rs = CardFinder.findByDeck(deck);
		while(rs.next()) {
			try {
				cards.add(IdentityMap.get(rs.getLong("id"), Card.class));
				continue;
			} catch (DomainObjectNotFoundException e) {	}
			cards.add(new CardProxy(rs.getLong("id")));
		}
		
		return cards;
	}

	@IdentityBasedProducerMethod
	public static ICard find(long id)  throws SQLException, MapperException {
		try {
			return IdentityMap.get(id, Card.class);
		} catch (DomainObjectNotFoundException e) {
		}
		ResultSet rs = CardFinder.findById(id);
		if(!rs.next()) throw new MapperException("Card with id " + id + " does not exist.");
		return getCard(rs);
	}
	
	public static Card findDO(long id)  throws SQLException, MapperException {
		try {
			return IdentityMap.get(id, Card.class);
		} catch (DomainObjectNotFoundException e) {
		}
		ResultSet rs = CardFinder.findById(id);
		if(!rs.next()) throw new MapperException("Card with id " + id + " does not exist.");
		return getCardDO(rs);
	}
	
	public static ICard find(String id)  throws SQLException, MapperException {
		ResultSet rs = CardFinder.findById(id);
		if(!rs.next()) throw new MapperException("Card with id " + id + " does not exist.");
		
		try {
			return IdentityMap.get(rs.getLong("id"), Card.class);
		} catch (DomainObjectNotFoundException e) {
		}
		return getCard(rs);
	}

	private static ICard getCard(ResultSet rs) throws SQLException, MapperException {
		return CardFactory.createClean(rs.getLong("id"), 
				rs.getLong("version"), 
				rs.getLong("deckid"), rs.getString("type"),rs.getString("name"));
	}
	
	private static Card getCardDO(ResultSet rs) throws SQLException, MapperException {
		return CardFactory.createClean(rs.getLong("id"), 
				rs.getLong("version"), 
				rs.getLong("deckid"), rs.getString("type"),rs.getString("name"));
	}

}
