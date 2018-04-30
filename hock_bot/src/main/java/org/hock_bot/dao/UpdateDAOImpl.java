package org.hock_bot.dao;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.hock_bot.model.Status;
import org.hock_bot.model.Update;

public class UpdateDAOImpl  extends GenericDAOImpl<Update, Long>  implements  UpdateDAOI {
	
	private Logger logger = Logger.getLogger(getClass());

	@SuppressWarnings("unchecked")
	@Override
	public List<Update> findByType(List<Status> statuses, int size) {
		
		List<Update> unprocessedUpdates = null;
		
		try{
			
			Query qry = em.createQuery("from Update u WHERE u.status in ( :statuses ) AND u.retryCount < u.maxRetries ");
			qry.setParameter("statuses", statuses);
			
			unprocessedUpdates = qry.getResultList();
			
		}catch(NoResultException e){
			
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}
		
		return unprocessedUpdates;
	}

	@Override
	public void updateStatus(Status status, Long updateId) {

		try{
			
			Query qry = em.createQuery("UPDATE Update u SET u.status = :status WHERE u.id = :updateId");
			qry.setParameter("status", status);
			qry.setParameter("updateId", updateId);
			qry.executeUpdate();
			
		}catch(NoResultException e){
			
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}
		
	}

	@Override
	public void increaseRetryCount(Long updateId) {

		try{
			
			Query qry = em.createQuery("UPDATE Update u SET u.retryCount = (u.retryCount + 1) WHERE u.id = :updateId");
			qry.setParameter("updateId", updateId);
			qry.executeUpdate();
			
		}catch(NoResultException e){
			
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}
		
	}

}
