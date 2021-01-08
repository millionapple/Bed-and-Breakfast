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
	public void addReservation(Reservation r) {
		try(Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/twobitheadsbnb?user=TwoBitheads&password=TwoBitheadsBnB&serverTimezone=UTC")){
			conn.setAutoCommit(false);
			PreparedStatement stmt = conn.prepareStatement("Insert into reservations(idreservations, guestname, email, phone, arrival, departure, rooms, price)\r\n" + 
					"values(?, ?, ?, ?, ?, ?, ?, ?);");
			stmt.setInt(1, 4);
			stmt.setString(2, "Garrett");
			stmt.setString(3, "my@email.com");
			stmt.setString(4, "098-765-4321");
			stmt.setString(5, "2021-01-07");
			stmt.setString(6, "2021-01-12");
			stmt.setInt(7, 2);
			stmt.setDouble(8, 200);
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


