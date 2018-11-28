package domain;

import java.sql.SQLException;

import data.StatusRDG;

public class StatusDM {

	public static void insert(Status status) throws SQLException {
		StatusRDG statusRdg = new StatusRDG(status.getGameId(), status.getPlayerId(), status.getStatus());
		statusRdg.insert();
	}
	
	public static void update(Status status) throws SQLException {
		StatusRDG statusRdg = new StatusRDG(status.getGameId(), status.getPlayerId(), status.getStatus());
		statusRdg.update();
	}
	
	public static void delete(Status status) throws SQLException {
		StatusRDG statusRdg = new StatusRDG(status.getGameId(), status.getPlayerId(), status.getStatus());
		statusRdg.delete();
	}
	
	public static Status find(long gameId, long playerId) throws SQLException {
		StatusRDG statusRdg = StatusRDG.find(gameId, playerId);
		if(statusRdg != null) {
			Status status = new Status(statusRdg.getGameId(), statusRdg.getPlayerId(), statusRdg.getStatus());
			return status;
		}
		else 
			return null;
	}
	
	public static Status find(String gameId, String playerId) throws SQLException {
		StatusRDG statusRdg = StatusRDG.find(gameId, playerId);
		if(statusRdg != null) {
			Status status = new Status(statusRdg.getGameId(), statusRdg.getPlayerId(), statusRdg.getStatus());
			return status;
		}
		else 
			return null;
	}
	
}
