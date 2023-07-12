package lesegosibanyoni.todoapp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import lesegosibanyoni.todoapp.model.Todo;
import lesegosibanyoni.todoapp.utils.JDBCUtils;

public class TodoDaoImpl implements TodoDao{
	
	private static final String  INSERT_TODOS = "INSERT INTO todos (title, username, description, target_date, is_done) VALUES (?, ?, ?, ?, ?)";
	private static final String SELECT_TODO_BY_ID = "SELECT id, title, username, description, target_date, is_done FROM todos WHERE id=?";
	private static final String SELECT_ALL_TODOS = "SELECT * FROM todos";
	private static final String DELETE_TODO_BY_ID = "DELETE FROM todos WHERE id =?";
	private static final String UPDATE_TODO = "UPDATE todos SET title=?, username=?, description=?, target_date=?, is_done=? where id=?";
	
	public TodoDaoImpl() {
		
	}
	
	public void insertTodo(Todo todo){
		
		try(Connection connection = JDBCUtils.getConnection();
				
				PreparedStatement preparedStatement = connection.prepareStatement(INSERT_TODOS)){
			
				preparedStatement.setString(1, todo.getTitle());
				preparedStatement.setString(2, todo.getUsername());
				preparedStatement.setString(3, todo.getDescription());
				preparedStatement.setDate(4, JDBCUtils.getSQLDate(todo.getTargetDate()));
				preparedStatement.setBoolean(5, todo.isStatus());
				
				int rowsInserted = preparedStatement.executeUpdate();
				
				if (rowsInserted == 0) {
		            throw new RuntimeException("Inserting todo failed, no rows affected.");
				}
				
		}catch (SQLException e) {
			 throw new RuntimeException("failed to insert todos ", e);
		}
	
	}
	
	
	public Todo selectTodoById(long todoId){
		
		Todo todo = null;
		
	try(Connection connection = JDBCUtils.getConnection();
					
					PreparedStatement preparedStatement = connection.prepareStatement(SELECT_TODO_BY_ID)){
					preparedStatement.setLong(1, todoId);
				
				ResultSet resultSet = preparedStatement.executeQuery();
				
				while(resultSet.next()) {
					
					long id = resultSet.getLong("id");
					String title = resultSet.getString("title");
					String username = resultSet.getString("username");
					String description = resultSet.getString("description");
					LocalDate targetDate = resultSet.getDate("target_date").toLocalDate();
					boolean isDone = resultSet.getBoolean("is_done");
					
					todo = new Todo(id, title, username, description, targetDate, isDone);
			
				}
	
				} catch (SQLException e) {
			        
			        throw new RuntimeException("Failed to select todo with ID ", e);
			    }
	
		return todo;
		
	}
	
	
	public List<Todo> selectAllTodos(){
		
		List <Todo> todos = new ArrayList<>();
		
		try(Connection connection = JDBCUtils.getConnection();
				
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_TODOS)){
			
				ResultSet resultSet = preparedStatement.executeQuery();
				
				while(resultSet.next()) {
					
					long id = resultSet.getLong("id");
					String title = resultSet.getString("title");
					String username = resultSet.getString("username");
					String description = resultSet.getString("description");
					LocalDate targetDate = resultSet.getDate("target_date").toLocalDate();
					boolean isDone = resultSet.getBoolean("is_done");
					
					todos.add(new Todo(id, title, username, description, targetDate, isDone));
					
				}
			
		} catch (SQLException e) {
	        
	        throw new RuntimeException("Failed to select all todos for user with ID ", e);
	    }
		
		return todos;
	}
	
	
	public boolean deleteTodoById(int id){
		
		boolean rowDeleted = false;
		
	try(Connection connection = JDBCUtils.getConnection();
					
			PreparedStatement preparedStatement = connection.prepareStatement(DELETE_TODO_BY_ID)){
			preparedStatement.setInt(1, id);
			
			rowDeleted = preparedStatement.executeUpdate() > 0;
			
		} catch (SQLException e) {
	       
	        throw new RuntimeException("Failed to delete todo with ID ", e);
	    }
		
		return rowDeleted;
		
	}
	
	
	public boolean updateTodo(Todo todo){
		
		boolean rowUpdated = false;
		
		try(Connection connection = JDBCUtils.getConnection();
				
				PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_TODO)){
				
				preparedStatement.setString(1, todo.getTitle());
				preparedStatement.setString(2, todo.getUsername());
				preparedStatement.setString(3, todo.getDescription());
				preparedStatement.setDate(4, JDBCUtils.getSQLDate(todo.getTargetDate()));
				preparedStatement.setBoolean(5, todo.isStatus());
				preparedStatement.setLong(6, todo.getId());
				
				rowUpdated = preparedStatement.executeUpdate() > 0;
				
		}catch (SQLException e) {
	        
	        throw new RuntimeException("Failed to update todo with ID ", e);
	    }
		
		return rowUpdated;
		
	}
	
	
}
