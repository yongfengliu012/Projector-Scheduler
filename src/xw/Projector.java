package xw;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Projector implements Serializable {
	private static final long serialVersionUID = 1L; 
	private int id; 
	private String name;
	public static List<Reservation> projectorListReservations = new ArrayList<Reservation>();
	public int getId() {
		return id;
	}
	@XmlElement
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	@XmlElement
	public void setName(String name) {
		this.name = name;
	}
	public Projector() {};
	public Projector(int id, String name) {
		this.id = id; 
		this.name = name;
	}
	public boolean equals(Object object) {
		if(object == null) return false;
		if(!(object instanceof Projector)) return false;
		else {
			Projector projector = (Projector)object; 
			if(id == projector.getId() && name == projector.getName()) {
				return true;
			}
		}
		return false;
	}
	
}
