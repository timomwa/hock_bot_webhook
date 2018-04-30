package org.hock_bot.dao;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.hock_bot.model.Message;

public class MessageDAOImpl extends GenericDAOImpl<Message, Long> implements MessageDAOI {
	
	private Logger logger = Logger.getLogger(getClass());

	@SuppressWarnings("unchecked")
	@Override
	public Message findbyMessageId(Integer messageId) {
		
		List<Message> messages = null;
		
		try{
			
			Query qry = em.createQuery("from Message msg WHERE msg.messageId = :messageId");
			qry.setParameter("messageId", messageId);
			messages = qry.getResultList();
			
		}catch(NoResultException e){
			
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}
		
		return (messages!=null ? messages.get(0) : null);
	}

}
