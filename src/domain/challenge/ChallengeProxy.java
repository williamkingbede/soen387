package domain.challenge;

import java.sql.SQLException;

import org.dsrg.soenea.domain.DomainObjectCreationException;
import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.domain.proxy.DomainObjectProxy;

import domain.challenge.mapper.ChallengeInputMapper;
import domain.user.User;

public class ChallengeProxy extends DomainObjectProxy<Long, Challenge> implements IChallenge{
	
	public ChallengeProxy(Long id) {
		super(id);
	}

	@Override
	protected Challenge getFromMapper(Long id) throws MapperException,
			DomainObjectCreationException {
		try {
			return ChallengeInputMapper.find(getId());
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
	
	public int getStatus() {
		return getInnerObject().getStatus();
	}

	public void setStatus(int status) {
		getInnerObject().setStatus(status);
	}
	
	public boolean equals(Object obj) {
		if (obj instanceof ChallengeProxy) return getId().equals(((ChallengeProxy)obj).getId());
		if (obj instanceof Challenge) return getId().equals(((Challenge)obj).getId());
		return false;		
	}

}
