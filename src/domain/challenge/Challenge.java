package domain.challenge;

import org.dsrg.soenea.domain.DomainObject;

import domain.user.User;

public class Challenge  extends DomainObject<Long> implements IChallenge{
	
	private User challenger;
	private User challengee;
	private int status;
	
	public Challenge(User challenger, User challengee, int status) {
		this(0, 1, challenger, challengee, status);
	}

	public Challenge(long id, User challenger, User challengee, int status) {
		this(id, 1, challenger, challengee, status);
	}
	
	public Challenge(long id, long version, User challenger, User challengee, int status) {
		super(id, version);
		this.challenger = challenger;
		this.challengee = challengee;
		this.status = status;
	}

	public User getChallenger() {
		return challenger;
	}
	
	public void setChallenger(User challenger) {
		this.challenger = challenger;
	}
	
	public User getChallengee() {
		return challengee;
	}
	
	public void setChallengee(User challengee) {
		this.challengee = challengee;
	}
	
	public int getStatus() {
		return status;
	}
	
	public void setStatus(int status) {
		this.status = status;
	}

}
