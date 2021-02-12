package beans;


import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;



public class Reservation implements java.io.Serializable{
	public int reservId = 0;
	private String guestName;
	private String email;
	private String phone;
	private String arrival;
	private String departure;
	private int rooms;
	private double price;
	private long days;
	private int roomNum;
	private List<Rooms> rl = new ArrayList<>();
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
	public int getRoomNum() {
		return roomNum;
	}
	public void setRoomNum(int roomNum) {
		this.roomNum = roomNum;
	}
	public List<Rooms> getRl() {
		return rl;
	}
	public void setRl(List<Rooms> rl) {
		this.rl = rl;
	}

public boolean checkReserved(Reservation res, List<Reservation> rl) {
	List<Reservation> overlapReservation = new ArrayList<>();
	for(Reservation r : rl) {
		if(LocalDate.parse(r.getArrival()).isBefore(LocalDate.parse(res.getDeparture())) && LocalDate.parse(r.getDeparture()).isAfter(LocalDate.parse(res.getArrival()))) {
			System.out.println("Does Overlap");
			System.out.println("Reservations room size "+r.getRl().size());
			overlapReservation.add(r);
			System.out.println("Size of OverlapReservation "+overlapReservation.size());
		}else {
			System.out.println("Does not Overlap");
		}
	}
	System.out.println("now check rooms");
	for(Reservation r : overlapReservation) {
		System.out.println("overlap arrival "+r.arrival);
		for(Rooms resRoom : res.getRl()) {
			System.out.println("resRoom "+resRoom.getRoomId());
			System.out.println("Overlap Room size "+r.getRl().size());
			for(Rooms room : r.getRl()) {
				System.out.println("Maded Reservations "+room.getRoomId());
				System.out.println("To be made "+resRoom.getRoomId());
				if(resRoom.getRoomId() == room.getRoomId()) {
					return false;
				}
			}
		}
	}
	return true;
}

public String createJSON() {
	String roomIds ="[";
	for(int i = 0; i< rl.size(); i++) {
		roomIds+="\"";
		roomIds+=rl.get(i).getRoomId();
		if(i<rl.size()-1) {
		roomIds+="\" , ";
		}else {
			roomIds+="\"";
		}
	}
	roomIds+="]";
	String str ="{\"reservId\" : \""+reservId+"\","
			+ "\"guestName\" : \""+guestName+"\","
			+ "\"email\" : \""+email+"\","
			+ "\"phone\" : \""+phone+"\","
			+ "\"arrival\" : \""+arrival+"\","
			+ "\"departure\" : \""+departure+"\","
			+ "\"roomIds\" : "+roomIds+","
			+ "\"rooms\" : \""+rooms+"\","
			+ "\"days\" : \""+getDays()+"\","
			+ "\"price\" : \""+getPrice()+"\"}";
	return str;
}

}
