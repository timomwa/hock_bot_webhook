package org.hock_bot.ejb;

import java.util.List;

import org.hock_bot.model.Status;
import org.telegram.telegrambots.api.objects.Update;

public interface UpdateEJBI {

	public org.hock_bot.model.Update saveNew(org.hock_bot.model.Message message_, Update update, org.hock_bot.model.CallbackQuery callBackQuery);

	public List<org.hock_bot.model.Update> listUnprocessed(int size);

	public void updateStatus(Status processing, Long id);

	public void increaseRetryCount(Long id);

}
