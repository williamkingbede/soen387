package domain.deck;

import java.util.List;

import org.dsrg.soenea.domain.interf.IDomainObject;

import domain.card.Card;
import domain.card.ICard;
import domain.user.User;

public interface IDeck extends IDomainObject<Long>{

	public User getPlayer();
	public void setPlayer(User player);
	public List<ICard> getCards();
	public void setCards(List<ICard> cards);
	
}
