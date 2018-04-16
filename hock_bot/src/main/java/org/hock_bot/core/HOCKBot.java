package org.hock_bot.core;
import java.util.Date;

import org.apache.log4j.Logger;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class HOCKBot  extends TelegramLongPollingBot {
	
	
	private Logger logger = Logger.getLogger(getClass());

	@Override
	public String getBotUsername() {
		return ConfigurationI.BOT_USERNAME;
	}

	@Override
	public void onUpdateReceived(Update update) {
		
		if (update.hasMessage() && update.getMessage().hasText()) {
	        SendMessage message = new SendMessage() // Create a SendMessage object with mandatory fields
	                .setChatId(update.getMessage().getChatId())
	                .setText(update.getMessage().getText()+ (new Date()));
	        
	        logger.info("\n\n\t\t Incoming message  --> "+update.getMessage().getText()+"\n\n");
	        try {
	            execute(message); // Call method to send the message
	        } catch (TelegramApiException e) {
	        	logger.error(e.getMessage(), e);
	        }catch (Exception e) {
	        	logger.error(e.getMessage(), e);
	        }
	    }
		
	}

	@Override
	public String getBotToken() {
		return ConfigurationI.API_KEY;
	}
	

}
