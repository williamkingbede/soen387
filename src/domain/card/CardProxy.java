package domain.card;

import java.sql.SQLException;
import java.util.List;

import org.dsrg.soenea.domain.DomainObjectCreationException;
import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.domain.proxy.DomainObjectProxy;

import domain.card.Card;
import domain.card.mapper.CardInputMapper;
import domain.deck.Deck;
import domain.deck.DeckProxy;
import domain.user.User;


public class CardProxy extends DomainObjectProxy<Long, Card> implements ICard{

	public CardProxy(Long id) {
		super(id);
	}

	protected Card getFromMapper(Long id) throws MapperException, DomainObjectCreationException {
		try {
			return CardInputMapper.findDO(getId());
		} catch (SQLException e) {
			throw new MapperException(e);
		}
	}

	public Deck getDeck() {
		return getInnerObject().getDeck();
	}

	@Override
	public void setDeck(Deck deck) {
		getInnerObject().setDeck(deck);
	}

	@Override
	public String getType() {
		return getInnerObject().getType();
	}

	@Override
	public void setType(String type) {
		getInnerObject().setType(type);
	}

	@Override
	public String getName() {
		return getInnerObject().getName();
	}

	@Override
	public void setName(String name) {
		getInnerObject().setName(name);
	}
	
	public boolean equals(Object obj) {
		if (obj instanceof DeckProxy) return getId().equals(((DeckProxy)obj).getId());
		if (obj instanceof Deck) return getId().equals(((Deck)obj).getId());
		return false;		
	}

}
