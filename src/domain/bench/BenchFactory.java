package domain.bench;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dsrg.soenea.domain.MapperException;

import domain.card.Card;
import domain.card.ICard;
import domain.game.Game;
import domain.game.mapper.GameInputMapper;
import domain.user.User;
import domain.user.mapper.UserInputMapper;

public class BenchFactory {

	public static  Bench createNew(Game game, User player, List<ICard> cards) throws SQLException, MapperException{
		Bench d = new Bench(game, player);
		if(cards == null) d.setCards(new ArrayList<ICard>());
		else d.setCards(cards);
		//UoW.getCurrent().registerNew(g);
		return d;
	}
	
	public static Bench createClean(long gameId, long playerId, long version) throws SQLException, MapperException {
		Game game = GameInputMapper.find(gameId);
		User player = UserInputMapper.find(playerId);
		Bench d = new Bench(game, player, version);
		//List<ICard> cards = BenchInputMapper
		//UoW.getCurrent().registerClean(g);
		return d;
	}
	
}
