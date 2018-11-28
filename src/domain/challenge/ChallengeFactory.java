package domain.challenge;

import java.sql.SQLException;

import org.dsrg.soenea.domain.MapperException;

import domain.user.User;
import domain.user.mapper.UserInputMapper;

public class ChallengeFactory {
	
	/*public static  Challenge createNew(User challenger, User challengee, int status) throws SQLException, MapperException{
		return createNew(1, challenger, challengee, status);
	}*/
	
	public static  Challenge createNew(User challenger, User challengee, int status) throws SQLException, MapperException{
		Challenge c = new Challenge(challenger, challengee, status);
		//UoW.getCurrent().registerNew(g);
		return c;
	}
	
	public static Challenge createClean(long id, long version, long challengerId, long challengeeId, int status) throws SQLException, MapperException {
		User challenger = UserInputMapper.find(challengerId);
		User challengee = UserInputMapper.find(challengeeId);
		Challenge c = new Challenge(id, version, challenger, challengee, status);
		//UoW.getCurrent().registerClean(g);
		return c;
	}

}
