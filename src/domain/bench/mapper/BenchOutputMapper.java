package domain.bench.mapper;

import java.sql.SQLException;

import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.domain.mapper.LostUpdateException;

import domain.bench.Bench;
import domain.mapper.GenericOutputMapper;
import service.tdg.BenchTDG;

public class BenchOutputMapper extends GenericOutputMapper<Long, Bench> {
	
	public void delete(Bench d) throws MapperException {
		try {
			int count = BenchTDG.delete(d.getGame().getId(), d.getPlayer().getId(), d.getVersion());
			if(count == 0) throw new LostUpdateException("When trying to delete, Bench with version: " + d.getVersion() + " and id: " + d.getId() + " was not found.");
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static long insert(Bench d) throws MapperException {
		long id = 0;
		
		try {
			id = BenchTDG.insert(d.getGame().getId(), d.getPlayer().getId(), d.getVersion());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return id;
	}
	
	public static long insert(Bench d, long cardid) throws MapperException {
		long id = 0;
		
		try {
			id = BenchTDG.insert(d.getGame().getId(), d.getPlayer().getId(), cardid, d.getVersion());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return id;
	}
	
	public void update(Bench d) throws MapperException {
		try {
			int count = 0;
			count = BenchTDG.update(d.getGame().getId(), d.getPlayer().getId(), d.getVersion());
			if(count == 0) throw new LostUpdateException("When trying to update, Bench with version: " + d.getVersion() + " and id: " + d.getId() + " was not foubd.");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void update(Bench d, long cardid) throws MapperException {
		try {
			int count = 0;
			count = BenchTDG.update(d.getGame().getId(), d.getPlayer().getId(), cardid, d.getVersion());
			if(count == 0) throw new LostUpdateException("When trying to update, Bench with version: " + d.getVersion() + " and id: " + d.getId() + " was not foubd.");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


}
