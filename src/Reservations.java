

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

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
		ReservationDao rd = new ReservationDao();
		List<Reservation> rl = new ArrayList<>();
		try {
			rl = rd.getAllReservations();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			rd.getReservationRooms(rl);
			String stringList ="{\"reservation\" : [";
			for(int i=0; i<rl.size(); i++) {
				stringList += rl.get(i).createJSON();
				if(i<rl.size()-1) {
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
		}
	}

}
