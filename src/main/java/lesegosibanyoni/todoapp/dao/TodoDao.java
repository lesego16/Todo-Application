package lesegosibanyoni.todoapp.dao;


import java.util.List;

import lesegosibanyoni.todoapp.model.Todo;

public interface TodoDao{
	
	void insertTodo(Todo todo);

	Todo selectTodoById(long todoId);

	List<Todo> selectAllTodos();

	boolean deleteTodoById(int id);

	boolean updateTodo(Todo todo);
	

}
