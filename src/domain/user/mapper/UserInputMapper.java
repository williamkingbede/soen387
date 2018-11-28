package domain.user.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.domain.mapper.DomainObjectNotFoundException;
import org.dsrg.soenea.domain.mapper.IdentityMap;
import org.dsrg.soenea.domain.producer.IdentityBasedProducer;
import org.dsrg.soenea.domain.producer.IdentityBasedProducerMethod;

import domain.user.User;
import domain.user.UserFactory;
import domain.user.IUser;
import domain.user.UserProxy;
import service.tdg.finder.UserFinder;


public class UserInputMapper implements IdentityBasedProducer{
	
	public static List<IUser> findAll() throws SQLException, MapperException {
		ArrayList<IUser> users = new ArrayList<IUser>();
		
		ResultSet rs = UserFinder.findAll();
		while(rs.next()) {
			try {
				users.add(IdentityMap.get(rs.getLong("id"), User.class));
				continue;
			} catch (DomainObjectNotFoundException e) {	}
			users.add(new UserProxy(rs.getLong("id")));
		}
		
		return users;
	}

	@IdentityBasedProducerMethod
	public static User find(long id)  throws SQLException, MapperException {
		try {
			return IdentityMap.get(id, User.class);
		} catch (DomainObjectNotFoundException e) {
		}
		ResultSet rs = UserFinder.findById(id);
		if(!rs.next()) throw new MapperException("User with id " + id + " does not exist.");
		return getUser(rs);
	}
	
	public static User findById(String id) throws SQLException, MapperException {
		ResultSet rs = UserFinder.findById(id);
		if(!rs.next()) throw new MapperException("User with id " + id + " does not exist.");
		
		try {
			return IdentityMap.get(rs.getLong("id"), User.class);
		} catch (DomainObjectNotFoundException e) {
		}
		return getUser(rs);
		
	}

	public static User find(String username, String password) throws SQLException, MapperException {
		ResultSet rs = UserFinder.findByUsernameAndPassword(username, password);
		if(!rs.next()) throw new MapperException("User with that Username/Password doesn't exist!");
		
		try {
			return IdentityMap.get(rs.getLong("id"), User.class);
		} catch (DomainObjectNotFoundException e) {
		}
		return getUser(rs);
		
	}
	
	public static User find(String username) throws SQLException, MapperException {
		ResultSet rs = UserFinder.findByUsername(username);
		if(!rs.next()) throw new MapperException("User with that Username doesn't exist!");
		
		try {
			return IdentityMap.get(rs.getLong("id"), User.class);
		} catch (DomainObjectNotFoundException e) {
		}
		
		return getUser(rs);
		
	}	

	private static User getUser(ResultSet rs) throws SQLException {
		return UserFactory.createClean(rs.getLong("id"), 
				rs.getLong("version"), 
				rs.getString("username").trim());
	}

}
