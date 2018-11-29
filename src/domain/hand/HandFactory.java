package domain.hand;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dsrg.soenea.domain.MapperException;

import domain.card.ICard;
import domain.game.Game;
import domain.game.mapper.GameInputMapper;
import domain.user.User;
import domain.user.mapper.UserInputMapper;

public class HandFactory {

	public static  Hand createNew(Game game, User player, List<ICard> cards) throws SQLException, MapperException{
		Hand d = new Hand(game, player);
		if(cards == null) d.setCards(new ArrayList<ICard>());
		else d.setCards(cards);
		//UoW.getCurrent().registerNew(g);
		return d;
	}
	
	public static Hand createClean(long gameId, long playerId, long version) throws SQLException, MapperException {
		Game game = GameInputMapper.find(gameId);
		User player = UserInputMapper.find(playerId);
		Hand d = new Hand(game, player, version);
		//List<ICard> cards = HandInputMapper
		//UoW.getCurrent().registerClean(g);
		return d;
	}
	
}
