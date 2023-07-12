package lesegosibanyoni.todoapp.utils;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;

public class JDBCUtils {
	
	private static final String CLASSNAME = "com.mysql.cj.jdbc.Driver";
	private static final String URL = "jdbc:mysql://localhost:3306/demo";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "12345";

	public static Connection getConnection() {
		Connection connection = null;

		try {
			Class.forName(CLASSNAME);
			connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		}catch(SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}

		return connection;

	}

	public static Date getSQLDate(LocalDate date) {
		return Date.valueOf(date);
	}

	public static LocalDate getUtilDate(Date sqlDate) {
		return sqlDate.toLocalDate();
	}

}
