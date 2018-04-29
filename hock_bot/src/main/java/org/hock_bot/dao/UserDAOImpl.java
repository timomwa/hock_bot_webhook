package org.hock_bot.dao;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.hock_bot.model.User;

public class UserDAOImpl extends GenericDAOImpl<User, Long> implements UserDAOI {
	
	private Logger logger = Logger.getLogger(getClass());

	@Override
	public User findByUserId(Integer userId) {
		
		User user = null;
		
		try{
			Query qry = em.createQuery("from User u WHERE u.userId = :userId ");
			qry.setParameter("userId", userId);
			user = (User) qry.getSingleResult();
		}catch(NoResultException nre){
			
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}
		
		return user;
	}

	

}
