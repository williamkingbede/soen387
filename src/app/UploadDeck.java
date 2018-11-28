package app;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Card;
import domain.CardDM;
import domain.Deck;
import domain.DeckDM;
import domain.User;
import domain.UserDM;

/**
 * Servlet implementation class UploadDeck
 */
@MultipartConfig
@WebServlet("/UploadDeck")
public class UploadDeck extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UploadDeck() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		String deck = req.getParameter("deck");
		
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
		
		if(deck == null || deck == "") {
			req.setAttribute("status", "fail");
			req.setAttribute("message", "You have not uploaded your deck!");
			req.getRequestDispatcher("/WEB-INF/jsp/uploadDeck.jsp").forward(req, res);
		}
		else if(u == null) {
			req.setAttribute("status", "fail");
			req.setAttribute("message", "You need to login before uploading your deck!");
			req.getRequestDispatcher("/WEB-INF/jsp/uploadDeck.jsp").forward(req, res);
		}else {
			
			String[] cards = deck.split("\n");
			
			if(cards.length != 40) {
				req.setAttribute("status", "fail");
				req.setAttribute("message", "You have to upload exactly 40 cards no more no less!");
				req.getRequestDispatcher("/WEB-INF/jsp/uploadDeck.jsp").forward(req, res);
			}
			else {
				
				Deck d = null;
				
				try {
					d = DeckDM.findByPlayer(u.getId());
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
				List<Card> newCards = new ArrayList<Card>();
				
				for(int i = 0; i < cards.length; i++) {
					String line = cards[i];
					String type = line.substring(0,1);
					String name = line.substring(3,line.length()-1);
					name = name.replaceAll("\"", "");
					//System.out.println("type: " + type);
					//System.out.println("name: " + name);
					newCards.add(new Card(type, name));
				}
				
				if(d != null) {
					
					try {
						List<Card> oldCards = CardDM.findByDeck(d.getId());
						long[] ids = new long[oldCards.size()];
						
						for(int i = 0; i < ids.length; i++) {
							ids[i] = oldCards.get(i).getId();
							//System.out.println("cardid: " + ids[i]);
						}
						
						for(int i = 0; i < ids.length; i++) {
							CardDM.update(newCards.get(i), ids[i]);
						}
						
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				else if(d == null){
					d = new Deck(u.getId());
					
					try {
						long deckid = DeckDM.insert(d);
						for(Card card : newCards) {
							card.setDeckId(deckid);
							CardDM.insert(card);
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				
				req.setAttribute("status", "success");
				req.setAttribute("message", "You have successfully uploaded your deck!");
				req.getRequestDispatcher("/WEB-INF/jsp/uploadDeck.jsp").forward(req, res);
			}
			
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req, res);
		
	    /*Part filePart = req.getPart("deck");
	    
	    System.out.println("filePart: " + filePart);
	    
		if(filePart == null) {
			req.setAttribute("status", "fail");
			req.setAttribute("message", "You have not uploaded your deck!");
			req.getRequestDispatcher("/WEB-INF/jsp/uploadDeck.jsp").forward(req, res);
		}
		else {

		    InputStream fileContent = filePart.getInputStream();
			
		    //https://stackoverflow.com/questions/309424/how-to-read-convert-an-inputstream-into-a-string-in-java
		    String newLine = System.getProperty("line.separator");
		    BufferedReader reader = new BufferedReader(new InputStreamReader(fileContent));
		    StringBuilder result = new StringBuilder();
		    boolean flag = false;
		    for (String line; (line = reader.readLine()) != null; ) {
		        result.append(flag? newLine: "").append(line);
		        line = line.replace(" " , "");
		        String type = line.substring(0, 1);
		        System.out.println("line: " + line);
		        flag = true;
		    }
		    
			req.setAttribute("status", "success");
			req.setAttribute("message", "You have successfully uploaded your deck!");
			req.getRequestDispatcher("/WEB-INF/jsp/uploadDeck.jsp").forward(req, res);
		}*/
	}

}
