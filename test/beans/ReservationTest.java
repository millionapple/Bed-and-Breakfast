package beans;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
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
		r1.getRl().add(room1);
		r1.getRl().add(room2);
		rl.add(r1);
		rl.add(r2);
		r3.setArrival("2021-01-05");
		r3.setDeparture("2021-01-09");
		r3.getRl().add(room3);
		boolean valid = r3.checkReserved(r3, rl);
		assertEquals(valid, false);
	}
}
