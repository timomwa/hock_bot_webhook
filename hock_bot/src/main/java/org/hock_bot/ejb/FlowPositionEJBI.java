package org.hock_bot.ejb;

import org.hock_bot.model.FlowPosition;

public interface FlowPositionEJBI {

	public FlowPosition createMarker(Long chatId, Integer userId, String positionMarker) throws Exception;

	public FlowPosition findFlowPosition(Long chatId, Integer userId);

	public void deleteById(Long id) throws Exception;

	public void deletePreviousMarkers(Long chatId, Integer voterUserId);

}
