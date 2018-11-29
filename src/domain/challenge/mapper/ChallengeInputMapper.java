package domain.challenge.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.domain.mapper.DomainObjectNotFoundException;
import org.dsrg.soenea.domain.mapper.IdentityMap;
import org.dsrg.soenea.domain.producer.IdentityBasedProducer;
import org.dsrg.soenea.domain.producer.IdentityBasedProducerMethod;

import domain.challenge.Challenge;
import domain.challenge.ChallengeFactory;
import domain.challenge.ChallengeProxy;
import domain.challenge.IChallenge;
import service.tdg.finder.ChallengeFinder;



public class ChallengeInputMapper implements IdentityBasedProducer{
	
	public static List<IChallenge> findAll() throws SQLException, MapperException {
		ArrayList<IChallenge> challenges = new ArrayList<IChallenge>();
		
		ResultSet rs = ChallengeFinder.findAll();
		while(rs.next()) {
			try {
				challenges.add(IdentityMap.get(rs.getLong("id"), Challenge.class));
				continue;
			} catch (DomainObjectNotFoundException e) {	}
			challenges.add(new ChallengeProxy(rs.getLong("id")));
		}
		
		return challenges;
	}
	
	public static List<IChallenge> findAllOpen() throws SQLException, MapperException {
		ArrayList<IChallenge> challenges = new ArrayList<IChallenge>();
		
		ResultSet rs = ChallengeFinder.findAllOpen();
		while(rs.next()) {
			try {
				challenges.add(IdentityMap.get(rs.getLong("id"), Challenge.class));
				continue;
			} catch (DomainObjectNotFoundException e) {	}
			challenges.add(new ChallengeProxy(rs.getLong("id")));
		}
		
		return challenges;
	}

	@IdentityBasedProducerMethod
	public static Challenge find(long id)  throws SQLException, MapperException {
		try {
			return IdentityMap.get(id, Challenge.class);
		} catch (DomainObjectNotFoundException e) {
		}
		ResultSet rs = ChallengeFinder.findById(id);
		if(!rs.next()) throw new MapperException("Challenge with id " + id + " does not exist.");
		return getChallenge(rs);
	}
	
	public static Challenge find(String id)  throws SQLException, MapperException {
		ResultSet rs = ChallengeFinder.findById(id);
		if(!rs.next()) throw new MapperException("Challenge with id " + id + " does not exist.");
		
		try {
			return IdentityMap.get(rs.getLong("id"), Challenge.class);
		} catch (DomainObjectNotFoundException e) {
		}
		return getChallenge(rs);
	}

	public static Challenge findOpenByChallenger(long challengerId) throws SQLException, MapperException {
		ResultSet rs = ChallengeFinder.findOpenByChallenger(challengerId);
		if(!rs.next()) throw new MapperException("Challenge with challenger id " + challengerId + " doesn't exist!");
		
		try {
			return IdentityMap.get(rs.getLong("id"), Challenge.class);
		} catch (DomainObjectNotFoundException e) {
		}
		return getChallenge(rs);
		
	}
	
	public static Challenge findOpenByChallengee(long challengeeId) throws SQLException, MapperException {
		ResultSet rs = ChallengeFinder.findOpenByChallengee(challengeeId);
		if(!rs.next()) throw new MapperException("Challenge with challengee id " + challengeeId + " doesn't exist!");
		
		try {
			return IdentityMap.get(rs.getLong("id"), Challenge.class);
		} catch (DomainObjectNotFoundException e) {
		}
		return getChallenge(rs);
		
	}
	
	public static Challenge findOpenByPlayer(long playerId) throws SQLException, MapperException {
		ResultSet rs = ChallengeFinder.findOpenByPlayer(playerId);
		if(!rs.next()) throw new MapperException("Challenge with player id " + playerId + " doesn't exist!");
		
		try {
			return IdentityMap.get(rs.getLong("id"), Challenge.class);
		} catch (DomainObjectNotFoundException e) {
		}
		return getChallenge(rs);
		
	}

	private static Challenge getChallenge(ResultSet rs) throws SQLException, MapperException {
		return ChallengeFactory.createClean(rs.getLong("id"), 
				rs.getLong("version"), 
				rs.getLong("challengerid"), rs.getLong("challengeeid"), rs.getInt("status"));
	}

}