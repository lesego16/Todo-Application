package lesegosibanyoni.todoapp.web;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lesegosibanyoni.todoapp.dao.TodoDao;
import lesegosibanyoni.todoapp.dao.TodoDaoImpl;
import lesegosibanyoni.todoapp.model.Todo;

public class TodoController extends HttpServlet {
	
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private TodoDao todoDao = new TodoDaoImpl();

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getServletPath();

		try {
	        switch (action) {
	            case "/new":
	                showNewForm(request, response);
	                break;
	            case "/insert":
	                insertTodo(request, response);
	                break;
	            case "/delete":
	                deleteTodo(request, response);
	                break;
	            case "/edit":
	                showEditForm(request, response);
	                break;
	            case "/update":
	                updateTodo(request, response);
	                break;
	            case "/list":
	                listTodo(request, response);
	                break;
	            default:
	                showLoginPage(request, response);
	                break;
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        // Handle the exception appropriately, such as showing an error page
	    }

	}
	
	private void showLoginPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
		dispatcher.forward(request, response);
	}

	private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("todo-form.jsp");
		dispatcher.forward(request, response);
	}

	private void insertTodo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String title = request.getParameter("title");
		String username = request.getParameter("username");
		String description = request.getParameter("description");
		boolean isDone = Boolean.valueOf(request.getParameter("isDone"));
		LocalDate targetDate = null;
		
		if(title == null || title.isEmpty() || username == null || 
				username.isEmpty() || description == null || description.isEmpty()) {
			request.setAttribute("errorTitleUserDescription", "Error: title, username and description cannot be empty. Please provide valid inputs");
			RequestDispatcher dispatcher = request.getRequestDispatcher("todo-form.jsp");
			dispatcher.forward(request, response);
			return;
		}
		
		try {
			DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			targetDate = LocalDate.parse(request.getParameter("targetDate"), df);
		}catch(DateTimeParseException e) {
			request.setAttribute("errorDate", "Error: date should be in the format yyyy-MM-dd");
			RequestDispatcher dispatcher = request.getRequestDispatcher("todo-form.jsp");
			dispatcher.forward(request, response);
			throw new ServletException("failed to parse date");
		}

		Todo newTodo = new Todo(title, username, description, targetDate, isDone);
		
		todoDao.insertTodo(newTodo);
	
		RequestDispatcher dispatcher = request.getRequestDispatcher("/list");
		dispatcher.forward(request, response);

	}

	private void deleteTodo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		int id = Integer.parseInt(request.getParameter("id"));
		
		todoDao.deleteTodoById(id);
		

		RequestDispatcher dispatcher = request.getRequestDispatcher("/list");
		dispatcher.forward(request, response);
	}

	private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		int id = Integer.parseInt(request.getParameter("id"));
		Todo existingTodo = todoDao.selectTodoById(id);

		RequestDispatcher dispatcher = request.getRequestDispatcher("todo-form.jsp");
		request.setAttribute("todo", existingTodo);
		dispatcher.forward(request, response);

	}

	private void updateTodo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		int id = Integer.parseInt(request.getParameter("id"));
		String title = request.getParameter("title");
		String username = request.getParameter("username");
		String description = request.getParameter("description");
		String date = request.getParameter("targetDate");

		DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		LocalDate targetDate = LocalDate.parse(date, df);
		boolean isDone = Boolean.valueOf(request.getParameter("isDone"));

		Todo updateTodo = new Todo(id, title, username, description, targetDate, isDone);

		
		todoDao.updateTodo(updateTodo);
		

		RequestDispatcher dispatcher = request.getRequestDispatcher("/list");
		dispatcher.forward(request, response);

	}

	private void listTodo(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException{
		
		List<Todo> listTodo = todoDao.selectAllTodos();
		request.setAttribute("listTodo", listTodo);
		RequestDispatcher dispatcher = request.getRequestDispatcher("todo-list.jsp");
		dispatcher.forward(request, response);

	}

}
