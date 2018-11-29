package domain.card;

import java.sql.SQLException;
import java.util.List;

import org.dsrg.soenea.domain.MapperException;

import domain.deck.Deck;
import domain.deck.mapper.DeckInputMapper;


public class CardFactory {
	
	public static Card createNew(long deckId, String type, String name) throws SQLException, MapperException{
		Deck deck = DeckInputMapper.find(deckId);
		Card d = new Card(deck, type, name);
		//UoW.getCurrent().registerNew(g);
		return d;
	}
	
	public static Card createClean(long id, long version, long deckId, String type, String name) throws SQLException, MapperException {
		Deck deck = DeckInputMapper.find(deckId);
		Card d = new Card(id, version, deck, type, name);
		//UoW.getCurrent().registerClean(g);
		return d;
	}

}
