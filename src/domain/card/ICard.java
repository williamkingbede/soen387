package domain.card;

import org.dsrg.soenea.domain.interf.IDomainObject;

import domain.deck.Deck;


public interface ICard extends IDomainObject<Long>{
	
	public Deck getDeck();
	public void setDeck(Deck deck);
	public String getType();
	public void setType(String type);
	public String getName();
	public void setName(String name);
}
