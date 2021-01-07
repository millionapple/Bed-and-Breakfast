

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Reservation;

/**
 * Servlet implementation class MyServlet
 */
@WebServlet("/MyServlet")
public class MyServlet extends HttpServlet {
	Reservation r = new Reservation();
	
	private static final long serialVersionUID = 1L;
    public MyServlet() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<html><body>");
		out.println("reservationId"+r.reservId+" Guest Name: "+r.getGuestName()+" Email: "+r.getEmail()+" Phone: "+r.getPhone());
		out.println("Arrival: "+r.getArrival()+" Departure: "+r.getDeparture()+" Rooms: "+r.getRooms()+" Price: "+r.getPrice());
		out.println("</body></html>");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		r.reservId +=1;
		r.setGuestName(request.getParameter("username"));
		r.setEmail(request.getParameter("email"));
		r.setPhone(request.getParameter("phone"));
		r.setArrival(request.getParameter("arrival"));
		r.setDeparture(request.getParameter("departure"));
		r.setRooms(Integer.parseInt(request.getParameter("rooms")));
		r.setPrice(r.getRooms()*100);
		doGet(request, response);
	}

}
