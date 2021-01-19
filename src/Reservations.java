

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
	Reservation r = new Reservation();
	ReservationDao rd = new ReservationDao();
    
	
	private static final long serialVersionUID = 1L;
    public Reservations() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter(); 
		ReservationDao rd = new ReservationDao();
		List<Reservation> rl;
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
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		r.setGuestName(request.getParameter("username"));
		r.setEmail(request.getParameter("email"));
		r.setPhone(request.getParameter("phone"));
		r.setArrival(request.getParameter("arrival"));
		r.setDeparture(request.getParameter("departure"));
		List<Rooms> rl = new ArrayList<>();
		for(int i = 1; i<=8; i++) {
			if(request.getParameter("room"+i) != null) {
				System.out.println(request.getParameter("room"+i));
				Rooms r = new Rooms();
				r.setRoomId(Integer.parseInt(request.getParameter("room"+i)));
				rl.add(r);
			}
		}
		r.setRooms(rl.size());
		try {
			rd.addReservation(r);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		response.sendRedirect("/DynamicTwoBitheads-BnB/MadeReservations.html");
	}

}
