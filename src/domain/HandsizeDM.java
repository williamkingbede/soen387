package domain;

import java.sql.SQLException;
import java.util.List;

import data.HandsizeRDG;

public class HandsizeDM {

	public static void insert(Handsize handsize) throws SQLException {
		HandsizeRDG handsizeRdg = new HandsizeRDG(handsize.getGameId(), handsize.getPlayerId());
		handsizeRdg.insert();
	}
	
	public static void insert(Handsize handsize, long cardid) throws SQLException {
		HandsizeRDG handsizeRdg = new HandsizeRDG(handsize.getGameId(), handsize.getPlayerId());
		handsizeRdg.insert(cardid);
	}
	
	public static void update(Handsize handsize, long cardid) throws SQLException {
		HandsizeRDG handsizeRdg = new HandsizeRDG(handsize.getGameId(), handsize.getPlayerId());
		handsizeRdg.update(cardid);
	}
	
	public static void delete(Handsize handsize) throws SQLException {
		HandsizeRDG handsizeRdg = new HandsizeRDG(handsize.getGameId(), handsize.getPlayerId());
		handsizeRdg.delete();
	}
	
	public static List<Card> getCardsInHand(long gameId, long playerId) throws SQLException {
		List<Card> cardsInHand = HandsizeRDG.getCardsInHand(gameId, playerId);
		return cardsInHand;
	}
	
	public static List<Card> getCardsInHand(String gameId, String playerId) throws SQLException {
		List<Card> cardsInHand = HandsizeRDG.getCardsInHand(gameId, playerId);
		return cardsInHand;
	}
	
}
