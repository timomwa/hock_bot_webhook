package org.hock_bot.ejb;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.hock_bot.dao.FlowPositionDAOI;
import org.hock_bot.model.FlowPosition;


@Stateless
public class FlowPositionEJBImpl implements FlowPositionEJBI {
	
	
	private Logger logger = Logger.getLogger(getClass());
	
	@Inject
	private FlowPositionDAOI flowPositionDAO;

	@Override
	public FlowPosition createMarker(Long chatId, Integer userId, String positionMarker) throws Exception{
		FlowPosition position = new FlowPosition();
		position.setChatId(chatId);
		position.setUserId(userId);
		position.setPositionMarker(positionMarker);
		return flowPositionDAO.save(position);
	}

	@Override
	public FlowPosition findFlowPosition(Long chatId, Integer userId) {
		return flowPositionDAO.findFlowPosition(chatId, userId);
	}

	@Override
	public void deleteById(Long id) throws Exception{
		flowPositionDAO.deleteById(id);
	}

	@Override
	public void deletePreviousMarkers(Long chatId, Integer voterUserId) {
		flowPositionDAO.deletePreviousMarkers(chatId, voterUserId);
	}

}
