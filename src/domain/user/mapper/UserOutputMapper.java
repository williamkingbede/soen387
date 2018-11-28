package domain.user.mapper;

import java.sql.SQLException;

import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.domain.mapper.LostUpdateException;

import domain.mapper.GenericOutputMapper;
import domain.user.User;
import service.tdg.UserTDG;

public class UserOutputMapper extends GenericOutputMapper<Long, User> {
	
	public void delete(User d) throws MapperException {
		try {
			int count = UserTDG.delete(d.getId(), d.getVersion());
			if(count == 0) throw new LostUpdateException("When trying to delete, User with version: " + d.getVersion() + " and id: " + d.getId() + " was not found.");
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static long insert(User d) throws MapperException {
		long id = 0;
		
		try {
			id = UserTDG.insert(d.getVersion(), d.getUsername(), d.getPassword());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return id;
	}

	public void update(User d) throws MapperException {
		try {
			int count = 0;
			if(d.hasChangedPassword()) {
				count = UserTDG.update(d.getId(), d.getVersion(), d.getUsername(), d.getPassword());
			} else {
				count = UserTDG.update(d.getId(), d.getVersion(), d.getUsername());
			}
			if(count == 0) throw new LostUpdateException("When trying to update, User with version: " + d.getVersion() + " and id: " + d.getId() + " was not foubd.");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
