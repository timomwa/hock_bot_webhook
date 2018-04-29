package org.hock_bot.rest.inbound;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.hock_bot.ejb.ConfigurationEJBI;
import org.telegram.telegrambots.api.objects.Update;

@Path("/incoming")
public class IncomingREST {
	
	private Logger logger = Logger.getLogger(getClass());
	
	
	@Inject
	private Event<Update> updateEventListener;
	
	@EJB
	private ConfigurationEJBI configurationEJB;
	
	private String ourtoken = "";
	
	
	@PostConstruct
	public void update(){
		try{
			ourtoken = configurationEJB.getOrCreateConfigValue(ConfigurationEJBI.OUR_IDENTIIER_TOKEN, "848sjksk392mmiwimx08372b");
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}
	}

	@GET
	@Path("/webhook/{token}")
	@Produces("text/plain")
	public Response doGet(@PathParam("token") String token) {
		
		return Response.ok("OK").build();
	}
	
	
	
	@POST
	@Path("/webhook/{token}")
	@Produces("text/plain")
	public Response doPost(@PathParam("token") String token, Update update) {
		
		if(ourtoken.equals(token)){
			updateEventListener.fire(update);
		}else{
			logger.info("\n\n\t\tREJECTED, unrecognized token -->["+token+"]\n\n");
		}
		
		return Response.ok("OK").build();
	}
}