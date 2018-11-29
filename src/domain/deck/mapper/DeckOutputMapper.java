package domain.deck.mapper;

import java.sql.SQLException;

import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.domain.mapper.LostUpdateException;

import domain.deck.Deck;
import domain.mapper.GenericOutputMapper;
import service.tdg.DeckTDG;

public class DeckOutputMapper extends GenericOutputMapper<Long, Deck> {
	
	public void delete(Deck d) throws MapperException {
		try {
			int count = DeckTDG.delete(d.getId(), d.getVersion());
			if(count == 0) throw new LostUpdateException("When trying to delete, Deck with version: " + d.getVersion() + " and id: " + d.getId() + " was not found.");
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static long insert(Deck d) throws MapperException {
		long id = 0;
		
		try {
			id = DeckTDG.insert(d.getVersion(), d.getPlayer().getId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return id;
	}

	public void update(Deck d) throws MapperException {
		try {
			int count = 0;
			count = DeckTDG.update(d.getId(), d.getVersion(), d.getPlayer().getId());
			if(count == 0) throw new LostUpdateException("When trying to update, Deck with version: " + d.getVersion() + " and id: " + d.getId() + " was not foubd.");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
