package org.hock_bot.rest.inbound;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;

import org.apache.log4j.Logger;

@Path("/incoming")
public class IncomingREST {
	
	private Logger logger = Logger.getLogger(getClass());

	@GET
	@Path("/webhook/{token}")
	@Produces("text/plain")
	public Response doGet(@PathParam("token") String token) {
		
		logger.info("\n\n IN GET token--->> "+token+"\n\n");
		return Response.ok("OK").build();
	}
	
	
	
	@POST
	@Path("/webhook/{token}")
	@Produces("text/plain")
	public Response doPost(@PathParam("token") String token) {
		
		logger.info("\n\n IN POST:: token--->> "+token+"\n\n");
		return Response.ok("OK").build();
	}
}