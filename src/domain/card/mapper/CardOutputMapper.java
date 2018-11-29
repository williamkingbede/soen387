package domain.card.mapper;

import java.sql.SQLException;

import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.domain.mapper.LostUpdateException;

import domain.card.Card;
import domain.mapper.GenericOutputMapper;
import service.tdg.CardTDG;

public class CardOutputMapper extends GenericOutputMapper<Long, Card> {
	
	public void delete(Card d) throws MapperException {
		try {
			int count = CardTDG.delete(d.getId(), d.getVersion());
			if(count == 0) throw new LostUpdateException("When trying to delete, Card with version: " + d.getVersion() + " and id: " + d.getId() + " was not found.");
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static long insert(Card d) throws MapperException {
		long id = 0;
		
		try {
			id = CardTDG.insert(d.getVersion(), d.getDeck().getId(), d.getType(), d.getName());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return id;
	}

	public void update(Card d) throws MapperException {
		try {
			int count = 0;
			count = CardTDG.update(d.getId(), d.getVersion(), d.getDeck().getId(), d.getType(), d.getName());
			if(count == 0) throw new LostUpdateException("When trying to update, Card with version: " + d.getVersion() + " and id: " + d.getId() + " was not foubd.");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
