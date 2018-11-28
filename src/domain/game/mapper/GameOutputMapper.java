package domain.game.mapper;

import java.sql.SQLException;

import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.domain.mapper.LostUpdateException;

import domain.game.Game;
import domain.mapper.GenericOutputMapper;
import service.tdg.GameTDG;

public class GameOutputMapper extends GenericOutputMapper<Long, Game> {
	
	public void delete(Game d) throws MapperException {
		try {
			int count = GameTDG.delete(d.getId(), d.getVersion());
			if(count == 0) throw new LostUpdateException("When trying to delete, Game with version: " + d.getVersion() + " and id: " + d.getId() + " was not found.");
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static long insert(Game d) throws MapperException {
		long id = 0;
		
		try {
			id = GameTDG.insert(d.getVersion(), d.getChallenger().getId(), d.getChallengee().getId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return id;
	}

	public void update(Game d) throws MapperException {
		try {
			int count = 0;
			count = GameTDG.update(d.getId(), d.getVersion(), d.getChallenger().getId(), d.getChallengee().getId());
			if(count == 0) throw new LostUpdateException("When trying to update, Game with version: " + d.getVersion() + " and id: " + d.getId() + " was not foubd.");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
