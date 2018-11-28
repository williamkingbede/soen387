package domain;

import java.util.ArrayList;
import java.util.List;

public class Deck {
	
	private long id;
	private long playerId;
	private List<Card> cards;
	
	public Deck(long id, long playerId, List<Card> cards) {
		this.id = id;
		this.playerId = playerId;
		this.cards = cards;
	}
	
	public Deck(long id, long playerId) {
		this.id = id;
		this.playerId = playerId;
		this.cards = new ArrayList<Card>();
	}
	
	public Deck(long playerId, List<Card> cards) {
		this.playerId = playerId;
		this.cards = cards;
	}
	
	public Deck(long playerId) {
		this.playerId = playerId;
		this.cards = new ArrayList<Card>();
	}

	public long getId() {
		return id;
	}
	
	public long getPlayerId() {
		return playerId;
	}

	public void setPlayerId(long playerId) {
		this.playerId = playerId;
	}

	public List<Card> getCards() {
		return cards;
	}
	
	public void setCards(List<Card> cards) {
		this.cards = cards;
	}
	
	public int decksize() {
		return cards.size();
	}

}
