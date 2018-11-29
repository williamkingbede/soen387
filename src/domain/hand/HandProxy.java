package domain.hand;

import java.util.List;

import org.dsrg.soenea.domain.DomainObjectCreationException;
import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.domain.proxy.DomainObjectProxy;

import domain.card.ICard;
import domain.game.Game;
import domain.user.User;

public class HandProxy extends DomainObjectProxy<Long, Hand> implements IHand{

	public HandProxy(Long id) {
		super(id);
	}

	@Override
	//Don't need this one 
	protected Hand getFromMapper(Long id) throws MapperException,
			DomainObjectCreationException {
	
			return null;
	}
	
	public Game getGame() {
		return getInnerObject().getGame();
	}

	public void setGame(Game game) {
		getInnerObject().setGame(game);
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
		if (obj instanceof HandProxy) return getId().equals(((HandProxy)obj).getId());
		if (obj instanceof Hand) return getId().equals(((Hand)obj).getId());
		return false;		
	}
	
}
