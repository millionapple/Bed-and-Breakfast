package dao;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;

import beans.Reservation;
import connection.ConnectionUtil;

public class ReservationDaoTest {
	
	@Test
	public void testBetterWayToConnect() {
		boolean valid = false;
		ConnectionUtil connUtil = new ConnectionUtil();
		Connection conn = connUtil.getConnection();
		try {
			valid = conn.isValid(10);
		} catch (SQLException e) {
			e.printStackTrace();
			e.printStackTrace();
		}
	assertEquals(valid, true);
	}
	
	@Test
	public void testGet() {
		Reservation r = new Reservation();
		try(Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/twobitheadsbnb?user=root&password=test123&serverTimezone=UTC")){
			PreparedStatement stmt = conn.prepareStatement("select guestName from reservations where idreservations = 1;");
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
			r.setGuestName(rs.getString("guestName"));
			}
		}catch (Exception e) {
			System.out.println(e);
		}
		assertEquals(r.getGuestName(), "Garrett");
	}
	
	

}
