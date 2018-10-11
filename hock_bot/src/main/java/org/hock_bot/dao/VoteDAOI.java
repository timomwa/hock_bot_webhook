package org.hock_bot.dao;

import java.util.List;

import org.hock_bot.cron.VoteDTO;
import org.hock_bot.model.Vote;

public interface VoteDAOI extends GenericDAOI<Vote, Long> {

	Vote findbyVoterUserIdAndPosition(Integer userId, String position);

	List<VoteDTO> doTally();

	Vote findByNomineeUserNameAndNamesAndUserId(String nomineeUsername, String nomineeNames, Integer userId, Boolean selfNomination);

	List<VoteDTO> doTally(String post);

}
