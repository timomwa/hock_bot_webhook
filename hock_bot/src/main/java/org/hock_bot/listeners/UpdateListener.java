package org.hock_bot.listeners;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.enterprise.event.Observes;

import org.apache.log4j.Logger;
import org.hock_bot.ejb.ChatEJBI;
import org.hock_bot.ejb.MessageEJBI;
import org.hock_bot.ejb.UpdateEJBI;
import org.hock_bot.ejb.UserEJBI;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.User;

import com.google.inject.Singleton;

@Stateless
@Singleton
public class UpdateListener {
	
	private Logger logger = Logger.getLogger(getClass());
	
	@EJB
	private UserEJBI userEJB;
	
	@EJB
	private ChatEJBI chatEJB;
	
	
	@EJB
	private MessageEJBI messageEJB;
	
	@EJB
	private UpdateEJBI updateEJB;
	
	
	public void update(@Observes Update update ){
		
		
		logger.info("\n\n IN POST:: update --->> "+update+"\n\n");
		
		if(update!=null){
			
			if(update.hasMessage()){
				
				Message message = update.getMessage();
				User user = message.getFrom();
				Chat chat = message.getChat();
				
				org.hock_bot.model.User user_ = userEJB.saveOrCreate(user);
				org.hock_bot.model.Chat chat_ = chatEJB.saveOrCreate(chat);
				org.hock_bot.model.Message message_ = messageEJB.createNew(user_, chat_, message);
				org.hock_bot.model.Update update_ = updateEJB.saveNew(message_, update);
				
				
			}
			
		}
		
		
	}

}
