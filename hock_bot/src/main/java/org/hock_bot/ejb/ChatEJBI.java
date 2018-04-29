package org.hock_bot.ejb;

import org.telegram.telegrambots.api.objects.Chat;

public interface ChatEJBI {

	public org.hock_bot.model.Chat saveOrCreate(Chat chat);
	
	public org.hock_bot.model.Chat findByChatId(Long chatId) ;
	
	public org.hock_bot.model.Chat createNew(org.telegram.telegrambots.api.objects.Chat chat);

}
