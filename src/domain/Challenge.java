package domain;

public class Challenge {
	
	private long id;
	private long challengerId;
	private long challengeeId;
	private int status;
	
	public Challenge(long id, long challengerId, long challengeeId, int status) {
		this.id = id;
		this.challengerId = challengerId;
		this.challengeeId = challengeeId;
		this.status = status;
	}
	
	public Challenge(long challengerId, long challengeeId, int status) {
		super();
		this.challengerId = challengerId;
		this.challengeeId = challengeeId;
		this.status = status;
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
	
	public int getStatus() {
		return status;
	}
	
	public void setStatus(int status) {
		this.status = status;
	}

}
