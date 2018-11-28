package domain;

public class Status {

	private long gameId;
	private long playerId;
	private int status;
	
	public Status(long gameId, long playerId, int status) {
		this.gameId = gameId;
		this.playerId = playerId;
		this.status = status;
	}
	
	public Status(long gameId, long playerId) {
		this.gameId = gameId;
		this.playerId = playerId;
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
	
	public int getStatus() {
		return status;
	}
	
	public void setStatus(int status) {
		this.status = status;
	}
	
}
