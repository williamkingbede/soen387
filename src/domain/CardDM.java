package domain;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import data.CardRDG;

public class CardDM {

	public static long insert(Card card) throws SQLException {
		CardRDG cardRdg = new CardRDG(card.getId(), card.getDeckId(), card.getType(), card.getName());
		return cardRdg.insert();
	}
	
	public static void update(Card card) throws SQLException {
		CardRDG cardRdg = new CardRDG(card.getId(), card.getDeckId(), card.getType(), card.getName());
		cardRdg.update();
	}
	
	public static void update(Card card, long cardid) throws SQLException {
		CardRDG cardRdg = new CardRDG(card.getId(), card.getDeckId(), card.getType(), card.getName());
		cardRdg.update(cardid);
	}
	
	public static void updateSoftDelete(Card card) throws SQLException {
		CardRDG cardRdg = new CardRDG(card.getId(), card.getDeckId(), card.getType(), card.getName());
		cardRdg.updateSoftDelete();
	}
	
	public static void delete(Card card) throws SQLException {
		CardRDG cardRdg = new CardRDG(card.getId(), card.getDeckId(), card.getType(), card.getName());
		cardRdg.delete();
	}
	
	public static Card findById(long id) throws SQLException {
		CardRDG cardRdg = CardRDG.findById(id);
		if(cardRdg != null) {
			Card card = new Card(cardRdg.getId(), cardRdg.getDeckId(), cardRdg.getType(), cardRdg.getName());
			return card;
		}
		else 
			return null;
	}
	
	public static Card findById(String id) throws SQLException {
		CardRDG cardRdg = CardRDG.findById(id);
		if(cardRdg != null) {
			Card card = new Card(cardRdg.getId(), cardRdg.getDeckId(), cardRdg.getType(), cardRdg.getName());
			return card;
		}
		else 
			return null;
	}
	
	public static List<Card> findByDeck(long deckid) throws SQLException {
		List<CardRDG> cardsRdg = CardRDG.findByDeck(deckid);
		List<Card> cards = new ArrayList<Card>();
		if(!cardsRdg.isEmpty()) {
			for(CardRDG cardRdg : cardsRdg) {
				Card card = new Card(cardRdg.getId(), cardRdg.getDeckId(), cardRdg.getType(), cardRdg.getName());
				cards.add(card);	
			}
			return cards;
		}
		else
			return null;
	}
}
