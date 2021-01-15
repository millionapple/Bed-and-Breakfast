package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import beans.Reservation;

public class ReservationDao {
	//for some reason that I don't know yet it will not connect to the driver
	public void addReservation(Reservation r) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		try(Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/twobitheadsbnb?user=TwoBitheads&password=TwoBitheadsBnB&serverTimezone=UTC")){
			conn.setAutoCommit(false);
			PreparedStatement stmt = conn.prepareStatement("Insert into reservations( guestname, email, phone, arrival, departure, rooms, price)\r\n" + 
					"values(?,?,?,?,?,?,?);");
			stmt.setString(1, r.getGuestName());
			stmt.setString(2, r.getEmail());
			stmt.setString(3, r.getPhone());
			stmt.setString(4, r.getArrival());
			stmt.setString(5, r.getDeparture());
			stmt.setInt(6, r.getRooms());
			stmt.setDouble(7, r.getPrice());
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
	
	public List<Reservation> getAllReservations() throws InstantiationException, IllegalAccessException, ClassNotFoundException{
		List<Reservation> rl = new ArrayList<>();
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		try(Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/twobitheadsbnb?user=TwoBitheads&password=TwoBitheadsBnB&serverTimezone=UTC")){
			PreparedStatement stmt = conn.prepareStatement("select * from reservations");
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				Reservation r = new Reservation();
				 DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				 
				r.reservId = rs.getInt("idreservations");
				r.setGuestName(rs.getString("guestname"));
				r.setEmail(rs.getString("email"));
				r.setPhone(rs.getString("phone"));
				r.setArrival(dateFormat.format(rs.getDate("arrival")));
				r.setDeparture(dateFormat.format(rs.getDate("departure")));
				r.setRooms(rs.getInt("rooms"));
				rl.add(r);
			}
		}catch(Exception e){
			System.out.println(e);
		}
		
		return rl;
	}

	public void deleteReservation(String resId) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		try(Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/twobitheadsbnb?user=TwoBitheads&password=TwoBitheadsBnB&serverTimezone=UTC")){
			PreparedStatement stmt = conn.prepareStatement("DELETE FROM `twobitheadsbnb`.`reservations` WHERE (`idreservations` = ?);");
			stmt.setString(1, resId);
			System.out.println(resId);
			int rowsUpdated = stmt.executeUpdate();
			if (rowsUpdated > 0) {
				System.out.println("The comment request was successful!");
				conn.commit();
			} else {
				System.out.println("comment was not successful");
				conn.rollback();
			}
		}catch(Exception e){
			System.out.println(e);
		}
	}

	public void updateReservation(Reservation r) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		try(Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/twobitheadsbnb?user=TwoBitheads&password=TwoBitheadsBnB&serverTimezone=UTC")){
			conn.setAutoCommit(false);
			PreparedStatement stmt = conn.prepareStatement("UPDATE `twobitheadsbnb`.`reservations` SET `guestname` = ?, `email` = ?, `phone` = ?, `arrival` = ?, `departure` = ?, `rooms` = ?, `price` = ? WHERE (`idreservations` = ?);");
			stmt.setString(1, r.getGuestName());
			stmt.setString(2, r.getEmail());
			stmt.setString(3, r.getPhone());
			stmt.setString(4, r.getArrival());
			stmt.setString(5, r.getDeparture());
			stmt.setInt(6, r.getRooms());
			stmt.setDouble(7, r.getPrice());
			stmt.setInt(8, r.reservId);
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


