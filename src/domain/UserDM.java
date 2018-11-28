package domain;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import data.UserRDG;

public class UserDM {
	
	public static long insert(User user) throws SQLException {
		UserRDG userRdg = new UserRDG(user.getId(), user.getUsername(), user.getPassword());
		return userRdg.insert();
	}
	
	public static void update(User user) throws SQLException {
		UserRDG userRdg = new UserRDG(user.getId(), user.getUsername(), user.getPassword());
		userRdg.update();
	}
	
	public static void delete(User user) throws SQLException {
		UserRDG userRdg = new UserRDG(user.getId(), user.getUsername(), user.getPassword());
		userRdg.delete();
	}
	
	public static User findById(long id) throws SQLException {
		UserRDG userRdg = UserRDG.findById(id);
		if(userRdg != null) {
			User user = new User(userRdg.getId(), userRdg.getUsername(), userRdg.getPassword());
			return user;
		}
		else 
			return null;
	}
	
	public static User findById(String id) throws SQLException {
		UserRDG userRdg = UserRDG.findById(id);
		if(userRdg != null) {
			User user = new User(userRdg.getId(), userRdg.getUsername(), userRdg.getPassword());
			return user;
		}
		else 
			return null;
	}
	
	public static User findByUsername(String username) throws SQLException {
		UserRDG userRdg = UserRDG.findByUsername(username);
		if(userRdg != null) {
			User user = new User(userRdg.getId(), userRdg.getUsername(), userRdg.getPassword());
			return user;
		}
		else 
			return null;
		
	}

	public static User findByUsernameAndPassword(String username, String password) throws SQLException {
		UserRDG userRdg = UserRDG.findByUsernameAndPassword(username, password);
		if(userRdg != null) {
			User user = new User(userRdg.getId(), userRdg.getUsername(), userRdg.getPassword());
			return user;
		}
		else 
			return null;
	}

	public static List<User> findAll() throws SQLException{
		List<UserRDG> usersRdg = UserRDG.findAll();
		List<User> users = new ArrayList<User>();
		if(!usersRdg.isEmpty()) {
			for(UserRDG userRdg : usersRdg) {
				User user = new User(userRdg.getId(), userRdg.getUsername(), userRdg.getPassword());
				users.add(user);	
			}
			return users;
		}
		else
			return null;
	}
	
}
