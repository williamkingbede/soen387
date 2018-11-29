package domain.game;

import org.dsrg.soenea.domain.interf.IDomainObject;

import domain.user.User;

public interface IGame extends IDomainObject<Long>{

	public User getChallenger();
	public User getChallengee();
	public void setChallenger(User challenger);
	public void setChallengee(User challengee);
	
}
