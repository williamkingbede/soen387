package domain.game;

import domain.user.User;

public interface IGame {

	public User getChallenger();
	public User getChallengee();
	public void setChallenger(User challenger);
	public void setChallengee(User challengee);
	
}
