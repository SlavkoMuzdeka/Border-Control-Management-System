package net.etfbl.api;

import java.util.Set;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/warrants")
public class WarrantAPI {
	
	WarrantService service;
	
	public WarrantAPI() {
		super();
		this.service = new WarrantService();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Set<Integer> dohvatiSve() {
		return service.getAll();
	}
	
	@POST
	public Response add(String mess) {
		int id = Integer.parseInt(mess);
		if (service.add(id)) {
			return Response.status(200).build();
		} else {
			return Response.status(500).build();
		}
	}
	
}
