package domain.hand.mapper;

import java.sql.SQLException;

import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.domain.mapper.LostUpdateException;

import domain.hand.Hand;
import domain.mapper.GenericOutputMapper;
import service.tdg.HandTDG;

public class HandOutputMapper extends GenericOutputMapper<Long, Hand> {
	
	public void delete(Hand d) throws MapperException {
		try {
			int count = HandTDG.delete(d.getGame().getId(), d.getPlayer().getId(), d.getVersion());
			if(count == 0) throw new LostUpdateException("When trying to delete, Hand with version: " + d.getVersion() + " and id: " + d.getId() + " was not found.");
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static long insert(Hand d) throws MapperException {
		long id = 0;
		
		try {
			id = HandTDG.insert(d.getGame().getId(), d.getPlayer().getId(), d.getVersion());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return id;
	}
	
	public static long insert(Hand d, long cardid) throws MapperException {
		long id = 0;
		
		try {
			id = HandTDG.insert(d.getGame().getId(), d.getPlayer().getId(), cardid, d.getVersion());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return id;
	}
	
	public void update(Hand d) throws MapperException {
		try {
			int count = 0;
			count = HandTDG.update(d.getGame().getId(), d.getPlayer().getId(), d.getVersion());
			if(count == 0) throw new LostUpdateException("When trying to update, Hand with version: " + d.getVersion() + " and id: " + d.getId() + " was not foubd.");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void update(Hand d, long cardid) throws MapperException {
		try {
			int count = 0;
			count = HandTDG.update(d.getGame().getId(), d.getPlayer().getId(), cardid, d.getVersion());
			if(count == 0) throw new LostUpdateException("When trying to update, Hand with version: " + d.getVersion() + " and id: " + d.getId() + " was not foubd.");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


}
