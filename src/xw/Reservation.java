package xw;

import java.io.Serializable;
import java.util.GregorianCalendar;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Reservation implements Serializable{
	private static final long serialVerionUID = 1L; 
	private int reservationId;
	private Projector projector;
	private GregorianCalendar date; 
	private GregorianCalendar startTime;
	private GregorianCalendar endTime;
	public int getReservationId() {
		return reservationId;
	}
	@XmlElement
	public void setReservationId(int reservationId) {
		this.reservationId = reservationId;
	}
	public Projector getProjector() {
		return projector;
	}
	@XmlElement
	public void setProjector(Projector projector) {
		this.projector = projector;
	}
	public GregorianCalendar getDate() {
		return date;
	}
	@XmlElement
	public void setDate(GregorianCalendar date) {
		this.date = date;
	}
	public GregorianCalendar getStartTime() {
		return startTime;
	}
	@XmlElement
	public void setStartTime(GregorianCalendar startTime) {
		this.startTime = startTime;
	}
	public GregorianCalendar getEndTime() {
		return endTime;
	}
	@XmlElement
	public void setEndTime(GregorianCalendar endTime) {
		this.endTime = endTime;
	}
	public Reservation() {}; 
	public Reservation(int reservationId, Projector projector,  GregorianCalendar date,  GregorianCalendar startTime, GregorianCalendar endTime) {
		this.reservationId = reservationId; 
		this.projector = projector;
		this.date = date; 
		this.startTime = startTime; 
		this.endTime = endTime;
	}
	@Override
	public boolean equals(Object object) {
		if(object == null)  return false;
		if(!(object instanceof Reservation)) return false; 
		else{
			Reservation reservation = (Reservation) object;
			if(reservationId == reservation.getReservationId() && projector == reservation.getProjector() && date == reservation.getDate() && startTime == reservation.getStartTime() && endTime == reservation.getEndTime()) {
				return true;
			}
		}
		return false;
	}
	
	

}
