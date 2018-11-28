package app;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.CardDM;
import domain.Deck;
import domain.DeckDM;
import domain.User;
import domain.UserDM;

/**
 * Servlet implementation class ViewDeck
 */
@WebServlet("/ViewDeck")
public class ViewDeck extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewDeck() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		Long userId;
		
		try {
			userId = (Long) req.getSession(true).getAttribute("userid");
		}
		catch(NullPointerException e) {
			userId = null;
		}
		
		User u = null;
		
		try {
			u = UserDM.findById(userId);
		} catch (SQLException e) {
			e.printStackTrace();
		}catch(NullPointerException e) {
			u = null;
		}
		
		if(u == null) {
			req.setAttribute("status", "fail");
			req.setAttribute("message", "You have to login first!");
			req.getRequestDispatcher("/WEB-INF/jsp/viewDeck.jsp").forward(req, res);
		}
		else {
			
			Deck d = null;
			
			try {
				d = DeckDM.findByPlayer(u.getId());
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			if(d == null) {
				req.setAttribute("status", "fail");
				req.setAttribute("message", "You have not uploaded your deck yet!");
				req.getRequestDispatcher("/WEB-INF/jsp/viewDeck.jsp").forward(req, res);
			}else {
				
				//Retrieving the cards
				try {
					d.setCards(CardDM.findByDeck(d.getId()));
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
				req.setAttribute("status", "success");
				req.setAttribute("message", "You can successfully view your deck!");
				req.setAttribute("deck", d);
				req.getRequestDispatcher("/WEB-INF/jsp/viewDeck.jsp").forward(req, res);
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
