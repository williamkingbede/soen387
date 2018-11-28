package domain.challenge;

import org.dsrg.soenea.domain.interf.IDomainObject;

import domain.user.User;

public interface IChallenge extends IDomainObject<Long>{

	public User getChallenger();
	public void setChallenger(User challenger);
	public User getChallengee();
	public void setChallengee(User challengee);
	public int getStatus();
	public void setStatus(int status);
	
}
