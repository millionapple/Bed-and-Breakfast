package dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import beans.Reservation;
import connection.ConnectionUtil;
import logs.LogUtil;

public class ReservationDao {
	private static ConnectionUtil cu = new ConnectionUtil();
	//add the data types that go into add reservation
	public void addReservation(String guestName, String email, String phone, String arrival, String departure, int rooms, double price) {
		Connection conn = cu.getConnection();
		try {
			conn.setAutoCommit(false);
			//add the sql and transaction
		}catch (SQLException e) {
			LogUtil.rollback(e, conn, ReservationDao.class);
	}
}
	
	public List<Reservation> getAllReservations(){
		List<Reservation> rl = new ArrayList<>();
		
		return rl;
	}
}


