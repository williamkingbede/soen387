package domain.card;

import org.dsrg.soenea.domain.DomainObject;

import domain.deck.Deck;
import domain.user.User;

public class Card extends DomainObject<Long> implements ICard{
	
	private Deck deck;
	private String type;
	private String name;
	
	public Card(Deck deck, String type, String name) {
		this(0, 1, deck, type, name);
	}

	public Card(long id,Deck deck, String type, String name) {
		this(id, 1, deck, type, name);
	}
	
	public Card(long id, long version, Deck deck, String type, String name) {
		super(id, version);
		this.deck = deck;
		this.type = type;
		this.name = name;
	}
	
	public Deck getDeck() {
		return deck;
	}
	
	public void setDeck(Deck deck) {
		this.deck = deck;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
}
