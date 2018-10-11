package org.hock_bot.dao;

import org.hock_bot.model.FlowPosition;

public interface FlowPositionDAOI extends GenericDAOI<FlowPosition, Long>  {

	FlowPosition findFlowPosition(Long chatId, Integer userId);

	void deletePreviousMarkers(Long chatId, Integer voterUserId);

}
