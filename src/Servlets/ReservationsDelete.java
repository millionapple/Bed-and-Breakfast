package Servlets;


import java.io.IOException;
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
 * Servlet implementation class ReservationsDelete
 * change name later to reservationServletThatHandlesDeletes
 */
@WebServlet("/ReservationsDelete")
public class ReservationsDelete extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ReservationDao reservationDao = new ReservationDao();
    public ReservationsDelete() {
        super();
        // TODO Auto-generated constructor stub
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		ReservationDao reservationDao = new ReservationDao();
		Reservation reservation = new Reservation();
		List<Reservation> listOfReservations = new ArrayList<>();
		reservation.reservId = Integer.parseInt(request.getParameter("resId"));
		reservation.setGuestName(request.getParameter("username"));
		reservation.setEmail(request.getParameter("email"));
		reservation.setPhone(request.getParameter("phone"));
		reservation.setArrival(request.getParameter("arrival"));
		reservation.setDeparture(request.getParameter("departure"));
		for(int i = 1; i<=8; i++) {
			if(request.getParameter("room"+i) != null) {
				Rooms room = new Rooms();
				room.setRoomId(Integer.parseInt(request.getParameter("room"+i)));
				reservation.getRl().add(room);
			}
		}
		reservation.setRooms(reservation.getRl().size());
		try {
			listOfReservations = reservationDao.getAllOtherReservations();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		if(reservation.checkReserved(reservation, listOfReservations)) {
			try {
				reservationDao.updateReservation(reservation);
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
				e.printStackTrace();
			}
			try {
				reservationDao.deleteRoom(Integer.toString(reservation.reservId));
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
				e.printStackTrace();
			}
			try {
				reservationDao.updateRooms(reservation);
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		response.sendRedirect("MadeReservations.html");
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println(request.getParameter("id"));
		try {
			reservationDao.deleteRoom(request.getParameter("id"));
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		try {
			reservationDao.deleteReservation(request.getParameter("id"));
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
