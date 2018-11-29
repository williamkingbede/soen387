package domain.bench;

import java.sql.SQLException;
import java.util.List;

import org.dsrg.soenea.domain.DomainObjectCreationException;
import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.domain.proxy.DomainObjectProxy;

import domain.card.ICard;
import domain.game.Game;
import domain.user.User;

public class BenchProxy extends DomainObjectProxy<Long, Bench> implements IBench{

	public BenchProxy(Long id) {
		super(id);
	}

	@Override
	//Don't need this one 
	protected Bench getFromMapper(Long id) throws MapperException,
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
		if (obj instanceof BenchProxy) return getId().equals(((BenchProxy)obj).getId());
		if (obj instanceof Bench) return getId().equals(((Bench)obj).getId());
		return false;		
	}
	
}
