package beans;

import dao.ReservationDao;

public class Reservation {
	String guestName;
	String email;
	String phone;
	String arrival;
	String departure;
	int rooms;
	double price;
	
	//should be called from front end with the information needed to complete the transaction
	//this then calls a method in ReservationDao that will store the information on the database
	public void addReservation() {
		ReservationDao rd = new ReservationDao();
		price = rooms *100;
		rd.addReservation(guestName, email, phone, arrival, departure, rooms, price);
	}
}
