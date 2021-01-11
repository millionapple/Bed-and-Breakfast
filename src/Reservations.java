

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Reservation;
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
		out.println("<html><body>");
		out.println("reservationId: "+"<br/> Guest Name: "+"<br/> Email: "+"<br/> Phone: ");
		out.println("<br/> Arrival: "+"<br/> Departure: "+"<br/> Rooms: "+"<br/> Price: ");
		out.println("</body></html>"); 
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		r.setGuestName(request.getParameter("username"));
		r.setEmail(request.getParameter("email"));
		r.setPhone(request.getParameter("phone"));
		r.setArrival(request.getParameter("arrival"));
		r.setDeparture(request.getParameter("departure"));
		r.setRooms(Integer.parseInt(request.getParameter("rooms")));
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
