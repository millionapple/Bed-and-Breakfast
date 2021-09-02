package connection;


import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionUtil {
	public Connection getConnection() {
		Connection conn = null;
		try {
			 Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/twobitheadsbnb?user=root&password=test123&serverTimezone=UTC");
			System.out.println(conn.isValid(10));
			conn.setAutoCommit(false);
		} catch (Exception e) { 
			e.printStackTrace();
		} 
		return conn;
}
}
