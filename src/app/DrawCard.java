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
 * Servlet implementation class DrawCard
 */
@WebServlet("/DrawCard")
public class DrawCard extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DrawCard() {
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
			req.getRequestDispatcher("/WEB-INF/jsp/drawCard.jsp").forward(req, res);
		}
		else if(player == null) {
			req.setAttribute("status", "fail");
			req.setAttribute("message", "You need to login first!");
			req.getRequestDispatcher("/WEB-INF/jsp/drawCard.jsp").forward(req, res);
		}
		else {
			if(player.getId() != g.getChallengeeId() && player.getId() != g.getChallengerId()) {
				req.setAttribute("status", "fail");
				req.setAttribute("message", "This is not your game so you cannot draw a card!");
				req.getRequestDispatcher("/WEB-INF/jsp/drawCard.jsp").forward(req, res);
			}
			else {
				
				Status s = null;
				Deck d = null;
				
				try {
					s = StatusDM.find(g.getId(), playerId);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
				if(s.getStatus() == PlayerStatus.RETIRED.ordinal()) {
					req.setAttribute("status", "fail");
					req.setAttribute("message", "You have retired from this game therefore you cannot draw a card!");
					req.getRequestDispatcher("/WEB-INF/jsp/drawCard.jsp").forward(req, res);
				}
				else {
					
					try {
						d = DeckDM.findByPlayer(playerId);
						d.setCards(CardDM.findByDeck(d.getId()));
						System.out.println("decksize: " + d.getCards().size());
					} catch (SQLException e) {
						e.printStackTrace();
					}
					
					Handsize hand = new Handsize(g.getId(), playerId);
					List<Card> cardsInHand = new ArrayList<Card>();
					Card drawnCard = d.getCards().get(0);
					cardsInHand.add(drawnCard);
					
					try {
						cardsInHand = HandsizeDM.getCardsInHand(g.getId(), playerId);
						hand.getCards().add(drawnCard);
						HandsizeDM.insert(hand, d.getCards().get(0).getId());
						
						CardDM.updateSoftDelete(drawnCard);
						
						d = DeckDM.findByPlayer(playerId);
						d.setCards(CardDM.findByDeck(d.getId()));
						
					} catch (SQLException e) {
						e.printStackTrace();
					}

					req.setAttribute("status", "success");
					req.setAttribute("message", "You have successfully drawn the card '" + drawnCard.getName() + "'!");
					req.getRequestDispatcher("/WEB-INF/jsp/drawCard.jsp").forward(req, res);
				}
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
