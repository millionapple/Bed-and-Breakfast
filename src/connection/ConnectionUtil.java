package connection;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.junit.Test;

public class ConnectionUtil {
	public Connection getConnection() {
		Connection conn = null;
		try {
			 Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/twobitheadsbnb?user=TwoBitheads&password=TwoBitheadsBnB&serverTimezone=UTC");
			System.out.println(conn.isValid(10));
		} catch (Exception e) { 
			e.printStackTrace();
		} 
		return conn;
}
}
