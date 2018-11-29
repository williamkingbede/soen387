package domain.deck;

import java.sql.SQLException;
import java.util.List;

import org.dsrg.soenea.domain.DomainObjectCreationException;
import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.domain.proxy.DomainObjectProxy;

import domain.card.Card;
import domain.card.ICard;
import domain.deck.mapper.DeckInputMapper;
import domain.user.User;

public class DeckProxy extends DomainObjectProxy<Long, Deck> implements IDeck{
	
	public DeckProxy(Long id) {
		super(id);
	}

	@Override
	protected Deck getFromMapper(Long id) throws MapperException,
			DomainObjectCreationException {
		try {
			return DeckInputMapper.find(getId());
		} catch (SQLException e) {
			throw new MapperException(e);
		}
	}

	public User getPlayer() {
		return getInnerObject().getPlayer();
	}

	public void setPlayer(User player) {
		getInnerObject().setPlayer(player);
	}
	
	public List<ICard> getCards() {
		return getInnerObject().getCards();
	}

	public void setCards(List<ICard> cards) {
		getInnerObject().setCards(cards);
	}
	
	public boolean equals(Object obj) {
		if (obj instanceof DeckProxy) return getId().equals(((DeckProxy)obj).getId());
		if (obj instanceof Deck) return getId().equals(((Deck)obj).getId());
		return false;		
	}

}
