package domain.challenge.mapper;

import java.sql.SQLException;

import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.domain.mapper.LostUpdateException;

import domain.challenge.Challenge;
import domain.mapper.GenericOutputMapper;
import service.tdg.ChallengeTDG;

public class ChallengeOutputMapper extends GenericOutputMapper<Long, Challenge> {
	
	public void delete(Challenge d) throws MapperException {
		try {
			int count = ChallengeTDG.delete(d.getId(), d.getVersion());
			if(count == 0) throw new LostUpdateException("When trying to delete, Challenge with version: " + d.getVersion() + " and id: " + d.getId() + " was not found.");
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static long insert(Challenge d) throws MapperException {
		long id = 0;
		
		try {
			id = ChallengeTDG.insert(d.getVersion(), d.getChallenger().getId(), d.getChallengee().getId(), d.getStatus());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return id;
	}

	public void update(Challenge d) throws MapperException {
		try {
			int count = 0;
			count = ChallengeTDG.update(d.getId(), d.getVersion(), d.getChallenger().getId(), d.getChallengee().getId(), d.getStatus());
			if(count == 0) throw new LostUpdateException("When trying to update, Challenge with version: " + d.getVersion() + " and id: " + d.getId() + " was not foubd.");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
