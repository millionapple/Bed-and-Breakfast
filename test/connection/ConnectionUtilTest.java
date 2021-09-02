package connection;

import static org.junit.Assert.assertEquals;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.junit.Test;

public class ConnectionUtilTest {
	boolean valid = false;
	@Test
	public void testConnection() {
		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/twobitheadsbnb?user=root&password=test123&serverTimezone=UTC")){
		valid = conn.isValid(10);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		assertEquals(valid, true);
	}
}
