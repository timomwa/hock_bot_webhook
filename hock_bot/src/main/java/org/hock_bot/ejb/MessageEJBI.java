package org.hock_bot.ejb;

import org.telegram.telegrambots.api.objects.Message;

public interface MessageEJBI {

	public org.hock_bot.model.Message createNew(org.hock_bot.model.User user_, org.hock_bot.model.Chat chat_, Message message, Message replyToMessage);
	
	public org.hock_bot.model.Message convert(org.telegram.telegrambots.api.objects.Message replyToMessage);

}
