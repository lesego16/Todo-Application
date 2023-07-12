package lesegosibanyoni.todoapp.web;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lesegosibanyoni.todoapp.dao.LoginDao;
import lesegosibanyoni.todoapp.model.LoginBean;

public class LoginController extends HttpServlet {
	
	private static final String LOGIN_PAGE_URL = "login.jsp";
	private static final long serialVersionUID = 1L;
	private LoginDao loginDao;
	//private User user;


    public LoginController() {
        super();

    }
    
  


	@Override
	public void init(ServletConfig config) throws ServletException {
		loginDao = new LoginDao();
		//user = new User();
	}
	



	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("login.jsp");
	}


	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		authenticate(request, response);
	}

	private void authenticate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
	    if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
	        request.setAttribute("loginError", "Please fill in all fields");
	        response.sendRedirect(LOGIN_PAGE_URL);
	        return;
	    }


		// Create a new user object with the given parameters
		LoginBean employeeLoginBean = new LoginBean(username, password);

		try {
			if(!loginDao.validate(employeeLoginBean)){
				request.setAttribute("loginError", "Invalid login credentials");
				response.sendRedirect(LOGIN_PAGE_URL);
				return;
			}


		} catch (ClassNotFoundException e) {
			throw new ServletException("Failed to validate login", e);
		}
		
		 HttpSession session = request.getSession();
		 session.setAttribute("username", username);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/list");
		dispatcher.forward(request, response);

	}

}
