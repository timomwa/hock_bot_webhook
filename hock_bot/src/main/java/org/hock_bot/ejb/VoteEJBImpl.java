package org.hock_bot.ejb;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.hock_bot.dao.VoteDAOI;
import org.hock_bot.model.Vote;

@Stateless
public class VoteEJBImpl implements VoteEJBI {
	
	@Inject
	private VoteDAOI voteDAO;
	
	private Logger logger = Logger.getLogger(getClass());

	@Override
	public Vote findbyVoterUserId(Integer userId) {
		return voteDAO.findBy("voterUserId", userId);
	}

	@Override
	public Vote saveOrUpdate(Vote nominationVoteCast) throws Exception{
		return voteDAO.save(nominationVoteCast);
	}

}
