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

public class Tests {
	boolean valid = false;
	@Test
	public void testConnection() {
		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/twobitheadsbnb?user=TwoBitheads&password=TwoBitheadsBnB&serverTimezone=UTC")){
		valid = conn.isValid(10);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		assertEquals(valid, true);
	}
	
	@Test
	public void testGet() {
		Reservation r = new Reservation();
		try(Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/twobitheadsbnb?user=TwoBitheads&password=TwoBitheadsBnB&serverTimezone=UTC")){
			PreparedStatement stmt = conn.prepareStatement("select guestName from reservations;");
			ResultSet rs = stmt.executeQuery();
			System.out.println(conn.isValid(10));
			System.out.println(rs);
			while(rs.next()) {
				r.setGuestName(rs.getString("guestName"));
				System.out.println(r.getGuestName());
			}
		}catch (Exception e) {
			System.out.println(e);
		}
		assertEquals(r.getGuestName(), "Garrett");
	}
	
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

	@Test
	public void testCompareDate() {
		LocalDate start = LocalDate.parse("2021-01-10");
		LocalDate end = LocalDate.parse("2021-02-01");
		long days = Period.between(start, end).getDays();
		assertEquals(days, 22);
	}

	@Test
	public void testOverlappingDate() {
		List<Reservation> rl = new ArrayList<>();
		Reservation r1 = new Reservation();
		Reservation r2 = new Reservation();
		Reservation r3 = new Reservation();
		Reservation r4 = new Reservation();
		r1.setArrival("2021-01-10");
		r1.setDeparture("2021-01-18");
		r2.setArrival("2021-01-08");
		r2.setDeparture("2021-01-17");
		rl.add(r1);
		rl.add(r2);
		r3.setArrival("2021-01-05");
		r3.setDeparture("2021-01-09");
		r4.setArrival("2021-01-21");
		r4.setDeparture("2021-01-28");
		for(Reservation r : rl) {
			if(LocalDate.parse(r.getArrival()).isBefore(LocalDate.parse(r3.getDeparture())) && LocalDate.parse(r.getDeparture()).isAfter(LocalDate.parse(r3.getArrival()))) {
				System.out.println("They overlap");
			}else {
				System.out.println("Does not overlap");
			}
		}
	}
}
