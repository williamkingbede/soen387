package domain.bench;

import java.util.ArrayList;
import java.util.List;

import org.dsrg.soenea.domain.DomainObject;

import domain.card.ICard;
import domain.game.Game;
import domain.user.User;

public class Bench extends DomainObject<Long> implements IBench{
	
	private Game game;
	private User player;
	private List<ICard> cards;
	
	public Bench(Game game, User player) {
		this(game, player, 1);
	}
	
	public Bench(Game game, User player, long version) {
		super((Long) null, version);
		this.player = player;
		this.cards = new ArrayList<ICard>();
	}

	public Bench(Game game, User player, List<ICard> cards, long version) {
		super((Long) null, version);
		this.player = player;
		this.cards = cards;
	}
	
	public Game getGame() {
		return game;
	}
	
	public void setGame(Game game) {
		this.game = game;
	}
	
	public User getPlayer() {
		return player;
	}
	
	public void setPlayer(User player) {
		this.player = player;
	}
	
	public List<ICard> getCards() {
		return cards;
	}
	
	public void setCards(List<ICard> cards) {
		this.cards = cards;
	}
	
	

}
