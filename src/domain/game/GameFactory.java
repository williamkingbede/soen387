package domain.game;

import java.sql.SQLException;

import org.dsrg.soenea.domain.MapperException;


import domain.user.User;
import domain.user.mapper.UserInputMapper;

public class GameFactory {
	
	/*public static  Game createNew(User challenger, User challengee) throws SQLException, MapperException{
		return createNew(challenger, challengee);
	}*/
	
	public static  Game createNew(User challenger, User challengee) throws SQLException, MapperException{
		Game g = new Game(challenger, challengee);
		//UoW.getCurrent().registerNew(g);
		return g;
	}
	
	public static Game createClean(long id, long version, long challengerId, long challengeeId) throws SQLException, MapperException {
		User challenger = UserInputMapper.find(challengerId);
		User challengee = UserInputMapper.find(challengeeId);
		Game g = new Game(id, version, challenger, challengee);
		//UoW.getCurrent().registerClean(g);
		return g;
	}

}
