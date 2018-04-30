package org.hock_bot.ejb;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.hock_bot.dao.MessageDAOI;
import org.hock_bot.model.Chat;
import org.hock_bot.model.Message;
import org.hock_bot.model.User;

@Stateless
public class MessageEJBImpl implements MessageEJBI {
	
	private Logger logger = Logger.getLogger(getClass());
	
	@EJB
	private UserEJBI userEJB;
	
	@EJB
	private ChatEJBI chatEJB;
	
	@EJB
	private MessageEJBI messageEJB;
	
	@EJB
	private UpdateEJBI updateEJB;
	
	@Inject
	private MessageDAOI messageDAO;

	@Override
	public Message createNew(User user_, Chat chat_, org.telegram.telegrambots.api.objects.Message message, org.telegram.telegrambots.api.objects.Message replyToMessage){
		
		Message message_ = null;
		
		try{
			message_ = new Message();
			message_.setChat(chat_);
			message_.setDate(message.getDate());
			message_.setFromUser(user_);
			message_.setMessageId(message.getMessageId());
			message_.setText(message.getText());
			
			message_.setReplyToMessage( convert(replyToMessage) );
			
			message_ =  messageDAO.save(message_);
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}
		
		return message_;
	}

	@Override
	public Message convert(org.telegram.telegrambots.api.objects.Message replyToMessage) {
		if(replyToMessage==null)
			return null;
		
		Message message_ = null;
		
		try{
			
			//message_ = messageDAO.findbyMessageId(replyToMessage.getMessageId());
			//if(message_==null)
			message_ = new Message();
			org.telegram.telegrambots.api.objects.Chat chat_ = replyToMessage.getChat();
			org.telegram.telegrambots.api.objects.User user_ = replyToMessage.getFrom();
			User user = userEJB.saveOrCreate(user_);
			Chat chat = chatEJB.saveOrCreate(chat_);
			
			
			message_.setChat(chat);
			message_.setDate(replyToMessage.getDate());
			message_.setFromUser(user);
			message_.setMessageId(replyToMessage.getMessageId());
			message_.setText(replyToMessage.getText());
			
			message_ =  messageDAO.save(message_);
			
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}
		return message_;
	}


}
