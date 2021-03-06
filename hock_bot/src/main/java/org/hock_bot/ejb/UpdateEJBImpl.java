package org.hock_bot.ejb;

import java.util.Arrays;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.hock_bot.dao.UpdateDAOI;
import org.hock_bot.model.Message;
import org.hock_bot.model.Status;
import org.telegram.telegrambots.api.objects.Update;

@Stateless
public class UpdateEJBImpl implements UpdateEJBI {
	
	private Logger logger = Logger.getLogger(getClass());
	
	@Inject
	private UpdateDAOI updateDAO;

	@Override
	public org.hock_bot.model.Update saveNew(Message message_, Update update, org.hock_bot.model.CallbackQuery callBackQuery) {
		
		org.hock_bot.model.Update update_ = null;
		
		try{
			
			update_ = new org.hock_bot.model.Update();
			update_.setMessage(message_);
			update_.setStatus(Status.JUST_IN);
			update_.setUpdateId(update.getUpdateId());
			update_.setCallbackQuery(callBackQuery);
			update_ = updateDAO.save(update_);
		
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return null;
		}
		
		return update_;
	}

	@Override
	public List<org.hock_bot.model.Update> listUnprocessed(int size) {
		Status[] statuses = {Status.FAILED_TEMPORARILY, Status.JUST_IN};
		return updateDAO.findByType(Arrays.asList(statuses), size);
	}

	@Override
	public void updateStatus(Status status, Long updateId) {
		updateDAO.updateStatus(status, updateId);
	}

	@Override
	public void increaseRetryCount(Long updateId) {
		updateDAO.increaseRetryCount(updateId);
	}

}
