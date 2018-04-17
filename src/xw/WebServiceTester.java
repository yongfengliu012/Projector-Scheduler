package xw;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class WebServiceTester {
	private Client client; 
	private String PROJECTOR_SERVICE_URL="http://localhost:8080/ProjectorScheduler/rest/ProjectorService/projectors";
	private String RESERVATION_SERVICE_URL = "http://localhost:8080/ProjectorScheduler/rest/ReservationService/reservations";
	private String RESERVATION_JSON_SERVICE_URL = "http://localhost:8080/ProjectorScheduler/rest/ReservationService/reservations2";
	public static final String SUCCESS_RESULT = "success";
	public static final String PASS = "pass"; 
	public static final String FAIL = "fail";
	public void init() {
		this.client = ClientBuilder.newClient();
	}
	public static void main(String [] args) {
		WebServiceTester  tester  = new WebServiceTester(); 
		//Initiate the tester
		tester.init();
		//Test get all projectors
		tester.testGetAllProjectors();
		
		//Test get one reservation by reservationId
		tester.testGetReservationById(); 
		
		//Test add reservation
		tester.testAddReservation();
		

		//Test delete reservation
		tester.testDeleteReservation();
		


		
	}
	//test get all projectors
	private void testGetAllProjectors() {
		GenericType < List<Projector>> list = new  GenericType<List<Projector>>() {};
		List<Projector> projectors = client.target(PROJECTOR_SERVICE_URL).request(MediaType.APPLICATION_JSON).get(list);
		String result = PASS; 
		if(projectors.isEmpty()) {
			result = FAIL;
		}
		System.out.println("test case name: testGetAllProjectos, result: " + result);
	}
	
	//Test get one reservation by id
	private void testGetReservationById() {
		Reservation sampleReservation = new Reservation(); 
		sampleReservation.setReservationId(1001);
		Reservation reservation = client.target(RESERVATION_JSON_SERVICE_URL).path("/{reservationId}").resolveTemplate("reservationId", 1001).request(MediaType.APPLICATION_JSON).get(Reservation.class);
		String result = FAIL; 
		if(sampleReservation!=null && sampleReservation.getReservationId()== reservation.getReservationId()) {
			result = PASS; 
		}
		System.out.println("test case name: testGetReservationById, result: " + result);
		
	}
	
	//Test add reservation
	private void testAddReservation() {
		Form form = new Form();
	    form.param("date", "20180509");
	    form.param("startTime", "0930");
	    form.param("endTime", "1000");
	    String callResult = client
	         .target(RESERVATION_JSON_SERVICE_URL)
	         .request(MediaType.APPLICATION_XML)
	         .post(Entity.entity(form,
	            MediaType.APPLICATION_FORM_URLENCODED_TYPE),
	            String.class);
		String result = PASS; 
		if(!callResult.equals("SUCCESS")) {
			result = FAIL;
		}
		System.out.println("test case name : testAddReservation, result: " + result);
	}

	//Test delete reservation
	private void testDeleteReservation() {
		String callResult = client.target(RESERVATION_SERVICE_URL).path("/{reservationId}").resolveTemplate("reservationId", 1004).request(MediaType.TEXT_PLAIN).delete(String.class);
		
		String result = PASS;
		String expectedResult = "delete is success for reservation with id: 1004";
		if(!expectedResult.equals(callResult)) {
			result = FAIL;
		}
		System.out.println("test case name: testDeleteReservation, result:" + result);
	}

}

