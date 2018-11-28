package domain.game;

import java.sql.SQLException;

import org.dsrg.soenea.domain.DomainObjectCreationException;
import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.domain.proxy.DomainObjectProxy;

import domain.game.mapper.GameInputMapper;
import domain.user.User;
import domain.user.mapper.UserInputMapper;

public class GameProxy extends DomainObjectProxy<Long, Game> implements IGame{
	
	public GameProxy(Long id) {
		super(id);
	}

	@Override
	protected Game getFromMapper(Long id) throws MapperException,
			DomainObjectCreationException {
		try {
			return GameInputMapper.find(getId());
		} catch (SQLException e) {
			throw new MapperException(e);
		}
	}

	public User getChallenger() {
		return getInnerObject().getChallenger();
	}

	public User getChallengee() {
		return getInnerObject().getChallengee();
	}

	public void setChallenger(User challenger) {
		getInnerObject().setChallenger(challenger);
	}

	public void setChallengee(User challengee) {
		getInnerObject().setChallengee(challengee);
	}
	
	public boolean equals(Object obj) {
		if (obj instanceof GameProxy) return getId().equals(((GameProxy)obj).getId());
		if (obj instanceof Game) return getId().equals(((Game)obj).getId());
		return false;		
	}

}
