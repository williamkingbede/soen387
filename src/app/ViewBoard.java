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

import domain.BenchDM;
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
 * Servlet implementation class ViewBoard
 */
@WebServlet("/ViewBoard")
public class ViewBoard extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewBoard() {
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
			req.getRequestDispatcher("/WEB-INF/jsp/viewBoard.jsp").forward(req, res);
		}
		else if(player == null) {
			req.setAttribute("status", "fail");
			req.setAttribute("message", "You need to login first!");
			req.getRequestDispatcher("/WEB-INF/jsp/viewBoard.jsp").forward(req, res);
		}
		else {
			
			if(player.getId() != g.getChallengeeId() && player.getId() != g.getChallengerId()) {
				req.setAttribute("status", "fail");
				req.setAttribute("message", "This is not your game so you cannot view its board!");
				req.getRequestDispatcher("/WEB-INF/jsp/viewBoard.jsp").forward(req, res);
			}
			else {
				//Challenger variables
				Status status1 = null;
				Deck d1 = null;
				int handsize1 = 0;
				int decksize1 = 0;
				List<Card> bench1 = new ArrayList<Card>();
				
				//Challengee variables
				Status status2 = null;
				Deck d2 = null;
				int handsize2 = 0;
				int decksize2 = 0;
				List<Card> bench2 = new ArrayList<Card>();
				
				try {
					status1 = StatusDM.find(g.getId(), g.getChallengerId());
					d1 = DeckDM.findByPlayer(g.getChallengerId());
					d1.setCards(CardDM.findByDeck(d1.getId()));
					handsize1 = HandsizeDM.getCardsInHand(g.getId(), g.getChallengerId()).size();
					decksize1 = d1.decksize();
					bench1 = BenchDM.getCardsInBench(g.getId(), g.getChallengerId());
					
					status2 = StatusDM.find(g.getId(), g.getChallengeeId());
					d2 = DeckDM.findByPlayer(g.getChallengeeId());
					d2.setCards(CardDM.findByDeck(d2.getId()));
					handsize2 = HandsizeDM.getCardsInHand(g.getId(), g.getChallengeeId()).size();
					decksize2 = d2.decksize();
					bench2 = BenchDM.getCardsInBench(g.getId(), g.getChallengeeId());
					
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
				req.setAttribute("game", g);
				req.setAttribute("deck1", d1.getId());
				req.setAttribute("deck2", d2.getId());
				
				req.setAttribute("discardsize1", 0);
				req.setAttribute("discardsize2", 0);
				
				String s1 = (status1.getStatus() == PlayerStatus.PLAYING.ordinal() ? "playing" : "retired");
				String s2 = (status2.getStatus() == PlayerStatus.PLAYING.ordinal() ? "playing" : "retired");
				
				req.setAttribute("status1", s1);
				req.setAttribute("handsize1", handsize1);
				req.setAttribute("decksize1", decksize1);
				req.setAttribute("bench1", bench1);
				
				req.setAttribute("status2", s2);
				req.setAttribute("handsize2", handsize2);
				req.setAttribute("decksize2", decksize2);
				req.setAttribute("bench2", bench2);
				
				req.setAttribute("status", "success");
				req.setAttribute("message", "You can succesfully view the board!");
				req.getRequestDispatcher("/WEB-INF/jsp/viewBoard.jsp").forward(req, res);
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
