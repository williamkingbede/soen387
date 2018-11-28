package domain;

public class Game {
	
	private long id;
	private long challengerId;
	private long challengeeId;
	
	public Game(long id, long challengerId, long challengeeId) {
		this.id = id;
		this.challengerId = challengerId;
		this.challengeeId = challengeeId;
	}
	
	public Game(long challengerId, long challengeeId) {
		this.challengerId = challengerId;
		this.challengeeId = challengeeId;
	}

	public long getId() {
		return id;
	}

	public long getChallengerId() {
		return challengerId;
	}

	public void setChallengerId(long challengerId) {
		this.challengerId = challengerId;
	}

	public long getChallengeeId() {
		return challengeeId;
	}

	public void setChallengeeId(long challengeeId) {
		this.challengeeId = challengeeId;
	}

}
