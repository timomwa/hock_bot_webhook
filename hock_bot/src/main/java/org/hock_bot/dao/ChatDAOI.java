package org.hock_bot.dao;

import org.hock_bot.model.Chat;

public interface ChatDAOI  extends GenericDAOI<Chat, Long> {

	public Chat findByChatId(Long chatId);

}
