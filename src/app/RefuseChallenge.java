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
import domain.User;
import domain.UserDM;

/**
 * Servlet implementation class RefuseChallenge
 */
@WebServlet("/RefuseChallenge")
public class RefuseChallenge extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RefuseChallenge() {
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

		
		if(userId == null) {
			req.setAttribute("status", "fail");
			req.setAttribute("message", "You need to login before refusing a challenge!");
			req.getRequestDispatcher("/WEB-INF/jsp/refuseChallenge.jsp").forward(req, res);
		}
		else {
			
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
				req.getRequestDispatcher("/WEB-INF/jsp/refuseChallenge.jsp").forward(req, res);
			}
			else if(userId != challengee.getId() && userId != challenger.getId()) {
				req.setAttribute("status", "fail");
				req.setAttribute("message", "This is not your challenge!");
				req.getRequestDispatcher("/WEB-INF/jsp/refuseChallenge.jsp").forward(req, res);
			}
			else {
				
				if(c.getChallengerId() == userId) {
					c.setStatus(ChallengeStatus.WITHDRAWN.ordinal());
				}
				else {
					c.setStatus(ChallengeStatus.REFUSED.ordinal());
				}
				
				try {
					ChallengeDM.update(c);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
				req.setAttribute("status", "success");
				req.setAttribute("message", "The challenge from '" + challenger.getUsername() + "' has been successfully refused");
				req.getRequestDispatcher("/WEB-INF/jsp/refuseChallenge.jsp").forward(req, res);
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
