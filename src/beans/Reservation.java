package beans;

import dao.ReservationDao;

public class Reservation {
	private String guestName;
	private String email;
	private String phone;
	private String arrival;
	private String departure;
	private int rooms;
	private double price;
	public String getGuestName() {
		return guestName;
	}
	public void setGuestName(String guestName) {
		this.guestName = guestName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getArrival() {
		return arrival;
	}
	public void setArrival(String arrival) {
		this.arrival = arrival;
	}
	public String getDeparture() {
		return departure;
	}
	public void setDeparture(String departure) {
		this.departure = departure;
	}
	public int getRooms() {
		return rooms;
	}
	public void setRooms(int rooms) {
		this.rooms = rooms;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	
	//should be called from front end with the information needed to complete the transaction
	//this then calls a method in ReservationDao that will store the information on the database
//	public void addReservation() {
//		ReservationDao rd = new ReservationDao();
//		price = rooms *100;
//		rd.addReservation(guestName, email, phone, arrival, departure, rooms, price);
//	}
}
