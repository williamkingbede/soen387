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

import domain.Bench;
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
 * Servlet implementation class PlayPokemonToBench
 */
@WebServlet("/PlayPokemonToBench")
public class PlayPokemonToBench extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PlayPokemonToBench() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		String gameid = req.getParameter("gameid");
		String cardid = req.getParameter("cardid");
		
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
		Card pokemon = null;
		
		try {
			g = GameDM.findById(gameid);
			s = StatusDM.find(g.getId(), playerId);
			pokemon = CardDM.findById(cardid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if(g == null) {
			req.setAttribute("status", "fail");
			req.setAttribute("message", "Could not find this game!");
			req.getRequestDispatcher("/WEB-INF/jsp/playPokemonToBench.jsp").forward(req, res);
		}
		else if(player == null) {
			req.setAttribute("status", "fail");
			req.setAttribute("message", "You need to login first!");
			req.getRequestDispatcher("/WEB-INF/jsp/playPokemonToBench.jsp").forward(req, res);
		}else if(player.getId() != g.getChallengeeId() && player.getId() != g.getChallengerId()){
			req.setAttribute("status", "fail");
			req.setAttribute("message", "This is not your game so you cannot play a pokemon to bench!");
			req.getRequestDispatcher("/WEB-INF/jsp/playPokemonToBench.jsp").forward(req, res);
		}
		else if(s.getStatus() == PlayerStatus.RETIRED.ordinal()) {
			req.setAttribute("status", "fail");
			req.setAttribute("message", "You have retired from this game so you cannot play a pokemon to bench!");
			req.getRequestDispatcher("/WEB-INF/jsp/playPokemonToBench.jsp").forward(req, res);
		}
		else if(!pokemon.getType().equals("p")) {
			req.setAttribute("status", "fail");
			req.setAttribute("message", "Not a Pokemon!!");
			req.getRequestDispatcher("/WEB-INF/jsp/playPokemonToBench.jsp").forward(req, res);
		}
		else{
			
			Deck d = null;
			Bench bench = new Bench(g.getId(), playerId);
			Handsize hand = new Handsize(g.getId(), playerId);
			List<Card> cardsInHand = new ArrayList<Card>();
			boolean inHand = false; 
			
			try {
				d = DeckDM.findByPlayer(playerId);
				d.setCards(CardDM.findByDeck(d.getId()));
				cardsInHand = HandsizeDM.getCardsInHand(g.getId(), player.getId());
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			for(Card card : cardsInHand) {
				if(pokemon.getId() == card.getId()) {
					inHand = true;
				}
			}
			
			if(inHand) {
				
				List<Card> cardsInBench = new ArrayList<Card>();
				boolean alreadyInBench = false;
				
				try {
					cardsInBench = BenchDM.getCardsInBench(g.getId(), playerId);
					cardsInBench.add(pokemon);
					BenchDM.insert(bench, pokemon.getId());
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
				for(Card card : cardsInBench) {
					if(pokemon.getId() == card.getId()) {
						alreadyInBench = true;
					}
				}
				
				if(alreadyInBench) {
					req.setAttribute("status", "fail");
					req.setAttribute("message", "You already have this pokemon card in your bench!");
					req.getRequestDispatcher("/WEB-INF/jsp/playPokemonToBench.jsp").forward(req, res);
				}
				else {
					req.setAttribute("status", "success");
					req.setAttribute("message", "You have successfully played '" + pokemon.getName() + "' to your bench!");
					req.getRequestDispatcher("/WEB-INF/jsp/playPokemonToBench.jsp").forward(req, res);
				}
			}
			else {
				req.setAttribute("status", "fail");
				req.setAttribute("message", "Pokemon '" + pokemon.getName() + "' is not in your hand so you cannot play it to your bench!");
				req.getRequestDispatcher("/WEB-INF/jsp/playPokemonToBench.jsp").forward(req, res);
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
