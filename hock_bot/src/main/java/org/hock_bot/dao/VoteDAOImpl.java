package org.hock_bot.dao;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.hock_bot.model.Vote;

public class VoteDAOImpl  extends GenericDAOImpl<Vote, Long> implements VoteDAOI {
	
	private Logger logger = Logger.getLogger(getClass());

	@Override
	public Vote findbyVoterUserIdAndPosition(Integer voterUserId, String position) {
		
		Vote nominationVote = null;
		
		try{
			
			Query qry = em.createQuery("from Vote v WHERE v.voterUserId = :voterUserId AND v.position = :position");
			qry.setParameter("voterUserId", voterUserId);
			qry.setParameter("position", position);
			nominationVote = (Vote) qry.getSingleResult();
			
		}catch(NoResultException nre){
			
		}catch(Exception e){
			logger.error(e.getMessage(),e);
		}
		
		return nominationVote;
	}

}
