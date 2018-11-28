package app;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.ChallengeDM;
import domain.User;
import domain.UserDM;

/**
 * Servlet implementation class MyOpenChallenges
 */
@WebServlet("/MyOpenChallenges")
public class MyOpenChallenges extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MyOpenChallenges() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		long playerId = (Long)req.getSession(true).getAttribute("userid");
		
		User challengee = null;
		
		try {
			challengee = UserDM.findById(playerId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if(challengee == null) {
			req.setAttribute("status", "fail");
			req.setAttribute("message", "You need to login to see your open challenges!");
			req.getRequestDispatcher("/WEB-INF/jsp/myOpenChallenges.jsp").forward(req, res);
		}
		else{
			try {
				req.setAttribute("openChallenges", ChallengeDM.findOpenByPlayer(playerId));
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			req.getRequestDispatcher("/WEB-INF/jsp/myOpenChallenges.jsp").forward(req, res);
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
