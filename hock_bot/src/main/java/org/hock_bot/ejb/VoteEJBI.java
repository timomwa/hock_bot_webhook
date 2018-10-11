package org.hock_bot.ejb;

import java.util.List;

import org.hock_bot.cron.VoteDTO;
import org.hock_bot.model.Vote;

public interface VoteEJBI {

	Vote findbyVoterUserId(Integer userId);

	Vote saveOrUpdate(Vote nominationVoteCast) throws Exception;

	Vote findbyVoterUserIdAndPosition(Integer userId, String positionChosen);

	List<VoteDTO> doTally();

	Vote findByNomineeUserNameAndNamesAndUserId(String username, String nomineeNames, Integer userId, Boolean selfNomination);

}
