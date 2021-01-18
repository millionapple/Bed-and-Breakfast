package beans;


import java.time.LocalDate;
import java.time.Period;



public class Reservation {
	public int reservId = 0;
	private String guestName;
	private String email;
	private String phone;
	private String arrival;
	private String departure;
	private int rooms;
	private double price;
	private long days;
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
		long days = getDays();
		price = (days*100)*rooms;
		return price;
	}
	public long getDays() {
		LocalDate start = LocalDate.parse(arrival);
		LocalDate end = LocalDate.parse(departure);
		long days = Period.between(start, end).getDays();
		return days;
	}
	public void setDays(long days) {
		this.days = days;
	}
}
