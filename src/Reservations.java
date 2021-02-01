

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
		response.setContentType("text/html");
		PrintWriter out = response.getWriter(); 
		ReservationDao rd = new ReservationDao();
		List<Reservation> rl = new ArrayList<>();
		try {
			rl = rd.getAllReservations();
			out.println("<table>");
			out.println("<tr><th>Reservation id</th><th>Guest Name</th><th>Email</th><th>Phone</th><th>Arrival</th><th>Departure</th><th>Rooms</th><th>Days</th><th>Price</th><th>Delete</th><th>Update</th></tr>");
			for(Reservation r : rl) {
				out.println("<tr id='"+r.reservId+"'><td>"+r.reservId+"</td><td>"+r.getGuestName()+"</td><td>"+r.getEmail()+"</td><td>"+r.getPhone()+"</td>");
				out.println("<td>"+r.getArrival()+"</td><td>"+r.getDeparture()+"</td><td>"+r.getRooms()+"</td><td>"+r.getDays()+"</td><td>"+r.getPrice()+"</td>"
						+ "<td><button onclick=\"deleteReservation()\" id=\""+r.reservId+"\">Delete</button></td>"
						+ "<td><input type=\"button\" onClick=\"updateForm()\" value=\"Update\" id=\""+r.reservId+"\"/></td></tr>");
			}
			out.println("</table>");
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			rd.getReservationRooms(rl);
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ReservationDao rd = new ReservationDao();
		Reservation res = new Reservation();
		res.setGuestName(request.getParameter("username"));
		res.setEmail(request.getParameter("email"));
		res.setPhone(request.getParameter("phone"));
		res.setArrival(request.getParameter("arrival"));
		res.setDeparture(request.getParameter("departure"));
		int resid = 0;
		List<Reservation> rl = new ArrayList<>();
		for(int i = 1; i<=8; i++) {
			if(request.getParameter("room"+i) != null) {
				System.out.println("Room id: "+request.getParameter("room"+i));
				Rooms r = new Rooms();
				r.setRoomId(Integer.parseInt(request.getParameter("room"+i)));
				res.getRl().add(r);
			}
		}
		res.setRooms(res.getRl().size());
		try {
			rl = rd.getAllReservations();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		try {
			rd.getReservationRooms(rl);
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		if(res.checkReserved(res, rl)) {
			try {
				resid = rd.addReservation(res);
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e1) {
				e1.printStackTrace();
			}
			try {
				rd.addRooms(resid, res.getRl());
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		response.sendRedirect("/DynamicTwoBitheads-BnB/MadeReservations.html");
		}else {
			response.sendRedirect("/DynamicTwoBitheads-BnB/ReservationForm.html");
			System.out.println("room not aviable");
			response.setContentType("text/html");
			response.getWriter().println("Some or all the Rooms are occupied");
			response.addHeader("text", "Some or all the Rooms are occupied");
		}
	}

}
