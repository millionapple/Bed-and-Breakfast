package connection;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {
	public Connection getConnection() {
		Connection failedConn = null;
		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/twobitheadsbnb?user=TwoBitheads&password=TwoBitheadsBnB&serverTimezone=UTC")){
			return conn;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return failedConn;
}
}
