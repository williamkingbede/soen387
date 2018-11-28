package domain;

import java.util.ArrayList;
import java.util.List;

public class Bench {
	
	private long gameId;
	private long playerId;
	private List<Card> cards;
	

	public Bench(long gameId, long playerId, List<Card> cards) {
		this.gameId = gameId;
		this.playerId = playerId;
		this.cards = cards;
	}
	
	public Bench(long gameId, long playerId) {
		this.gameId = gameId;
		this.playerId = playerId;
		this.cards = new ArrayList<Card>();
	}

	public long getGameId() {
		return gameId;
	}

	public void setGameId(long gameId) {
		this.gameId = gameId;
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

}
