package app;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dsrg.soenea.domain.MapperException;

import domain.user.User;
import domain.user.mapper.UserInputMapper;

/**
 * Servlet implementation class LoginPC
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		String user = req.getParameter("user");
		String pass = req.getParameter("pass");
		
		User u = null;
		try {
			u = UserInputMapper.find(user, pass);
		} catch (SQLException | MapperException e) {
			e.printStackTrace();
		}
		if(u == null) {
			req.setAttribute("user", u);
			req.setAttribute("status", "fail");
			req.setAttribute("message", "I do not recognize that username and password combination.");
			req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, res);
		} else {
			long id = u.getId();
			req.getSession(true).setAttribute("userid", id);
			req.setAttribute("user", u);
			try {
				req.setAttribute("players", UserInputMapper.findAll());
			} catch (SQLException | MapperException e) {
				e.printStackTrace();
			}
			req.setAttribute("status", "success");
			req.setAttribute("message", "User '" + u.getUsername() + "' has been successfully logged in.");
			req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, res);
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
