package org.hock_bot.rest.inbound;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;

import org.apache.log4j.Logger;
import org.hock_bot.ejb.ConfigurationEJBI;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.inlinequery.result.InlineQueryResult;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.exceptions.TelegramApiValidationException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Path("/incoming")
public class IncomingREST {
	
	private Logger logger = Logger.getLogger(getClass());
	
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
		
		logger.info("\n\n IN GET token--->> "+token+"\n\n");
		return Response.ok("OK").build();
	}
	
	
	
	@POST
	@Path("/webhook/{token}")
	@Produces("text/plain")
	public Response doPost(@PathParam("token") String token, Update update) {
		
		logger.info("\n\n IN POST:: update --->> "+update+"\n\n");
		logger.info("\n\n IN POST:: ourtoken --->> ["+update+"]\n\n");
		logger.info("\n\n IN POST:: token    --->> ["+token+"]\n\n");
		
		if(ourtoken.equals(token)){
			
			if(update.hasMessage()){
				
				Long chatId = update.getMessage().getChatId();
				Integer reply_to_message_id = update.getMessage().getMessageId();
				
				SendMessage message = new SendMessage();
				
				//InlineKeyboardMarkup replyMarkup = new InlineKeyboardMarkup();
				
				//List<List<InlineKeyboardButton>> keyboard = new ArrayList<List<InlineKeyboardButton>>();
				//List<InlineKeyboardButton> inlinekeyboardButtons = new ArrayList<InlineKeyboardButton>();
				//InlineKeyboardButton inlineButton = new InlineKeyboardButton();
				//inlineButton.setText("InlineBtnTest");
				//inlinekeyboardButtons.add(inlineButton);
				
				//keyboard.add( inlinekeyboardButtons );
				//replyMarkup.setKeyboard(keyboard);
				
				message.setChatId( chatId );
				//message.setReplyMarkup(replyMarkup);
				message.setReplyToMessageId(reply_to_message_id);
				message.setText("Test --> "+(new Date()));
				
				ObjectMapper mapper = new ObjectMapper();
				
				try {
					
					String response = mapper.writeValueAsString(message);//message.toString();
					message.validate();
					
					logger.info("\n\n IN POST:: Hapa sasa response --->> \n\t\t"+response+"\n\n");
					
					 return Response.ok(response).build();
				} catch (TelegramApiValidationException e) {
					logger.error(e.getMessage(), e);
				} catch (JsonProcessingException e) {
					logger.error(e.getMessage(), e);
				}
				
				
			}
			
			if(update.hasCallbackQuery()){
				
			}
			
			if(update.hasChannelPost()){
				
			}
			
			if(update.hasEditedChannelPost()){
				
			}
			
			if(update.hasInlineQuery()){
				
			}
			
			if(update.hasPreCheckoutQuery()){
				
			}
			
			if(update.hasShippingQuery()){
				
			}
			
			if(update.hasEditedMessage()){
				
			}
			
			
			
			
		}
		
		
		if (update.hasMessage() && update.getMessage().hasText()) {
			InlineQueryResult result; 
			SendMessage message = new SendMessage() // Create a SendMessage object with mandatory fields
	                .setChatId(update.getMessage().getChatId())
	                .setText(update.getMessage().getText()+ (new Date()));
	        
	        logger.info("\n\n\t\t >>> Incoming message  --> "+update.getMessage().getText()+"\n\n");
	        try {
	            //execute(message); // Call method to send the message
	        	String resp = "{ \"method\" : \"sendMessage\", \"chat_id\": \""+update.getMessage().getChatId()+"\", \"text\": \""+message.getText()+"\" }";
	        	 logger.info("\n\n\t\t << OUT  --> "+resp+"\n\n");
	  	       return Response.ok(resp).build();
	        }catch (Exception e) {
	        	logger.error(e.getMessage(), e);
	        }
	    }
		return Response.ok("OK").build();
	}
}