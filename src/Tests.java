import static org.junit.Assert.assertEquals;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import beans.Reservation;
import beans.Rooms;

public class Tests {
	
	@Test
	public void testPost() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		try(Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/twobitheadsbnb?userSSL=false", "TwoBitheads", "TwoBitheadsBnB")){
			conn.setAutoCommit(false);
			PreparedStatement stmt = conn.prepareStatement("Insert into reservations(guestname, email, phone, arrival, departure, rooms, price)\r\n" + 
					"values(?, ?, ?, ?, ?, ?, ?);");
			stmt.setString(1, "Garrett");
			stmt.setString(2, "my@email.com");
			stmt.setString(3, "098-765-4321");
			stmt.setString(4, "2021/01/07");
			stmt.setString(5, "2021/01/12");
			stmt.setInt(6, 2);
			stmt.setDouble(7, 200);
			int rowsUpdated = stmt.executeUpdate();
			if (rowsUpdated > 0) {
				System.out.println("The comment request was successful!");
				conn.commit();
			} else {
				System.out.println("comment was not successful");
				conn.rollback();
			}
		}catch (Exception e) {
			System.out.println(e);
		}
	}
	
}
