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
		ReservationDao rd = new ReservationDao();
		HashMap<Integer, Boolean> availableRooms = new HashMap<>();
		ArrayList<Reservation> rl = new ArrayList<>();
		try {
			 rl = rd.getAllReservations();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		availableRooms = checkAvailability(arrival, departure, rl);
		
		response.getWriter().append(availableRooms.toString());
	}
	
	private HashMap<Integer, Boolean> checkAvailability(String arrival, String departure,
			ArrayList<Reservation> reservations) {
		HashMap<Integer, Boolean> availability = new HashMap<>();
		try {
			LocalDate.parse(arrival);
			LocalDate.parse(departure);
		}catch(DateTimeParseException e) {
			System.out.println(e);
			IllegalArgumentException args = new IllegalArgumentException();
			throw args;
		}
		for (int i = 1; i <= 8; i++) {
			availability.put(i, true);
			for (Reservation reserv : reservations) {
				if (LocalDate.parse(reserv.getArrival()).isBefore(LocalDate.parse(departure))
						&& LocalDate.parse(reserv.getDeparture()).isAfter(LocalDate.parse(arrival))) {
					for (Rooms room : reserv.getRl()) {
						if (i == room.getRoomId()) {
							availability.put(i, false);
						}
					}
				}
			}
		}
		return availability;
	}

}
