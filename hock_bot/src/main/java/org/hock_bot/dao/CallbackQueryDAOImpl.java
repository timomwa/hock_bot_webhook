package org.hock_bot.dao;


import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.hock_bot.model.CallbackQuery;

public class CallbackQueryDAOImpl extends GenericDAOImpl<CallbackQuery, Long> implements CallbackQueryDAOI {
	
	private Logger logger = Logger.getLogger(getClass());

	@Override
	public CallbackQuery findbyCallBackQueryId(String callBackQueryId) {
		
		CallbackQuery cbq = null;
		
		try{
			
			Query qry = em.createQuery("from CallbackQuery cbq WHERE cbq.callBackQueryId = :callBackQueryId");
			qry.setParameter("callBackQueryId", callBackQueryId);
			cbq = (CallbackQuery) qry.getSingleResult();
			
		}catch(NoResultException nre){
			
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}
		return cbq;
	}


}
