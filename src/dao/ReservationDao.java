package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.Reservation;
import connection.ConnectionUtil;
import logs.LogUtil;

public class ReservationDao {
	//for some reason that I don't know yet it will not connect to the driver
	public void addReservation(Reservation r) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		try(Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/twobitheadsbnb?user=TwoBitheads&password=TwoBitheadsBnB&serverTimezone=UTC")){
			conn.setAutoCommit(false);
			PreparedStatement stmt = conn.prepareStatement("Insert into reservations(idreservations, guestname, email, phone, arrival, departure, rooms, price)\r\n" + 
					"values(?, ?, ?, ?, ?, ?, ?, ?);");
			stmt.setInt(1, r.reservId);
			stmt.setString(2, r.getGuestName());
			stmt.setString(3, r.getEmail());
			stmt.setString(4, r.getPhone());
			stmt.setString(5, r.getArrival());
			stmt.setString(6, r.getDeparture());
			stmt.setInt(7, r.getRooms());
			stmt.setDouble(8, r.getPrice());
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
	
	public List<Reservation> getAllReservations(){
		List<Reservation> rl = new ArrayList<>();
		
		return rl;
	}
}


