package domain.deck;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dsrg.soenea.domain.MapperException;

import domain.card.Card;
import domain.card.ICard;
import domain.card.mapper.CardInputMapper;
import domain.user.User;
import domain.user.mapper.UserInputMapper;

public class DeckFactory {

	public static  Deck createNew(User player, List<ICard> cards) throws SQLException, MapperException{
		Deck d = new Deck(player);
		if(cards == null) d.setCards(new ArrayList<ICard>());
		else d.setCards(cards);
		//UoW.getCurrent().registerNew(g);
		return d;
	}
	
	public static Deck createClean(long id, long version, long playerId) throws SQLException, MapperException {
		User player = UserInputMapper.find(playerId);
		Deck d = new Deck(id, version, player);
		List<ICard> cards = CardInputMapper.findByDeck(id);
		d.setCards(cards);
		//UoW.getCurrent().registerClean(g);
		return d;
	}
	
}
