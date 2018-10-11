package org.hock_bot.dao;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.hock_bot.model.FlowPosition;

public class FlowPositionDAOImpl extends GenericDAOImpl<FlowPosition, Long> implements FlowPositionDAOI {
	
	private Logger logger = Logger.getLogger(getClass());

	@Override
	public FlowPosition findFlowPosition(Long chatId, Integer userId) {
		
		FlowPosition position = null;
		
		try{
			
			Query qry = em.createQuery("from FlowPosition fp WHERE fp.chatId = :chatId AND fp.userId = :userId");
			qry.setParameter("chatId", chatId);
			qry.setParameter("userId", userId);
			position = (FlowPosition) qry.getSingleResult();
			
		}catch(NoResultException e){
			
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}
		
		return position;
	}

}
