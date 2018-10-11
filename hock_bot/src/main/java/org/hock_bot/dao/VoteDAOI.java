package org.hock_bot.dao;

import org.hock_bot.model.Vote;

public interface VoteDAOI extends GenericDAOI<Vote, Long> {

	Vote findbyVoterUserIdAndPosition(Integer userId, String position);

}
