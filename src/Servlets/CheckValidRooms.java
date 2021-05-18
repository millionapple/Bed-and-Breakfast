package Servlets;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import beans.Reservation;
import beans.Rooms;
import dao.ReservationDao;

/**
 * Servlet implementation class CheckValidRooms
 */
@WebServlet("/CheckValidRooms")
public class CheckValidRooms extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public CheckValidRooms() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String arrival = request.getParameter("arrival");
		String departure = request.getParameter("departure");
		ReservationDao reservationDao = new ReservationDao();
		HashMap<Integer, Boolean> availableRooms = new HashMap<>();
		ArrayList<Reservation> listOfReservations = new ArrayList<>();
		try {
			 listOfReservations = reservationDao.getAllReservations();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			reservationDao.getReservationRooms(listOfReservations);
		} catch(InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		availableRooms = checkAvailability(arrival, departure, listOfReservations);
		String isRoomJson = "{ \"isAvailable\": [";
		for(int i = 1; i <= availableRooms.size(); i++) {
			if(i != 8) {
			isRoomJson += "{\""+i+"\": \""+availableRooms.get(i)+"\"},";
			}else {
				isRoomJson += "{\""+i+"\": \""+availableRooms.get(i)+"\"}]}";
			}
		}
		System.out.println(isRoomJson);
		response.getWriter().write(isRoomJson);
	}
	
	public HashMap<Integer, Boolean> checkAvailability(String arrival, String departure,
			ArrayList<Reservation> reservations) {
		HashMap<Integer, Boolean> availability = new HashMap<>();
		validateDates(arrival, departure);
		for (int roomNumber = 1; roomNumber <= 8; roomNumber++) {
			availability.put(roomNumber, true);
			for (Reservation reserv : reservations) {
				if (reservationDateRangeOverlapping(arrival, departure, reserv)) {
					updateRoomAvailability(availability, roomNumber, reserv);
				}
			}
		}
		return availability;
	}

	private void updateRoomAvailability(HashMap<Integer, Boolean> availability, int roomNumber, Reservation reserv) {
		for (Rooms room : reserv.getRl()) {
			if (roomNumber == room.getRoomId()) {
				availability.put(roomNumber, false);
			}
		}
	}

	private boolean reservationDateRangeOverlapping(String arrival, String departure, Reservation reserv) {
		return LocalDate.parse(reserv.getArrival()).isBefore(LocalDate.parse(departure))
				&& LocalDate.parse(reserv.getDeparture()).isAfter(LocalDate.parse(arrival));
	}

	private void validateDates(String arrival, String departure) {
		try {
			LocalDate.parse(arrival);
			LocalDate.parse(departure);
		}catch(DateTimeParseException e) {
			System.out.println(e);
			IllegalArgumentException args = new IllegalArgumentException();
			throw args;
		}
	}
	
	

}
