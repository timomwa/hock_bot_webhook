package org.hock_bot.ejb;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.hock_bot.dao.CallbackQueryDAOI;
import org.hock_bot.model.CallbackQuery;
import org.hock_bot.model.Message;
import org.hock_bot.model.User;

@Stateless
public class CallbackQueryEJBImpl implements CallbackQueryEJBI {
	
	private Logger logger = Logger.getLogger(getClass());
	
	@Inject
	private CallbackQueryDAOI callBackQueryDAO;

	@Override
	public CallbackQuery createNew(User fromUserCBQ_, Message message_,
			org.telegram.telegrambots.api.objects.CallbackQuery callBackQuery) {
		
		if(callBackQuery==null)
			return null;
		
		CallbackQuery callbackQuery = findbyCallBackQueryId(callBackQuery.getId());
		
		try{
			if(callbackQuery==null){
				callbackQuery = new CallbackQuery();
				callbackQuery.setCallBackQueryId( Long.valueOf( callBackQuery.getId() )  );
				callbackQuery.setChatInstance(callBackQuery.getChatInstance() );
				callbackQuery.setData(callBackQuery.getData() );
				callbackQuery.setFromUser(fromUserCBQ_);
				callbackQuery.setGameShortName( callBackQuery.getGameShortName() );
				callbackQuery.setInlineMessageId( callBackQuery.getInlineMessageId() );
				callbackQuery.setMessage( message_ );
				callbackQuery = callBackQueryDAO.save(callbackQuery);
			}
			
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}
		return callbackQuery;
	}

	@Override
	public  CallbackQuery findbyCallBackQueryId(String callBackQueryId) {
		return callBackQueryDAO.findbyCallBackQueryId(callBackQueryId);
	}

}
