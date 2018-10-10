package org.hock_bot.ejb;

import org.hock_bot.model.Vote;

public interface VoteEJBI {

	Vote findbyVoterUserId(Integer userId);

	Vote saveOrUpdate(Vote nominationVoteCast) throws Exception;

}
