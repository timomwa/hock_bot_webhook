package org.hock_bot.rest.inbound;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import java.util.Date;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;

import org.apache.log4j.Logger;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.inlinequery.result.InlineQueryResult;
import org.telegram.telegrambots.exceptions.TelegramApiException;

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
	public Response doPost(@PathParam("token") String token, Update update) {
		
		logger.info("\n\n IN POST:: update --->> "+update+"\n\n");
		
		if (update.hasMessage() && update.getMessage().hasText()) {
			InlineQueryResult result; 
			SendMessage message = new SendMessage() // Create a SendMessage object with mandatory fields
	                .setChatId(update.getMessage().getChatId())
	                .setText(update.getMessage().getText()+ (new Date()));
	        
	        logger.info("\n\n\t\t >>> Incoming message  --> "+update.getMessage().getText()+"\n\n");
	        try {
	            //execute(message); // Call method to send the message
	        	String resp = "{ \"chat_id\": \""+update.getMessage().getChatId()+"\", \"text\": \"Text\" }";
	        	 logger.info("\n\n\t\t << OUT  --> "+resp+"\n\n");
	  	       return Response.ok(resp).build();
	        }catch (Exception e) {
	        	logger.error(e.getMessage(), e);
	        }
	    }
		return Response.ok("OK").build();
	}
}