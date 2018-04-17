package xw;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

@Path("/ReservationService")
public class ReservationService {
	private static final String DELETE_SUCCESS = "delete is success for reservation with id: ";
	private static final String RESERVATION_NOT_EXIST = "no reservation exists for id:  ";
	private static int reservationId = 1001;
	ReservationDao reservationDao = new ReservationDao();
	ProjectorDao projectorDao = new ProjectorDao();
	SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat timeFormatter = new SimpleDateFormat("hh:mm");
	List<Projector> listProjector = projectorDao.getAllProjectors();
	@GET
	@Path("/reservations")
    @Produces(MediaType.APPLICATION_JSON)
	// This method provides access to all the reservations
	public List<Reservation> getAllReservations(){
		return reservationDao.getAllReservations();
	}
	@GET
	@Path("/reservations/{reservationId}")
	@Produces(MediaType.TEXT_PLAIN)
	// This method provides the access to a reservation by id
	public String getReservation(@PathParam("reservationId") int id) {	
		Reservation reservationQuery = reservationDao.getReservation(id);
		if(reservationQuery!= null) {
			return formatReservationDetails(reservationQuery);
		}
		return "No such reservation with input id";  
	}
	@GET
	@Path("/reservations2/{reservationId}")
	@Produces(MediaType.APPLICATION_JSON)
	//This method is used to test the getting of reservation by id
	public Reservation getReservation2(@PathParam("reservationId") int id) {	
		return  reservationDao.getReservation(id); 
	}
	@DELETE
	@Path("/reservations/{reservationId}")
	@Produces(MediaType.TEXT_PLAIN)
	// This method provides the functionality of removing a reservation by id
	public String deleteReservation(@PathParam("reservationId") int id) {
		Reservation reservationToDelete = reservationDao.getReservation(id); 
		if(reservationToDelete !=null) {
			projectorDao.deleteReservation(reservationToDelete);
		}
		int result = reservationDao.deleteReservation(id); 
		if(result == 1) {
			return DELETE_SUCCESS + "" + id;
		}
		return RESERVATION_NOT_EXIST + "" + id;
	}
	@POST
	@Path("/reservations/{date}/{startTime}/{endTime}")
	@Produces(MediaType.TEXT_PLAIN)
	// This method provides the functionality of adding reservation
	public String addReservation(@PathParam("date") String inputDate, @PathParam("startTime") String inputStartTime, @PathParam("endTime") String inputEndTime) {
		String digitsRegex = "[0-9]+";
		if(!inputDate.matches(digitsRegex) || !inputStartTime.matches(digitsRegex) || !inputEndTime.matches(digitsRegex)) {
			return "Error: please input your date/startTime/endTime all in numbers without any other characters like 20180408/1130/1430"; 
		}
		int year = Integer.parseInt(inputDate.substring(0, 4));
		int month = Integer.parseInt(inputDate.substring(4, 6)) -1;
		int day = Integer.parseInt(inputDate.substring(6, 8));
		int startHour = Integer.parseInt(inputStartTime.substring(0, 2));
		int startMinute = Integer.parseInt(inputStartTime.substring(2, 4)); 
		int endHour = Integer.parseInt(inputEndTime.substring(0, 2)); 
		int endMinute = Integer.parseInt(inputEndTime.substring(2, 4));
		if(startHour <8 || endHour > 17) {
			return "Office projectors only available from 8am to 5 pm";
		}
		GregorianCalendar date = new GregorianCalendar(year, month, day);
		GregorianCalendar startTime = new GregorianCalendar(year, month, day, startHour, startMinute);
		GregorianCalendar endTime = new GregorianCalendar(year, month, day, endHour, endMinute);
		Reservation addedReservation = tryAddReservation(date, startTime, endTime); 
		if(addedReservation != null) {
			return formatReservationDetails(addedReservation) + " created successfully";
		}
		return "No projector available with given time";
	}
	@PUT
	@Path("/reservations/{reservationId}/{date}/{startTime}/{endTime}")
	@Produces(MediaType.TEXT_PLAIN)
	//This method provides the service to update an existing reservation
	public String updateReservation(@PathParam("reservationId") int id,  @PathParam("date") String inputDate, @PathParam("startTime") String inputStartTime, @PathParam("endTime") String inputEndTime) {
		Reservation reservation = reservationDao.getReservation(id);
		if(reservation == null) return "No reservation exists with given reservation id";
		else {
			Projector projector = reservation.getProjector();
			projector.projectorListReservations.remove(reservation);
			reservationDao.deleteReservation(id);		
			String digitsRegex = "[0-9]+";
			if(!inputDate.matches(digitsRegex) || !inputStartTime.matches(digitsRegex) || !inputEndTime.matches(digitsRegex)) {
				return "Error: please input your date/startTime/endTime all in numbers without any other characters like 20180408/1130/1430"; 
			}
			int year = Integer.parseInt(inputDate.substring(0, 4));
			int month = Integer.parseInt(inputDate.substring(4, 6)) -1;
			int day = Integer.parseInt(inputDate.substring(6, 8));
			int startHour = Integer.parseInt(inputStartTime.substring(0, 2));
			int startMinute = Integer.parseInt(inputStartTime.substring(2, 4)); 
			int endHour = Integer.parseInt(inputEndTime.substring(0, 2)); 
			int endMinute = Integer.parseInt(inputEndTime.substring(2, 4));
			if(startHour <8 || endHour > 17) {
				return "Office projectors only available from 8am to 5 pm";
			}
			GregorianCalendar date = new GregorianCalendar(year, month, day);
			GregorianCalendar startTime = new GregorianCalendar(year, month, day, startHour, startMinute);
			GregorianCalendar endTime = new GregorianCalendar(year, month, day, endHour, endMinute);
			Reservation updatedReservation = tryUpdateReservation(id, date, startTime, endTime); 
			if(updatedReservation != null) {
				return updatedReservation.getReservationId() + " updated successfully with details " + formatReservationDetails(updatedReservation);
			}
		}
		return "No projector available with given time"; 
	}
	
