package domain.bench.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.domain.producer.IdentityBasedProducer;
import org.dsrg.soenea.domain.producer.IdentityBasedProducerMethod;

import domain.card.ICard;
import domain.card.mapper.CardInputMapper;
import service.tdg.finder.BenchFinder;

public class BenchInputMapper implements IdentityBasedProducer{
	
	@IdentityBasedProducerMethod
	public static List<ICard> getCardsInBench(long gameId, long playerId)  throws SQLException, MapperException {
		
		ResultSet rs = BenchFinder.getCardsInBench(gameId, playerId);
		if(!rs.next()) throw new MapperException("Bench with gameid " + gameId + " and player id " + playerId + " does not exist.");
		List<ICard> cardsInBench = new ArrayList<ICard>();
		while(rs.next()) {
			 ICard c = CardInputMapper.find(rs.getLong("cardid"));
			 cardsInBench.add(c);
		}
		
		return cardsInBench;
	}
	
	public static List<ICard> getCardsInBench(String gameId, String playerId)  throws SQLException, MapperException {
		
		ResultSet rs = BenchFinder.getCardsInBench(gameId, playerId);
		if(!rs.next()) throw new MapperException("Bench with gameid " + gameId + " and player id " + playerId + " does not exist.");
		List<ICard> cardsInBench = new ArrayList<ICard>();
		while(rs.next()) {
			 ICard c = CardInputMapper.find(rs.getLong("cardid"));
			 cardsInBench.add(c);
		}
		
		return cardsInBench;
	}

}
