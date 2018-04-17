package xw;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ProjectorDao {
	public List<Projector> getAllProjectors(){
		List<Projector> listProjector = null;
		try {
			File file = new File("MyProjectors.dat"); 
			if(!file.exists()) {
				Projector P1 = new Projector(1, "P1");
				/*
				Projector P2 = new Projector(2, "P2");
				Projector P3 = new Projector(3, "P3");
				Projector P4 = new Projector(4, "P4");
				Projector P5 = new Projector(5, "P5");
				Projector P6 = new Projector(6, "P6");
				Projector P7 = new Projector(7, "P7");
				Projector P8 = new Projector(8, "P8");
				Projector P9 = new Projector(9, "P9");
				Projector P10 = new Projector(10, "P10");
				*/
				listProjector = new ArrayList<Projector>();
				listProjector.add(P1);
				/*
				listProjector.add(P2);
				listProjector.add(P3);
				listProjector.add(P4);
				listProjector.add(P5);
				listProjector.add(P6);
				listProjector.add(P7);
				listProjector.add(P8);
				listProjector.add(P9);
				listProjector.add(P10);
				*/
				saveProjectorList(listProjector);
			}
			else {
				FileInputStream fis = new FileInputStream(file);
				ObjectInputStream ois = new ObjectInputStream(fis); 
				listProjector = (List<Projector>) ois.readObject();
				ois.close();
			}
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}catch(IOException e) {
			e.printStackTrace();
		}
		return listProjector;
	}

	private void saveProjectorList(List<Projector> listProjector) {
		// TODO Auto-generated method stub
		try {
			File file = new File("MyProjectors.dat"); 
			FileOutputStream fos = new FileOutputStream(file);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(listProjector);
			oos.close();
			}catch(FileNotFoundException e) {
				e.printStackTrace();
			}catch(IOException e) {
				e.printStackTrace();
			}
	}
	public int addProjector(Projector postProjector) {
		List<Projector> listProjector = getAllProjectors();
		boolean exists = false;
		for(Projector projector: listProjector) {
			if(projector.getId() == postProjector.getId()) {
				exists = true; 
				break;
			}
		}
		if(!exists) {
			listProjector.add(postProjector);
			saveProjectorList(listProjector);
			return 1;
		}
		return 0;
	}
	public Projector getProjector(int id) {
		List<Projector> listProjector = getAllProjectors();
		for(Projector projector : listProjector) {
			if(projector.getId()==id) {
				return projector;
			}
		}
		return null;
	}
	public int deleteProjector(int id) {
		List<Projector> listProjector = getAllProjectors(); 
		for(Projector projector : listProjector) {
			if(projector.getId() == id) {
				int index = listProjector.indexOf(projector);
				listProjector.remove(projector);
				saveProjectorList(listProjector);
				return 1;
			}
		}
		return 0;
	}
	public int addReservation(Reservation reservation) {
		List<Projector> listProjector = getAllProjectors();
		for(Projector projector : listProjector) {
			if(reservation.getProjector().equals(projector)) {
				projector.projectorListReservations.add(reservation);
			}
		}
		return 0;
	}
	public int deleteReservation(Reservation deleteReservation) {
		List<Projector> listProjector = getAllProjectors(); 
		for(Projector projector : listProjector) {
			if(deleteReservation.getProjector().equals(projector)) {
				projector.projectorListReservations.remove(deleteReservation);
			}
		}
		return 0;
	}
}
