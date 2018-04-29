package org.hock_bot.ejb;

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
	
	@Inject
	private MessageDAOI messageDAO;

	@Override
	public Message createNew(User user_, Chat chat_, org.telegram.telegrambots.api.objects.Message message) {
		
		Message message_ = null;
		
		try{
			message_ = new Message();
			message_.setChat(chat_);
			message_.setDate(message.getDate());
			message_.setFrom(user_);
			message_.setMessageId(message.getMessageId());
			message_.setText(message.getText());
			message_ =  messageDAO.save(message_);
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}
		
		return message_;
	}

}
