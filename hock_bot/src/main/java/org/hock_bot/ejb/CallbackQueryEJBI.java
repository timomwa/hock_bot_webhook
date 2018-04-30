package org.hock_bot.ejb;

import org.telegram.telegrambots.api.objects.CallbackQuery;

public interface CallbackQueryEJBI {

	org.hock_bot.model.CallbackQuery createNew(org.hock_bot.model.User fromUserCBQ_,
			org.hock_bot.model.Message message_, CallbackQuery callBackQuery);
	
	public  org.hock_bot.model.CallbackQuery findbyCallBackQueryId(Long callBackQueryId);

}
