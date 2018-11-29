package domain.hand.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.domain.producer.IdentityBasedProducer;
import org.dsrg.soenea.domain.producer.IdentityBasedProducerMethod;

import domain.card.ICard;
import domain.card.mapper.CardInputMapper;
import service.tdg.finder.HandFinder;

public class HandInputMapper implements IdentityBasedProducer{
	
	@IdentityBasedProducerMethod
	public static List<ICard> getCardsInHand(long gameId, long playerId)  throws SQLException, MapperException {
		
		ResultSet rs = HandFinder.getCardsInHand(gameId, playerId);
		if(!rs.next()) throw new MapperException("Hand with gameid " + gameId + " and player id " + playerId + " does not exist.");
		List<ICard> cardsInHand = new ArrayList<ICard>();
		while(rs.next()) {
			 ICard c = CardInputMapper.find(rs.getLong("cardid"));
			 cardsInHand.add(c);
		}
		
		return cardsInHand;
	}
	
	public static List<ICard> getCardsInHand(String gameId, String playerId)  throws SQLException, MapperException {
		
		ResultSet rs = HandFinder.getCardsInHand(gameId, playerId);
		if(!rs.next()) throw new MapperException("Hand with gameid " + gameId + " and player id " + playerId + " does not exist.");
		List<ICard> cardsInHand = new ArrayList<ICard>();
		while(rs.next()) {
			 ICard c = CardInputMapper.find(rs.getLong("cardid"));
			 cardsInHand.add(c);
		}
		
		return cardsInHand;
	}

}
