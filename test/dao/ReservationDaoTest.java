package dao;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
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
	//add before and after to the testGet()
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
	public void testDeleteReservation() throws SQLException {
		ConnectionUtil connUtil = new ConnectionUtil();
		  Connection connection = connUtil.getConnection();
		  connection.setAutoCommit(false);
		  ReservationDao reservationDao = new ReservationDao(connection);
		boolean success;
		
		try {
			reservationDao.deleteReservation("1");
			success = true;
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			success = false;
			e.printStackTrace();
		}finally {
		    connection.rollback();
		    connection.close();
		  } 
		
		assertEquals(success, true);
	}
	

	@Test
	public void testAddReservation() throws Exception {
	  ConnectionUtil connUtil = new ConnectionUtil();
	  Connection connection = connUtil.getConnection();
	  connection.setAutoCommit(false);
	  ReservationDao reservationDao = new ReservationDao(connection);
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
		}finally {
		    connection.rollback();
		    connection.close();
		  } 
		
		assertEquals(success, true);  
	}
}
