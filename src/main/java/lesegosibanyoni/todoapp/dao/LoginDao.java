package lesegosibanyoni.todoapp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.mindrot.jbcrypt.BCrypt;

import lesegosibanyoni.todoapp.model.LoginBean;
import lesegosibanyoni.todoapp.utils.JDBCUtils;

public class LoginDao {
	
	public  final String SQL_QUERY = "SELECT * FROM users WHERE username = ?";
	

	public boolean validate(LoginBean loginBean) throws ClassNotFoundException{
			
		boolean status = false;

		try(Connection connection = JDBCUtils.getConnection();
				//Step 2: Create a statement using connection object
				PreparedStatement preparedStatement = connection.prepareStatement(SQL_QUERY)){
				preparedStatement.setString(1, loginBean.getUsername());

				//Execute the query and get the ResultSet
				ResultSet rs = preparedStatement.executeQuery();

				
				if(rs.next()) {

					//get the hashed password from the database
					String hashedPasswordFromDB = rs.getString("password");

					//hash the password entered by the user using the same hashing algorithm
					String passwordEnteredByUser = loginBean.getPassword();

					//compare the hashed password entered by the user with the password in the database using BCrypt
					if(BCrypt.checkpw(passwordEnteredByUser, hashedPasswordFromDB)) {
						status = true;
					}

				}



		} catch (SQLException e) {
			e.printStackTrace();
	    }

		return status;

	}

}
