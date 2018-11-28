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
import domain.User;
import domain.UserDM;

/**
 * Servlet implementation class ChallengePlayer
 */
@WebServlet("/ChallengePlayer")
public class ChallengePlayer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChallengePlayer() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		String challengeeId = req.getParameter("challengeeid");
		Long challengerId;
		
		try {
			challengerId = (Long) req.getSession(true).getAttribute("userid");
		}
		catch(NullPointerException e) {
			challengerId = null;
		}
		
		User challengee = null;
		User challenger = null;
		
		try {
			challengee = UserDM.findById(challengeeId);
			challenger = UserDM.findById(challengerId);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch(NullPointerException e) {
			challenger = null;
		}
		
		
		if(challenger == null) {
			req.setAttribute("status", "fail");
			req.setAttribute("message", "You need to login before challenging a player!");
			req.getRequestDispatcher("/WEB-INF/jsp/challengePlayer.jsp").forward(req, res);
		} 
		else if(challengee == null) {
			req.setAttribute("status", "fail");
			req.setAttribute("message", "The player you wanna challenge does not exist!");
			req.getRequestDispatcher("/WEB-INF/jsp/challengePlayer.jsp").forward(req, res);
		} 
		else if (challengerId == challengee.getId()){
			req.setAttribute("status", "fail");
			req.setAttribute("message", "You cannot challenge yourself !");
			req.getRequestDispatcher("/WEB-INF/jsp/challengePlayer.jsp").forward(req, res);
		}
		else {
			
			//check if the challenger has a deck
			
			Challenge c = new Challenge(challengerId, challengee.getId(), 0);
			
			try {
				ChallengeDM.insert(c);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			req.setAttribute("challenge", c);
			req.setAttribute("status", "success");
			req.setAttribute("message", "Your challenge to '" + challengee.getUsername() + "' has been successfully processed.");
			req.getRequestDispatcher("/WEB-INF/jsp/challengePlayer.jsp").forward(req, res);
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
