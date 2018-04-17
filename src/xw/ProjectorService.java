package xw;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/ProjectorService")
public class ProjectorService {
	ProjectorDao projectorDao = new ProjectorDao(); 
	private static final String SUCCESS = "success"; 
	private static final String FAILURE = "failure";
	@GET
	@Path("/projectors")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Projector> getAllProjectors(){
		return projectorDao.getAllProjectors();
	}
	@GET
	@Path("/projectors/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Projector getProjector(@PathParam("id") int id) {
		return projectorDao.getProjector(id);
	}
	@POST
	@Path("/projectors/{id}/{name}")
	@Produces(MediaType.TEXT_PLAIN)
	public String addProjector(@PathParam("id") int id, @PathParam("name") String name) {
		Projector postProjector = new Projector(id, name);
		int result = projectorDao.addProjector(postProjector);
		if(result==1) {
			return SUCCESS;
		}
		return FAILURE; 
	}
	@DELETE
	@Path("/projectors/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteProjector(@PathParam("id") int id) {
		int result = projectorDao.deleteProjector(id);
		if(result == 1) {
			return SUCCESS;
		}
		return FAILURE; 
	}
	
	

}
