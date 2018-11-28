package app;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.uow.MapperFactory;
import org.dsrg.soenea.uow.UoW;

import domain.user.User;
import domain.user.UserFactory;
import domain.user.mapper.UserInputMapper;
import domain.user.mapper.UserOutputMapper;


/**
 * Servlet implementation class Register
 */
@WebServlet("/Register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/*public static ThreadLocal<Connection> myCon;
    
    @Override
    public void init(javax.servlet.ServletConfig config) throws ServletException {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		makeCon();
    };
	
    
    public static synchronized void makeCon() {
    	if(Register.myCon==null) Register.myCon = new ThreadLocal<Connection>();
    }
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Register() {
        super();
    }
    
    public static void setupUoW() {
		 MapperFactory myDomain2MapperMapper = new MapperFactory();
		 //myDomain2MapperMapper.addMapping(User.class, UserOutputMapper.class);
		 UoW.initMapperFactory(myDomain2MapperMapper);
	 } 

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		/*try {
			Register.myCon.set(DriverManager.getConnection("jdbc:mysql://localhost:3306/pokemon?"
					+"user=root&password=root"));
		} catch (SQLException e1) {
			e1.printStackTrace();
		}*/
		
		String username = req.getParameter("user");
		String pass = req.getParameter("pass");
		
		if(username==null || username.isEmpty() || pass==null || pass.isEmpty() ) {
			req.setAttribute("status", "fail");
			req.setAttribute("message", "Please enter both a username and a password.");
			req.getRequestDispatcher("/WEB-INF/jsp/register.jsp").forward(req, res);
		} else {
			User u = null;
			try {
				u = UserInputMapper.find(username);
			} catch (SQLException | MapperException e) {
				e.printStackTrace();
			}
		
			if(u != null) {
				req.setAttribute("status", "fail");
				req.setAttribute("message", "That user has already registered.");
				req.getRequestDispatcher("/WEB-INF/jsp/register.jsp").forward(req, res);
			} else {
				Long id = null;
				try {
					//UoW.newCurrent();
					u = UserFactory.createNew(username, pass);
					id = UserOutputMapper.insert(u);
					//UoW.getCurrent().commit();
				} catch (SQLException | MapperException e) {
					e.printStackTrace();
				}
				
				req.getSession(true).setAttribute("userid", id);
				req.setAttribute("status", "success");
				req.setAttribute("message", "User '" + username + "' has been successfully registered.");
				req.getRequestDispatcher("/WEB-INF/jsp/register.jsp").forward(req, res);
			}
		
			/*Connection con = Register.myCon.get();
			Register.myCon.remove();
			try {con.close();} catch(Exception e){}*/
		
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
