package org.hock_bot.ejb;

import org.hock_bot.model.FlowPosition;

public interface FlowPositionEJBI {

	public FlowPosition createMarker(Long chatId, Integer userId, String positionMarker) throws Exception;

	public FlowPosition findFlowPosition(Long chatId, Integer userId);

}
