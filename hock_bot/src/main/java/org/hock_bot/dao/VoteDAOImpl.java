package org.hock_bot.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.hock_bot.cron.VoteDTO;
import org.hock_bot.model.Vote;

public class VoteDAOImpl  extends GenericDAOImpl<Vote, Long> implements VoteDAOI {
	
	private Logger logger = Logger.getLogger(getClass());

	@Override
	public Vote findbyVoterUserIdAndPosition(Integer voterUserId, String position) {
		
		Vote nominationVote = null;
		
		try{
			
			Query qry = em.createQuery("from Vote v WHERE v.voterUserId = :voterUserId AND lower(v.position) = lower(:post_)");
			qry.setParameter("voterUserId", voterUserId);
			qry.setParameter("post_", position);
			nominationVote = (Vote) qry.getSingleResult();
			
		}catch(NoResultException nre){
			
		}catch(Exception e){
			logger.error(e.getMessage(),e);
		}
		
		return nominationVote;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VoteDTO> doTally() {
		
		List<VoteDTO> tally= new ArrayList<VoteDTO>();
		
		try{
			
			Query qry = em.createQuery("SELECT  v.nomineeNames, v.position, count(*) as cnt from Vote v GROUP BY v.nomineeNames ORDER BY cnt DESC");
			
			List<Object[]> objects = qry.getResultList();
			for(Object[] object : objects){
				tally.add( new VoteDTO( (String)object[0] ,(String)object[1], (Long)object[2] ) ); 
			}
			
		}catch(NoResultException nre){
			
		}catch(Exception e){
			logger.error(e.getMessage(),e);
		}
		
		return tally;
	}

}
