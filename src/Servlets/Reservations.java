package Servlets;




import java.io.IOException;

import java.io.PrintWriter;
import java.util.ArrayList;

import java.util.List;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import beans.Reservation;
import beans.Rooms;
import dao.ReservationDao;

/**
 * Servlet implementation class MyServlet
 */
@WebServlet("/Reservations")
public class Reservations extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
    public Reservations() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter(); 
		ReservationDao reservationDao = new ReservationDao();
		List<Reservation> listOfReservations = new ArrayList<>();
		try {
			listOfReservations = reservationDao.getAllReservations();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			reservationDao.getReservationRooms(listOfReservations);
			String stringList ="{\"reservation\" : [";
			for(int i=0; i<listOfReservations.size(); i++) {
				stringList += listOfReservations.get(i).createJSON();
				if(i<listOfReservations.size()-1) {
					stringList +=", ";
				}
			}
			stringList +="]}";
			out.write(stringList);
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ReservationDao reservationDao = new ReservationDao();
		Reservation reservation = new Reservation();
		reservation.setGuestName(request.getParameter("username"));
		reservation.setEmail(request.getParameter("email"));
		reservation.setPhone(request.getParameter("phone"));
		reservation.setArrival(request.getParameter("arrival"));
		reservation.setDeparture(request.getParameter("departure"));
		int reservationId = 0;
		List<Reservation> listOfReservations = new ArrayList<>();
		for(int i = 1; i<=8; i++) {
			if(request.getParameter("room"+i) != null) {
				Rooms room = new Rooms();
				room.setRoomId(Integer.parseInt(request.getParameter("room"+i)));
				reservation.getRl().add(room);
			}
		}
		reservation.setRooms(reservation.getRl().size());
		try {
			listOfReservations = reservationDao.getAllReservations();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		try {
			reservationDao.getReservationRooms(listOfReservations);
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
		}
			try {
				reservationId = reservationDao.addReservation(reservation);
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e1) {
				e1.printStackTrace();
			}
			try {
				reservationDao.addRooms(reservationId, reservation.getRl());
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		response.sendRedirect("/DynamicTwoBitheads-BnB/MadeReservations.html");
	}

}
