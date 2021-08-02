package dao;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

import beans.Reservation;
import connection.ConnectionUtil;

public class ReservationDaoTest {
	@Test
	public void testBetterWayToConnect() {
		boolean valid = false;
		ConnectionUtil connUtil = new ConnectionUtil();
		try(Connection conn = connUtil.getConnection()) {
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
		ConnectionUtil connUtil = new ConnectionUtil();
		try(Connection conn = connUtil.getConnection()){
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
	
	@Test
	public void testAddReservation() {
		ReservationDao reservationDao = new ReservationDao();
		Reservation reservation = new Reservation();
		reservation.setGuestName("Garrett");
		reservation.setEmail("garrett@mail.com");
		reservation.setPhone("123-456-7890");
		reservation.setArrival("2021-08-02");
		reservation.setDeparture("2021-08-05");
		reservation.setRooms(1);
		boolean success;
		
		try {
			reservationDao.addReservation(reservation);
			success = true;
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			success = false;
			e.printStackTrace();
		}
		
		assertEquals(success, true);
	}
	@Before
	public void init() {
		ReservationDao reservationDao = new ReservationDao();
		Reservation reservation = new Reservation();
		reservation.setGuestName("test");
		reservation.setEmail("test@mail.com");
		reservation.setPhone("123-456-7890");
		reservation.setArrival("2021-08-02");
		reservation.setDeparture("2021-08-05");
		reservation.setRooms(1);
		try {
			reservationDao.addReservation(reservation);
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testDeleteReservation() {
		ReservationDao reservationDao = new ReservationDao();
		boolean success;
		try {
			reservationDao.deleteReservation("");
			success = true;
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			success = false;
			e.printStackTrace();
		}
		
		assertEquals(success, true);
	}
	

}
