package domain.hand;

import java.util.List;

import domain.card.ICard;
import domain.game.Game;
import domain.user.User;

public interface IHand {
	
	public Game getGame();
	public void setGame(Game game);
	public User getPlayer();
	public void setPlayer(User player);
	public List<ICard> getCards();
	public void setCards(List<ICard> cards);

}
