package domain.deck;

import java.util.ArrayList;
import java.util.List;

import org.dsrg.soenea.domain.DomainObject;

import domain.card.ICard;
import domain.user.User;

public class Deck extends DomainObject<Long> implements IDeck{
	
	private User player;
	private List<ICard> cards;
	
	public Deck(User player) {
		this(0, 1, player);
	}
	
	public Deck(long id, User player) {
		this(id, 1, player);
	}
	
	public Deck(User player, List<ICard> cards) {
		this(0, 1, player, cards);
	}
	
	public Deck(long id, User player, List<ICard> cards) {
		this(id, 1, player, cards);
	}
	
	public Deck(long id, long version, User player) {
		super(id, version);
		this.player = player;
		this.cards = new ArrayList<ICard>();
	}

	public Deck(long id, long version, User player, List<ICard> cards) {
		super(id, version);
		this.player = player;
		this.cards = cards;
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
