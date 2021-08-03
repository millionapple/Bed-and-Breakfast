package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import beans.Reservation;
import beans.Rooms;
import connection.ConnectionUtil;

public class ReservationDao {
	public ReservationDao() {
		
	}
	public ReservationDao(Connection conn) {
		// TODO Auto-generated constructor stub
	}

	public int addReservation(Reservation reservation) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		int resid = 0;
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		ConnectionUtil connUtil = new ConnectionUtil();
		try(Connection conn = connUtil.getConnection()){
			conn.setAutoCommit(false);
			PreparedStatement stmt = conn.prepareStatement("Insert into reservations( guestname, email, phone, arrival, departure, rooms, price, days)\r\n" + 
					"values(?,?,?,?,?,?,?,?);", Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, reservation.getGuestName());
			stmt.setString(2, reservation.getEmail());
			stmt.setString(3, reservation.getPhone());
			stmt.setString(4, reservation.getArrival());
			stmt.setString(5, reservation.getDeparture());
			stmt.setInt(6, reservation.getRooms());
			stmt.setDouble(7, reservation.getPrice());
			stmt.setInt(8, (int) reservation.getDays());
			int rowsUpdated = stmt.executeUpdate();
			ResultSet res = stmt.getGeneratedKeys();
			while(res.next()) {
			resid = Integer.parseInt(res.getString(1));
			}
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
		return resid;
}
	
	public ArrayList<Reservation> getAllReservations() throws InstantiationException, IllegalAccessException, ClassNotFoundException{
		ArrayList<Reservation> listOfReservationsFromDatabase = new ArrayList<>();
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		try(Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/twobitheadsbnb?user=root&password=test123&serverTimezone=UTC")){
			PreparedStatement stmt = conn.prepareStatement("select * from reservations");
			ResultSet resultSet = stmt.executeQuery();
			while(resultSet.next()) {
				Reservation reservationFromDatabase = new Reservation();
				 DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				 dateFormat.setTimeZone(TimeZone.getTimeZone("CT"));
				reservationFromDatabase.reservId = resultSet.getInt("idreservations");
				reservationFromDatabase.setGuestName(resultSet.getString("guestname"));
				reservationFromDatabase.setEmail(resultSet.getString("email"));
				reservationFromDatabase.setPhone(resultSet.getString("phone"));
				reservationFromDatabase.setArrival(dateFormat.format(resultSet.getDate("arrival")));
				reservationFromDatabase.setDeparture(dateFormat.format(resultSet.getDate("departure")));
				reservationFromDatabase.setRooms(resultSet.getInt("rooms"));
				listOfReservationsFromDatabase.add(reservationFromDatabase);
			}
		}catch(Exception e){
			System.out.println(e);
		}
		
		return listOfReservationsFromDatabase;
	}

