package org.hock_bot.dao;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.hock_bot.model.Chat;

public class ChatDAOImpl  extends GenericDAOImpl<Chat, Long>  implements ChatDAOI {
	
	private Logger logger = Logger.getLogger( getClass() );

	@Override
	public Chat findByChatId(Long chatId) {
		Chat chat = null;
		try{
			
			Query qry = em.createQuery("from Chat c WHERE c.chatId = :chatId");
			qry.setParameter("chatId", chatId);
			chat = (Chat) qry.getSingleResult();
		}catch(NoResultException nre){
			
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}
		
		return chat;
	}

}
