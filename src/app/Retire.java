package app;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Game;
import domain.GameDM;
import domain.PlayerStatus;
import domain.Status;
import domain.StatusDM;
import domain.User;
import domain.UserDM;

/**
 * Servlet implementation class Retire
 */
@WebServlet("/Retire")
public class Retire extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Retire() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		String gameid = req.getParameter("gameid");
		
		Long playerId;
		
		try {
			playerId = (Long) req.getSession(true).getAttribute("userid");
		}
		catch(NullPointerException e) {
			playerId = null;
		}
		
		User player = null;
		
		try {
			player = UserDM.findById(playerId);
		} catch (SQLException e) {
			e.printStackTrace();
		}catch(NullPointerException e) {
			player = null;
		}
		
		Game g = null;
		Status s = null;
		
		try {
			g = GameDM.findById(gameid);
			s = StatusDM.find(g.getId(), playerId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if(g == null) {
			req.setAttribute("status", "fail");
			req.setAttribute("message", "Could not find this game!");
			req.getRequestDispatcher("/WEB-INF/jsp/retire.jsp").forward(req, res);
		}
		else if(player == null) {
			req.setAttribute("status", "fail");
			req.setAttribute("message", "You need to login first!");
			req.getRequestDispatcher("/WEB-INF/jsp/retire.jsp").forward(req, res);
		}else if(player.getId() != g.getChallengeeId() && player.getId() != g.getChallengerId()){
			req.setAttribute("status", "fail");
			req.setAttribute("message", "This is not your game so you cannot retire from it!");
			req.getRequestDispatcher("/WEB-INF/jsp/retire.jsp").forward(req, res);
		}
		else if(s.getStatus() == PlayerStatus.RETIRED.ordinal()){
			req.setAttribute("status", "fail");
			req.setAttribute("message", "You already have retired from this game!");
			req.getRequestDispatcher("/WEB-INF/jsp/retire.jsp").forward(req, res);
		}
		else {
			s.setStatus(PlayerStatus.RETIRED.ordinal());
			
			try {
				StatusDM.update(s);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			req.setAttribute("status", "success");
			req.setAttribute("message", "You have successfully retired from this game!");
			req.getRequestDispatcher("/WEB-INF/jsp/retire.jsp").forward(req, res);
			
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