	private Reservation tryUpdateReservation(int id, GregorianCalendar date, GregorianCalendar startTime,
			GregorianCalendar endTime) {
		boolean hasConflict = false; 
		int updateReservationResult = 0;
		for(Projector projector : listProjector) {
			hasConflict = false;
			if(!projector.projectorListReservations.isEmpty()) {
				for(Reservation reservation : projector.projectorListReservations) {
					if(!(endTime.before(reservation.getStartTime()) || startTime.after(reservation.getEndTime()))){
						hasConflict = true;
						break;
					}
				}
				if(!hasConflict) {
					Reservation postReservation = new Reservation(id, projector, date, startTime, endTime);
					projector.projectorListReservations.add(postReservation);
					updateReservationResult= reservationDao.addReservation(postReservation);	
					
					if (updateReservationResult==1) return postReservation;
				}
			}
			else {
					Reservation postReservation = new Reservation(id, projector, date, startTime, endTime);
					projector.projectorListReservations.add(postReservation);
					updateReservationResult= reservationDao.addReservation(postReservation);
					if (updateReservationResult==1) return postReservation;
			}
		}
		return null;
	}
	@POST
	@Path("/reservations2")
	@Produces(MediaType.APPLICATION_XML)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	//This method is used to test the adding of reservation
	public String addReservation2(@FormParam("date") String inputDate, @FormParam("startTime") String inputStartTime, @FormParam("endTime") String inputEndTime, @Context HttpServletResponse servletResponse) throws IOException{
		String digitsRegex = "[0-9]+";
		if(!inputDate.matches(digitsRegex) || !inputStartTime.matches(digitsRegex) || !inputEndTime.matches(digitsRegex)) {
			return "Error: please input your date/startTime/endTime all in numbers without any other characters like 20180408/1130/1430"; 
		}
		int year = Integer.parseInt(inputDate.substring(0, 4));
		int month = Integer.parseInt(inputDate.substring(4, 6)) -1;
		int day = Integer.parseInt(inputDate.substring(6, 8));
		int startHour = Integer.parseInt(inputStartTime.substring(0, 2));
		int startMinute = Integer.parseInt(inputStartTime.substring(2, 4)); 
		int endHour = Integer.parseInt(inputEndTime.substring(0, 2)); 
		int endMinute = Integer.parseInt(inputEndTime.substring(2, 4));
		GregorianCalendar date = new GregorianCalendar(year, month, day);
		GregorianCalendar startTime = new GregorianCalendar(year, month, day, startHour, startMinute);
		GregorianCalendar endTime = new GregorianCalendar(year, month, day, endHour, endMinute);
		Reservation addedReservation = tryAddReservation(date, startTime, endTime); 
		if(addedReservation != null) {
			return "SUCCESS";
		}
		return "FAILURE";
	}
	private Reservation tryAddReservation(GregorianCalendar date, GregorianCalendar startTime, GregorianCalendar endTime) {
		boolean hasConflict = false; 
		int addReservationResult = 0;
		for(Projector projector : listProjector) {
			hasConflict = false;
			if(!projector.projectorListReservations.isEmpty()) {
				for(Reservation reservation : projector.projectorListReservations) {
					if(!(endTime.before(reservation.getStartTime()) || startTime.after(reservation.getEndTime()))){
						hasConflict = true;
						break;
					}
				}
				if(!hasConflict) {
					Reservation postReservation = new Reservation(++reservationId, projector, date, startTime, endTime);
					projector.projectorListReservations.add(postReservation);
					addReservationResult= reservationDao.addReservation(postReservation);	
					
					if (addReservationResult==1) return postReservation;
				}
			}
			else {
					Reservation postReservation = new Reservation(++reservationId, projector, date, startTime, endTime);
					projector.projectorListReservations.add(postReservation);
					addReservationResult= reservationDao.addReservation(postReservation);
					if (addReservationResult==1) return postReservation;
			}
		}
		return null;
	}
	private String formatReservationDetails(Reservation reservation) {
		return "reservationID = " +  reservation.getReservationId() + " projector = " + reservation.getProjector().getName() + " date " + dateFormatter.format(reservation.getDate().getTime()) + " startTime " +
				timeFormatter.format(reservation.getStartTime().getTime()) + " endTime " + timeFormatter.format(reservation.getEndTime().getTime());
	}
}
