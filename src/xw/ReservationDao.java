package xw;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

public class ReservationDao {
	ProjectorDao projectorDao = new ProjectorDao(); 
	public List<Reservation> getAllReservations(){
		List<Reservation> listReservation = null;
		try {
			File file = new File("Reservations.dat"); 
			if(!file.exists()) {
				listReservation = new ArrayList<Reservation>();
				Reservation defaultReservation = new Reservation(1001, projectorDao.getProjector(1), new GregorianCalendar(2018, (4-1), 7), 
						new GregorianCalendar(2018, (4-1), 7, 11, 30), new GregorianCalendar(2018, (4-1), 7, 12, 30)); 
				listReservation.add(defaultReservation);
				projectorDao.addReservation(defaultReservation);
				saveListReservation(listReservation);
			}
			else {
				FileInputStream fis = new FileInputStream(file); 
				ObjectInputStream ois = new ObjectInputStream(fis); 
				listReservation = (List<Reservation>) ois.readObject();
				ois.close();
			}
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}catch(IOException e) {
			e.printStackTrace();
		}
		return listReservation;
		
	}

	private void saveListReservation(List<Reservation> listReservation) {
		try {
			File file = new File("Reservations.dat"); 
			FileOutputStream fos = new FileOutputStream(file); 
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(listReservation);
			oos.close();
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	public Reservation getReservation(int id) {
		List<Reservation> listReservation = getAllReservations(); 
		for(Reservation reservation : listReservation) {
			if(reservation.getReservationId() == id) {
				return reservation;
			}
		}
		return null;
	}
	public int addReservation(Reservation postReservation) {
		List<Reservation> listReservation = getAllReservations(); 
		boolean exists = false; 
		for(Reservation reservation : listReservation) {
			if(reservation.getReservationId() == postReservation.getReservationId()) {
				exists = true; 
				break;
			}
		}
		if(!exists) {
			listReservation.add(postReservation);
			saveListReservation(listReservation);
			return 1;
		}
		return 0;
	}
	public int updateReservation(Reservation putReservation) {
		List<Reservation> listReservation = getAllReservations(); 
		for(Reservation reservation : listReservation) {
			if(reservation.getReservationId() == putReservation.getReservationId()) {
				int index = listReservation.indexOf(reservation);
				listReservation.set(index, putReservation);
				saveListReservation(listReservation);
				return 1;
			}
		}
		return 0;
	}
	public int deleteReservation(int id) {
		List<Reservation> listReservation = getAllReservations(); 
		for(Reservation reservation : listReservation) {
			if(reservation.getReservationId() == id) {
				listReservation.remove(reservation);
				saveListReservation(listReservation);
				return 1;
			}
		}
		return 0;
	}

}
