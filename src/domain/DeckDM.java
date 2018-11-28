package domain;

import java.sql.SQLException;

import data.DeckRDG;

public class DeckDM {

	public static long insert(Deck deck) throws SQLException {
		DeckRDG deckRdg = new DeckRDG(deck.getId(), deck.getPlayerId());
		return deckRdg.insert();
	}
	
	public static void update(Deck deck) throws SQLException {
		DeckRDG deckRdg = new DeckRDG(deck.getId(), deck.getPlayerId());
		deckRdg.update();
	}
	
	public static void delete(Deck deck) throws SQLException {
		DeckRDG deckRdg = new DeckRDG(deck.getId(), deck.getPlayerId());
		deckRdg.delete();
	}
	
	public static Deck findById(long id) throws SQLException {
		DeckRDG deckRdg = DeckRDG.findById(id);
		if(deckRdg != null) {
			Deck deck = new Deck(deckRdg.getId(), deckRdg.getPlayerId());
			return deck;
		}
		else 
			return null;
	}
	
	public static Deck findById(String id) throws SQLException {
		DeckRDG deckRdg = DeckRDG.findById(id);
		if(deckRdg != null) {
			Deck deck = new Deck(deckRdg.getId(), deckRdg.getPlayerId());
			return deck;
		}
		else 
			return null;
	}
	
	public static Deck findByPlayer(long playerid) throws SQLException {
		DeckRDG deckRdg = DeckRDG.findByPlayer(playerid);
		if(deckRdg != null) {
			Deck deck = new Deck(deckRdg.getId(), deckRdg.getPlayerId());
			return deck;
		}
		else 
			return null;
	}
	
}
