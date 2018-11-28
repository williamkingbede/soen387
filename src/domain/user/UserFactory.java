package domain.user;

import java.sql.SQLException;

import org.dsrg.soenea.domain.MapperException;
import domain.user.User;
import org.dsrg.soenea.uow.UoW;

public class UserFactory {
	
	/*public static  User createNew(String username, String password) throws SQLException, MapperException{
		return createNew(1, username, password);
	}*/
	
	public static  User createNew(String username, String password) throws SQLException, MapperException{
		User u = new User( username);
		u.setPassword(password);
		//UoW.getCurrent().registerNew(u);
		return u;
	}
	
	public static User createClean(long id, long version, String username) {
		User u = new User(id, version, username);
		//UoW.getCurrent().registerClean(u);
		return u;
	}

}
