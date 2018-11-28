package app;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Card;
import domain.CardDM;
import domain.Game;
import domain.GameDM;
import domain.Handsize;
import domain.HandsizeDM;
import domain.User;
import domain.UserDM;

/**
 * Servlet implementation class ViewHand
 */
@WebServlet("/ViewHand")
public class ViewHand extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewHand() {
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
		
		try {
			g = GameDM.findById(gameid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if(g == null) {
			req.setAttribute("status", "fail");
			req.setAttribute("message", "Could not find this game!");
			req.getRequestDispatcher("/WEB-INF/jsp/viewHand.jsp").forward(req, res);
		}
		else if(player == null) {
			req.setAttribute("status", "fail");
			req.setAttribute("message", "You need to login first!");
			req.getRequestDispatcher("/WEB-INF/jsp/viewHand.jsp").forward(req, res);
		}else if(player.getId() != g.getChallengeeId() && player.getId() != g.getChallengerId()){
			req.setAttribute("status", "fail");
			req.setAttribute("message", "This is not your game so you cannot view your hand!");
			req.getRequestDispatcher("/WEB-INF/jsp/viewHand.jsp").forward(req, res);
		}
		else {
			
			List<Card> cardsInHand = new ArrayList<Card>();
			
			try {
				cardsInHand = HandsizeDM.getCardsInHand(g.getId(), playerId);
				
				for(Card card : cardsInHand) {
					Card fullCardInfo = CardDM.findById(card.getId());
					card.setDeckId(fullCardInfo.getDeckId());
					card.setName(fullCardInfo.getName());
					card.setType(fullCardInfo.getType());
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			req.setAttribute("hand", cardsInHand);
			
			req.setAttribute("status", "success");
			req.setAttribute("message", "You can successfully view your hand!");
			req.getRequestDispatcher("/WEB-INF/jsp/viewHand.jsp").forward(req, res);
			
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
