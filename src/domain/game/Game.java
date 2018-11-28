package domain.game;

import org.dsrg.soenea.domain.DomainObject;

import domain.user.User;

public class Game extends DomainObject<Long> implements IGame{
	
	private User challenger;
	private User challengee;
	
	public Game(User challenger, User challengee) {
		this(0, 0, challenger, challengee);
	}
	
	public Game(long id, User challenger, User challengee) {
		this(id, 1, challenger, challengee);
	}

	public Game(long id, long version, User challenger, User challengee) {
		super(id, version);
		this.challenger = challenger;
		this.challengee = challengee;
	}

	@Override
	public User getChallenger() {
		return this.challenger;
	}

	@Override
	public User getChallengee() {
		return this.challengee;
	}

	@Override
	public void setChallenger(User challenger) {
		this.challenger = challenger;
	}

	@Override
	public void setChallengee(User challengee) {
		this.challengee = challengee;
	}
	
}
