package lesegosibanyoni.todoapp.web;

import java.io.IOException;

import org.mindrot.jbcrypt.BCrypt;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lesegosibanyoni.todoapp.dao.UserDao;
import lesegosibanyoni.todoapp.model.User;

public class UserController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
    private UserDao userDao;

    public UserController() {
    	super();
    }


	@Override
	public void init(ServletConfig config) throws ServletException {
		userDao = new UserDao();
	}


	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("index.jsp");
	}


	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		register(request, response);
	}

	private void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// Get the parameters from the request
		String firstname = request.getParameter("firstname");
		String lastname = request.getParameter("lastname");
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		// Validate user input
	    if (firstname == null || firstname.isEmpty() ||
	        lastname == null || lastname.isEmpty() ||
	        username == null || username.isEmpty() ||
	        password == null || password.isEmpty()) {
	        // If any fields are empty, set an error message and forward to the index page
	        request.setAttribute("NOTIFICATION", "Please fill in all fields.");
	        request.getRequestDispatcher("index.jsp").forward(request, response);
	        return;
	    }

	 // Validate input to prevent SQL injection attacks
	    if (!isValidInput(firstname) || !isValidInput(lastname) || !isValidInput(username) || !isValidInput(password)) {
	        // If any fields contain invalid characters, set an error message and forward to the index page
	        request.setAttribute("NOTIFICATION", "Please enter valid input.");
	        request.getRequestDispatcher("index.jsp").forward(request, response);
	        return;
	    }

	  //password hashing
	  		String hashedPassword = hashPassword(password);

		// Create a new user object with the given parameters
		User employee = new User(firstname, lastname, username, hashedPassword);


		try {
				// Attempt to register the user
				userDao.registerEmployee(employee);

				// If successful, set a success message and forward to the index page
				request.setAttribute("success", "User Registered Successfully!");
				RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
				dispatcher.forward(request, response);
		}catch (Exception e) {
				// If an error occurs, set an error message and forward to the index page
				request.setAttribute("NOTIFICATION", "An Error Occured!");
				RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
				dispatcher.forward(request, response);
		}

	}

	private String hashPassword(String password) {

		//generate a random salt value to use the hashing process
		String salt = BCrypt.gensalt();

		//use the bcrypt hasing to hash the password with salt
		String hashedPassword = BCrypt.hashpw(password, salt);

		return hashedPassword;

	}


	private boolean isValidInput(String input) {
	    // Implement input validation to prevent SQL injection attacks
	    // Return true if input contains only letters, numbers, and whitespace
	    return input.matches("^[a-zA-Z0-9\\s]+$");
	}

}
