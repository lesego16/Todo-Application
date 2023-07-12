package lesegosibanyoni.todoapp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import lesegosibanyoni.todoapp.model.User;
import lesegosibanyoni.todoapp.utils.JDBCUtils;

public class UserDao {
	
	public int registerEmployee(User employee) throws Exception {

		String INSERT_USERS_SQL =
				"INSERT INTO users (first_name, last_name, username, password) VALUES (?, ?, ?, ?)";
		int result = 0;

		try(Connection connection = JDBCUtils.getConnection();
			//Step 2: create a statement using connection object
				PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS_SQL)){
				preparedStatement.setString(1, employee.getFirstName());
				preparedStatement.setString(2, employee.getLastName());
				preparedStatement.setString(3, employee.getUsername());
				preparedStatement.setString(4, employee.getPassword());

			//Step 3: Execute the query or update query
				result = preparedStatement.executeUpdate();
		}catch (SQLException e) {
				e.printStackTrace();
		}

		return result;

	}

}
