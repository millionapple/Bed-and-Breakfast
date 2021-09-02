package beans;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import Servlets.CheckValidRooms;

public class ReservationTest {
	private static final String arrival = "2021-03-01";
	private static final String departure = "2021-03-04";

	HashMap<Integer, Boolean> expected = new HashMap<Integer, Boolean>();
	CheckValidRooms checkValidRooms = new CheckValidRooms();
	
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


	@Before
	public void setup() {
		expected = new HashMap<Integer, Boolean>();
		for (int i = 1; i <= 8; i++) {
			expected.put(i, true);
		}
	}
	
	@Test
	public void testNoConflictReserv() {
		// arrange

		ArrayList<Reservation> reservations = new ArrayList<Reservation>();

		// act
		HashMap<Integer, Boolean> actual = checkValidRooms.checkAvailability(arrival, departure, reservations);

		// assert
		assertEquals(expected, actual);
	}

	@Test
	public void testSingleConflictReserv() {
		// arrange
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
		HashMap<Integer, Boolean> actual = checkValidRooms.checkAvailability(arrival, departure, reservations);

		// assert
		assertEquals(expected, actual);
	}

	@Test
	public void testNoOverlappingDate() {
		// arrange

		ArrayList<Reservation> reservations = new ArrayList<Reservation>();
		Reservation r1 = new Reservation();
		r1.setArrival("2021-04-01");
		r1.setDeparture("2021-04-04");
		Rooms room3 = new Rooms();
		room3.setRoomId(3);
		r1.getRl().add(room3);
		reservations.add(r1);

		// act
		HashMap<Integer, Boolean> actual = checkValidRooms.checkAvailability(arrival, departure, reservations);

		// assert
		assertEquals(expected, actual);
	}

	@Test
	public void testMultipleConflicts() {
		// arrange
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
		HashMap<Integer, Boolean> actual = checkValidRooms.checkAvailability(arrival, departure, reservations);

		// assert
		assertEquals(expected, actual);
	}

	@Test
	public void testAgainstMultipleReservations() {
		// arrange
		expected.put(3, false);
		expected.put(5, false);

		ArrayList<Reservation> reservations = new ArrayList<Reservation>();
		// create reservation one
		Reservation r1 = new Reservation();
		r1.setArrival(arrival);
		r1.setDeparture(departure);
		// create reservation two
		Reservation r2 = new Reservation();
		r2.setArrival(arrival);
		r2.setDeparture(departure);
		// create room 3
		Rooms room3 = new Rooms();
		room3.setRoomId(3);
		// create room 5
		Rooms room5 = new Rooms();
		room5.setRoomId(5);
		// add rooms to reservations
		r1.getRl().add(room3);
		r2.getRl().add(room5);
		// add reservations to list
		reservations.add(r1);
		reservations.add(r2);

		// act
		HashMap<Integer, Boolean> actual = checkValidRooms.checkAvailability(arrival, departure, reservations);

		// assert
		assertEquals(expected, actual);
	}

	@Test
	public void testPartialDateRange() {
		expected.put(3, false);
		expected.put(5, false);
		
		ArrayList<Reservation> reservations = new ArrayList<Reservation>();
		
		Reservation r1 = new Reservation();
		r1.setArrival("2021-02-01");
		r1.setDeparture("2021-03-02");
		Rooms room3 = new Rooms();
		room3.setRoomId(3);
		r1.getRl().add(room3);
		Reservation r2 = new Reservation();
		r2.setArrival("2021-03-03");
		r2.setDeparture("2021-03-08");
		Rooms room5 = new Rooms();
		room5.setRoomId(5);
		r2.getRl().add(room5);
		reservations.add(r1);
		reservations.add(r2);
		
		//act
		HashMap<Integer, Boolean> actual = checkValidRooms.checkAvailability(arrival, departure, reservations);
		
		//assert
		assertEquals(expected, actual);
	}


    @Rule
    public ExpectedException exception = ExpectedException.none();
	
	@Test
	public void testInvalidArgs() {
		//so trying to make the date invalid will not cause the function to through an exception due to how it is setup it will see
		//no matching date and thus all rooms are available. Added only one date to the reservation that matched and then an invalid
		//one.
		exception.expect(IllegalArgumentException.class);
		ArrayList<Reservation> reservations = new ArrayList<Reservation>();
        
		checkValidRooms.checkAvailability("1982-24-01", "20", reservations);
	}
	

}
