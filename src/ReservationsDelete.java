

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
 * Servlet implementation class ReservationsDelete
 */
@WebServlet("/ReservationsDelete")
public class ReservationsDelete extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ReservationDao rd = new ReservationDao();
    public ReservationsDelete() {
        super();
        // TODO Auto-generated constructor stub
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		ReservationDao rd = new ReservationDao();
		Reservation r = new Reservation();
		List<Reservation> rl = new ArrayList<>();
		r.reservId = Integer.parseInt(request.getParameter("resId"));
		r.setGuestName(request.getParameter("username"));
		r.setEmail(request.getParameter("email"));
		r.setPhone(request.getParameter("phone"));
		r.setArrival(request.getParameter("arrival"));
		r.setDeparture(request.getParameter("departure"));
		for(int i = 1; i<=8; i++) {
			if(request.getParameter("room"+i) != null) {
				Rooms room = new Rooms();
				room.setRoomId(Integer.parseInt(request.getParameter("room"+i)));
				r.getRl().add(room);
			}
		}
		r.setRooms(r.getRl().size());
		try {
			rl = rd.getAllOtherReservations();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		if(r.checkReserved(r, rl)) {
			try {
				rd.updateReservation(r);
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
				e.printStackTrace();
			}
			try {
				rd.deleteRoom(Integer.toString(r.reservId));
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
				e.printStackTrace();
			}
			try {
				rd.updateRooms(r);
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		response.sendRedirect("MadeReservations.html");
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println(request.getParameter("id"));
		try {
			rd.deleteRoom(request.getParameter("id"));
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		try {
			rd.deleteReservation(request.getParameter("id"));
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
