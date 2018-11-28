package domain;

import java.sql.SQLException;
import java.util.List;

import data.BenchRDG;

public class BenchDM {
	
	public static void insert(Bench bench) throws SQLException {
		BenchRDG benchRdg = new BenchRDG(bench.getGameId(), bench.getPlayerId());
		benchRdg.insert();
	}
	
	public static void insert(Bench bench, long cardid) throws SQLException {
		BenchRDG benchRdg = new BenchRDG(bench.getGameId(), bench.getPlayerId());
		benchRdg.insert(cardid);
	}
	
	public static void update(Bench bench, long cardid) throws SQLException {
		BenchRDG benchRdg = new BenchRDG(bench.getGameId(), bench.getPlayerId());
		benchRdg.update(cardid);
	}
	
	public static void delete(Bench bench) throws SQLException {
		BenchRDG benchRdg = new BenchRDG(bench.getGameId(), bench.getPlayerId());
		benchRdg.delete();
	}
	
	public static List<Card> getCardsInBench(long gameId, long playerId) throws SQLException {
		List<Card> cardsInBench = BenchRDG.getCardsInBench(gameId, playerId);
		return cardsInBench;
	}
	
	public static List<Card> getCardsInBench(String gameId, String playerId) throws SQLException {
		List<Card> cardsInBench = BenchRDG.getCardsInBench(gameId, playerId);
		return cardsInBench;
	}
	

}
