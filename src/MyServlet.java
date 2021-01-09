

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
@WebServlet("/MyServlet")
public class MyServlet extends HttpServlet {
	Reservation r = new Reservation();
	ReservationDao rd = new ReservationDao();
    
	
	private static final long serialVersionUID = 1L;
    public MyServlet() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter(); 
		out.println("<html><body>");
		out.println("reservationId: "+r.reservId+"<br/> Guest Name: "+r.getGuestName()+"<br/> Email: "+r.getEmail()+"<br/> Phone: "+r.getPhone());
		out.println("<br/> Arrival: "+r.getArrival()+"<br/> Departure: "+r.getDeparture()+"<br/> Rooms: "+r.getRooms()+"<br/> Price: "+r.getPrice());
		out.println("</body></html>"); 
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		r.reservId =2;
		r.setGuestName(request.getParameter("username"));
		r.setEmail(request.getParameter("email"));
		r.setPhone(request.getParameter("phone"));
		r.setArrival(request.getParameter("arrival"));
		r.setDeparture(request.getParameter("departure"));
		r.setRooms(Integer.parseInt(request.getParameter("rooms")));
		r.setPrice(r.getRooms()*100);
		//change to ReservationDao.addReservation when I can connect to the driver from packages
		Tests t = new Tests();
		t.testPost();
	}

}
