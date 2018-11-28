package app;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Challenge;
import domain.ChallengeDM;
import domain.ChallengeStatus;
import domain.Deck;
import domain.DeckDM;
import domain.Game;
import domain.GameDM;
import domain.Handsize;
import domain.HandsizeDM;
import domain.PlayerStatus;
import domain.Status;
import domain.StatusDM;
import domain.User;
import domain.UserDM;

/**
 * Servlet implementation class AcceptChallenge
 */
@WebServlet("/AcceptChallenge")
public class AcceptChallenge extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AcceptChallenge() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		String challengeid = req.getParameter("challengeid");
		
		Long userId;
		
		try {
			userId = (Long) req.getSession(true).getAttribute("userid");
		}
		catch(NullPointerException e) {
			userId = null;
		}
		
		Challenge c = null;
		
		try {
			c = ChallengeDM.findById(challengeid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		long challengeeid = c.getChallengeeId();
		long challengerid = c.getChallengerId();
		
		User challengee = null;
		User challenger = null;
		
		try {
			challengee = UserDM.findById(challengeeid);
			challenger = UserDM.findById(challengerid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if(challengee == null || challenger == null) {
			req.setAttribute("status", "fail");
			req.setAttribute("message", "Cannot find the challenger or the challengee!");
			req.getRequestDispatcher("/WEB-INF/jsp/acceptChallenge.jsp").forward(req, res);
		}
		else if(userId == null) {
			req.setAttribute("status", "fail");
			req.setAttribute("message", "You need to login first!");
			req.getRequestDispatcher("/WEB-INF/jsp/acceptChallenge.jsp").forward(req, res);
		}
		else if(userId == challenger.getId()) {
			req.setAttribute("status", "fail");
			req.setAttribute("message", "You cannot accept your own challenge!");
			req.getRequestDispatcher("/WEB-INF/jsp/acceptChallenge.jsp").forward(req, res);
		}
		else if(userId != challengee.getId() && userId != challenger.getId()) {
			req.setAttribute("status", "fail");
			req.setAttribute("message", "This is not your challenge!");
			req.getRequestDispatcher("/WEB-INF/jsp/acceptChallenge.jsp").forward(req, res);
		}
		else {

			Deck d1 = null; 
			Deck d2 = null;
			Long gameId = null;
			
			try {
				d1 = DeckDM.findByPlayer(challenger.getId());
				d2 = DeckDM.findByPlayer(challengee.getId());
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			if(d1 == null || d2 == null) {
				req.setAttribute("status", "fail");
				req.setAttribute("message", "One of the players did not upload his deck yet!");
				req.getRequestDispatcher("/WEB-INF/jsp/acceptChallenge.jsp").forward(req, res);
			}
			else if(!(c.getStatus() == ChallengeStatus.OPEN.ordinal())) {
				req.setAttribute("status", "fail");
				req.setAttribute("message", "You already have refused/accepted the challenge or challenger has withdrawn his challenge!");
				req.getRequestDispatcher("/WEB-INF/jsp/acceptChallenge.jsp").forward(req, res);
			}
			else {
				
				c.setStatus(ChallengeStatus.ACCEPTED.ordinal());
				
				try {
					ChallengeDM.update(c);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
				Game g = new Game(challenger.getId(), challengee.getId());
				
				try {
					gameId = GameDM.insert(g);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
				Status status1 = new Status(gameId, g.getChallengerId());
				status1.setStatus(PlayerStatus.PLAYING.ordinal());
				
				Status status2 = new Status(gameId, g.getChallengeeId());
				status2.setStatus(PlayerStatus.PLAYING.ordinal());
				
				try {
					StatusDM.insert(status1);
					StatusDM.insert(status2);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
				req.setAttribute("status", "success");
				req.setAttribute("message", "The challenge from '" + challenger.getUsername() + "' has been successfully accepted");
				req.getRequestDispatcher("/WEB-INF/jsp/acceptChallenge.jsp").forward(req, res);	
			}
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
