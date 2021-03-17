package beans;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;

public class ReservationTest {

	@Test
	public void testCompareDate() {
		LocalDate start = LocalDate.parse("2021-01-10");
		LocalDate end = LocalDate.parse("2021-02-01");
		long days = Period.between(start, end).getDays();
		assertEquals(days, 22);
	}
	
	@Test
	public void testGetPrice() {
		Reservation r = new Reservation();
		r.setArrival("2021-01-10");
		r.setDeparture("2021-02-01");
		r.setRooms(1);
		int price = (int) r.getPrice();
		assertEquals(price, 2300);
	}

	@Test
	public void testOverlappingDate() {
		List<Reservation> rl = new ArrayList<>();
		Reservation r1 = new Reservation();
		Reservation r2 = new Reservation();
		Reservation r3 = new Reservation();
		Rooms room1 = new Rooms();
		Rooms room2 = new Rooms();
		Rooms room3 = new Rooms();
		room1.setRoomId(1);
		room2.setRoomId(2);
		room3.setRoomId(3);
		r1.setArrival("2021-01-10");
		r1.setDeparture("2021-01-18");
		r1.getRl().add(room1);
		r1.getRl().add(room2);
		r2.setArrival("2021-01-08");
		r2.setDeparture("2021-01-17");
		r2.getRl().add(room3);
		rl.add(r1);
		rl.add(r2);
		r3.setArrival("2021-01-05");
		r3.setDeparture("2021-01-09");
		r3.getRl().add(room3);
		boolean valid = r3.checkReserved(r3, rl);
		assertEquals(valid, false);
	}

	@Test
	public void testNoConflictReserv() {
		// arrange
		String arrival = "2021-03-01";
		String departure = "2021-03-04";
		HashMap<Integer, Boolean> expected = new HashMap<Integer, Boolean>();
		expected.put(1, true);
		expected.put(2, true);
		expected.put(3, true);
		expected.put(4, true);
		expected.put(5, true);
		expected.put(6, true);
		expected.put(7, true);
		expected.put(8, true);
		
		ArrayList<Reservation> reservations = new ArrayList<Reservation>();

		// act
		HashMap<Integer, Boolean> actual = checkAvailability(arrival, departure, reservations);
		
		// assert
		assertEquals(expected, actual);
	}
	
	@Test
	public void testSingleConflictReserv() {
		// arrange
		String arrival = "2021-03-01";
		String departure = "2021-03-04";
		HashMap<Integer, Boolean> expected = new HashMap<Integer, Boolean>();
		for(int i = 1; i <= 8; i++) {
			expected.put(i, true);
		}
		expected.put(3, false);
		
		ArrayList<Reservation> reservations = new ArrayList<Reservation>();
		Reservation r1 = new Reservation();
		r1.setArrival(arrival);
		r1.setDeparture(departure);
		Rooms room3 = new Rooms();
		room3.setRoomId(3);
		r1.getRl().add(room3);
		reservations.add(r1);

		// act
		HashMap<Integer, Boolean> actual = checkAvailability(arrival, departure, reservations);
		
		// assert
		assertEquals(expected, actual);
	}

	@Test
	public void testNoOverlappingDate() {
		//the dates of the reservation should be different meaning that the values should be true
		// arrange
				String arrival = "2021-03-01";
				String departure = "2021-03-04";
				HashMap<Integer, Boolean> expected = new HashMap<Integer, Boolean>();
				for(int i = 1; i <= 8; i++) {
					expected.put(i, true);
				}
				
				ArrayList<Reservation> reservations = new ArrayList<Reservation>();
				Reservation r1 = new Reservation();
				r1.setArrival("2021-04-01");
				r1.setDeparture("2021-04-04");
				Rooms room3 = new Rooms();
				room3.setRoomId(3);
				r1.getRl().add(room3);
				reservations.add(r1);

				// act
				HashMap<Integer, Boolean> actual = checkAvailability(arrival, departure, reservations);
				
				// assert
				assertEquals(expected, actual);
	}
	
	@Test
	public void testMultipleConflicts() {
		//very close to testSingleConflictReserv but with multiple false instead of one
		// arrange
		String arrival = "2021-03-01";
		String departure = "2021-03-04";
		HashMap<Integer, Boolean> expected = new HashMap<Integer, Boolean>();
		for(int i = 1; i <= 8; i++) {
			expected.put(i, true);
		}
			expected.put(3, false);
			expected.put(5, false);
		
		ArrayList<Reservation> reservations = new ArrayList<Reservation>();
		Reservation r1 = new Reservation();
		r1.setArrival(arrival);
		r1.setDeparture(departure);
		Rooms room3 = new Rooms();
		Rooms room5 = new Rooms();
		room3.setRoomId(3);
		room5.setRoomId(5);
		r1.getRl().add(room3);
		r1.getRl().add(room5);
		reservations.add(r1);

		// act
		HashMap<Integer, Boolean> actual = checkAvailability(arrival, departure, reservations);
		
		// assert
		assertEquals(expected, actual);
	}
	
	@Test
	public void testAgainstMultipleReservations() {
		//have more that one reservation in the reservation list
	}
	
	@Test
	public void testMulipleReservationsWithDifferentRooms() {
		//have more than one reservation each with more that one room of different ids
	}
	
	private HashMap<Integer, Boolean> checkAvailability(String arrival, String departure, ArrayList<Reservation> reservations) {
		HashMap<Integer, Boolean> availability = new HashMap<>();
		
		for(int i = 1; i <= 8; i++) {
			availability.put(i, true);
			if(reservations.size() > 0) {
				Reservation reservation = reservations.get(0);
				if(LocalDate.parse(reservation.getArrival()).isBefore(LocalDate.parse(departure)) 
						&& LocalDate.parse(reservation.getDeparture()).isAfter(LocalDate.parse(arrival))) {
					for(int room = 0; room < reservation.getRl().size(); room++) {
						if(i == reservation.getRl().get(room).getRoomId()) {
							availability.put(i, false);
						}
					}
				}
			}
		}
		return availability;
	}
	
	
}
