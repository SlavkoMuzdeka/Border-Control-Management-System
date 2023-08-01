package net.etfbl.api;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;


@Path("/credentials")
public class CredentialsAPI {
	
	CredentialService service;
	
	public CredentialsAPI() {
		super();
		this.service = new CredentialService();
	}
	
	@GET
	@Path("/{username}")
	public Response getPassword(@PathParam("username") String username) {
		String password = service.getPassword(username);
		if(password != null) {
			return Response.status(200).entity(password).build();
		}else {
			return Response.status(404).build();
		}
	}
	
	@POST
	public Response addCredentials(String kredencijali) {
		if (service.addCredentials(kredencijali.split(",")[0], kredencijali.split(",")[1])) {
			return Response.status(200).build();
		} else {
			return Response.status(500).build();
		}
	}
	
	@DELETE
	@Path("/{username}")
	public Response deleteCredentials(@PathParam("username") String username) {
		if (service.deleteCredentials(username)) {
			return Response.status(200).build();
		}
		return Response.status(404).build();
	}
	
	@PUT
	@Path("/{username}")
	public Response updateCredentials(@PathParam("username") String username, String password) {
		if (service.updateCredentials(username, password)) {
			return Response.status(200).build();
		}
		return Response.status(404).build();
	}
	
}