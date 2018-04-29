package org.hock_bot.ejb;

import org.telegram.telegrambots.api.objects.Message;

public interface MessageEJBI {

	org.hock_bot.model.Message createNew(org.hock_bot.model.User user_, org.hock_bot.model.Chat chat_, Message message);

}
