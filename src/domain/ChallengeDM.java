package domain;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import data.ChallengeRDG;

public class ChallengeDM {

	public static long insert(Challenge challenge) throws SQLException {
		ChallengeRDG challengeRdg = new ChallengeRDG(challenge.getId(), challenge.getChallengerId(), challenge.getChallengeeId(), challenge.getStatus());
		return challengeRdg.insert();
	}
	
	public static void update(Challenge challenge) throws SQLException {
		ChallengeRDG challengeRdg = new ChallengeRDG(challenge.getId(), challenge.getChallengerId(), challenge.getChallengeeId(), challenge.getStatus());
		challengeRdg.update();
	}
	
	public static void delete(Challenge challenge) throws SQLException {
		ChallengeRDG challengeRdg = new ChallengeRDG(challenge.getId(), challenge.getChallengerId(), challenge.getChallengeeId(), challenge.getStatus());
		challengeRdg.delete();
	}
	
	public static Challenge findById(long id) throws SQLException {
		ChallengeRDG challengeRdg = ChallengeRDG.findById(id);
		if(challengeRdg != null) {
			Challenge challenge = new Challenge(challengeRdg.getId(), challengeRdg.getChallengerId(), challengeRdg.getChallengeeId(), challengeRdg.getStatus());
			return challenge;
		}
		else 
			return null;
	}
	
	public static Challenge findById(String id) throws SQLException {
		ChallengeRDG challengeRdg = ChallengeRDG.findById(id);
		if(challengeRdg != null) {
			Challenge challenge = new Challenge(challengeRdg.getId(), challengeRdg.getChallengerId(), challengeRdg.getChallengeeId(), challengeRdg.getStatus());
			return challenge;
		}
		else 
			return null;
	}
	
	public static List<Challenge> findOpenByChallenger(long challengerid) throws SQLException {
		List<ChallengeRDG> challengesRdg = ChallengeRDG.findOpenByChallengee(challengerid);
		List<Challenge> challenges = new ArrayList<Challenge>();
		if(!challengesRdg.isEmpty()) {
			for(ChallengeRDG challengeRdg : challengesRdg) {
				Challenge challenge = new Challenge(challengeRdg.getId(), challengeRdg.getChallengerId(), challengeRdg.getChallengeeId(), challengeRdg.getStatus());
				challenges.add(challenge);	
			}
			return challenges;
		}
		else
			return null;
	}

	public static List<Challenge> findOpenByChallengee(long challengeeid) throws SQLException {
		List<ChallengeRDG> challengesRdg = ChallengeRDG.findOpenByChallengee(challengeeid);
		List<Challenge> challenges = new ArrayList<Challenge>();
		if(!challengesRdg.isEmpty()) {
			for(ChallengeRDG challengeRdg : challengesRdg) {
				Challenge challenge = new Challenge(challengeRdg.getId(), challengeRdg.getChallengerId(), challengeRdg.getChallengeeId(), challengeRdg.getStatus());
				challenges.add(challenge);	
			}
			return challenges;
		}
		else
			return null;
	}
	
	public static List<Challenge> findOpenByPlayer(long playerid) throws SQLException {
		List<ChallengeRDG> challengesRdg = ChallengeRDG.findOpenByPlayer(playerid);
		List<Challenge> challenges = new ArrayList<Challenge>();
		if(!challengesRdg.isEmpty()) {
			for(ChallengeRDG challengeRdg : challengesRdg) {
				Challenge challenge = new Challenge(challengeRdg.getId(), challengeRdg.getChallengerId(), challengeRdg.getChallengeeId(), challengeRdg.getStatus());
				challenges.add(challenge);	
			}
			return challenges;
		}
		else
			return null;
	}
	
	public static List<Challenge> findAll() throws SQLException{
		List<ChallengeRDG> challengesRdg = ChallengeRDG.findAll();
		List<Challenge> challenges = new ArrayList<Challenge>();
		if(!challengesRdg.isEmpty()) {
			for(ChallengeRDG challengeRdg : challengesRdg) {
				Challenge challenge = new Challenge(challengeRdg.getId(), challengeRdg.getChallengerId(), challengeRdg.getChallengeeId(), challengeRdg.getStatus());
				challenges.add(challenge);	
			}
			return challenges;
		}
		else
			return null;
	}

	public static List<Challenge> findAllOpen() throws SQLException{
		List<ChallengeRDG> challengesRdg = ChallengeRDG.findAllOpen();
		List<Challenge> challenges = new ArrayList<Challenge>();
		if(!challengesRdg.isEmpty()) {
			for(ChallengeRDG challengeRdg : challengesRdg) {
				Challenge challenge = new Challenge(challengeRdg.getId(), challengeRdg.getChallengerId(), challengeRdg.getChallengeeId(), challengeRdg.getStatus());
				challenges.add(challenge);	
			}
			return challenges;
		}
		else
			return null;
	}
	
}
