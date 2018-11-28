package app;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dsrg.soenea.domain.MapperException;

import domain.user.User;
import domain.user.mapper.UserInputMapper;

/**
 * Servlet implementation class LogoutPC
 */
//@WebServlet("/Register")
public class Logout extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Logout() {
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
			u = UserInputMapper.find(userId);
		} catch (SQLException | MapperException e) {
			e.printStackTrace();
		}catch(NullPointerException e) {
			u = null;
		} 
		
		if(u == null) {
			req.setAttribute("status", "fail");
			req.setAttribute("message", "I could not find your session! You must be already logged or you have not registered and logged in yet!");
			req.getRequestDispatcher("/WEB-INF/jsp/register.jsp").forward(req, res);
		}
		else {
			req.getSession(true).invalidate();
			req.setAttribute("status", "success");
			req.setAttribute("message", "User '" + u.getUsername() + "' has been successfully logged out.");
			req.getRequestDispatcher("/WEB-INF/jsp/register.jsp").forward(req, res);
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