	public List<Reservation> getAllOtherReservations() throws InstantiationException, IllegalAccessException, ClassNotFoundException{
		List<Reservation> listOfAllOtherReservations = new ArrayList<>();
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		try(Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/twobitheadsbnb?user=root&password=test123&serverTimezone=UTC")){
			PreparedStatement stmt = conn.prepareStatement("select * from reservations where idreservations != ?;");
			ResultSet resultSet = stmt.executeQuery();
			while(resultSet.next()) {
				Reservation reservationFromDatabase = new Reservation();
				 DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				 dateFormat.setTimeZone(TimeZone.getTimeZone("CT"));
				reservationFromDatabase.reservId = resultSet.getInt("idreservations");
				reservationFromDatabase.setGuestName(resultSet.getString("guestname"));
				reservationFromDatabase.setEmail(resultSet.getString("email"));
				reservationFromDatabase.setPhone(resultSet.getString("phone"));
				reservationFromDatabase.setArrival(dateFormat.format(resultSet.getDate("arrival")));
				reservationFromDatabase.setDeparture(dateFormat.format(resultSet.getDate("departure")));
				reservationFromDatabase.setRooms(resultSet.getInt("rooms"));
				listOfAllOtherReservations.add(reservationFromDatabase);
			}
		}catch(Exception e){
			System.out.println(e);
		}
		
		return listOfAllOtherReservations;
	}
	public void deleteReservation(String reservationId) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		try(Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/twobitheadsbnb?user=root&password=test123&serverTimezone=UTC")){
			PreparedStatement stmt = conn.prepareStatement("DELETE FROM `twobitheadsbnb`.`reservations` WHERE (`idreservations` = ?);");
			stmt.setString(1, reservationId);
			System.out.println(reservationId);
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

	public void updateReservation(Reservation reservationToSendToDatabase) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		try(Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/twobitheadsbnb?user=root&password=test123&serverTimezone=UTC")){
			conn.setAutoCommit(false);
			PreparedStatement stmt = conn.prepareStatement("UPDATE `twobitheadsbnb`.`reservations` SET `guestname` = ?, `email` = ?, `phone` = ?, `arrival` = ?, `departure` = ?, `rooms` = ?, `price` = ? WHERE (`idreservations` = ?);");
			stmt.setString(1, reservationToSendToDatabase.getGuestName());
			stmt.setString(2, reservationToSendToDatabase.getEmail());
			stmt.setString(3, reservationToSendToDatabase.getPhone());
			stmt.setString(4, reservationToSendToDatabase.getArrival());
			stmt.setString(5, reservationToSendToDatabase.getDeparture());
			stmt.setInt(6, reservationToSendToDatabase.getRooms());
			stmt.setDouble(7, reservationToSendToDatabase.getPrice());
			stmt.setInt(8, reservationToSendToDatabase.reservId);
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

	public void addRooms(int reservationId, List<Rooms> listOfRoomsInReservation) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		try(Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/twobitheadsbnb?user=root&password=test123&serverTimezone=UTC")){
			conn.setAutoCommit(false);
			for(Rooms r :listOfRoomsInReservation) {
			PreparedStatement stmt = conn.prepareStatement("insert into `twobitheadsbnb`.`reserv_rooms` (`reservid`, `roomid`) \r\n" + 
					"values (?,?);");
			stmt.setInt(1, reservationId);
			stmt.setInt(2, r.getRoomId());
			int rowsUpdated = stmt.executeUpdate();
			if (rowsUpdated > 0) {
				System.out.println("The comment request was successful!");
				conn.commit();
			} else {
				System.out.println("comment was not successful");
				conn.rollback();
			}
			}
	}catch (Exception e) {
		System.out.println(e);
	}
}
	public void deleteRoom(String reservationId) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		ConnectionUtil connUtil = new ConnectionUtil();
		try(Connection conn = connUtil.getConnection()){
			PreparedStatement stmt = conn.prepareStatement("DELETE FROM `twobitheadsbnb`.`reserv_rooms` WHERE (`reservid` = ?);");
			stmt.setString(1, reservationId);
			System.out.println(reservationId);
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
	//I Think I found the Problem it is putting all of the Rooms in all reservations
	public void getReservationRooms(List<Reservation> listOfReservations) throws InstantiationException, IllegalAccessException, ClassNotFoundException{
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		try(Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/twobitheadsbnb?user=root&password=test123&serverTimezone=UTC")){
			for(Reservation reservation : listOfReservations) {
				List<Rooms> roomList = new ArrayList<>();
				PreparedStatement stmt = conn.prepareStatement("select roomid from reserv_rooms where reservid = ?;");
				stmt.setInt(1, reservation.reservId);
				ResultSet resultSet = stmt.executeQuery();
				while(resultSet.next()) {
					Rooms rooms = new Rooms();
					rooms.setRoomId(resultSet.getInt("roomid"));
					roomList.add(rooms);
				}
				reservation.setRl(roomList);
			}
		}catch(Exception e){
			System.out.println(e);
		}
	}

	public void updateRooms(Reservation reservation) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		try(Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/twobitheadsbnb?user=root&password=test123&serverTimezone=UTC")){
			conn.setAutoCommit(false);
			for(Rooms room : reservation.getRl()) {
			PreparedStatement stmt = conn.prepareStatement("insert into `twobitheadsbnb`.`reserv_rooms` (`reservid`, `roomid`) \r\n" + 
					"values (?,?);");
			stmt.setInt(1, reservation.reservId);
			stmt.setInt(2, room.getRoomId());
			int rowsUpdated = stmt.executeUpdate();
			if (rowsUpdated > 0) {
				System.out.println("The comment request was successful!");
				conn.commit();
			} else {
				System.out.println("comment was not successful");
				conn.rollback();
			}
			}
	}catch (Exception e) {
		System.out.println(e);
	}
	}
}


