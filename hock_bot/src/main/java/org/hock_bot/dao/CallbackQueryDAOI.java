package org.hock_bot.dao;

import org.hock_bot.model.CallbackQuery;

public interface CallbackQueryDAOI extends GenericDAOI<CallbackQuery, Long> {

	public CallbackQuery findbyCallBackQueryId(Long callBackQueryId);

}
