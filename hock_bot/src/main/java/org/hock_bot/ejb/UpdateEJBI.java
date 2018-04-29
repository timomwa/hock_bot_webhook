package org.hock_bot.ejb;

import org.telegram.telegrambots.api.objects.Update;

public interface UpdateEJBI {

	public org.hock_bot.model.Update saveNew(org.hock_bot.model.Message message_, Update update);

}
