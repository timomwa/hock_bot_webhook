package org.hock_bot.ejb;

import org.telegram.telegrambots.api.objects.User;

public interface UserEJBI {

	public org.hock_bot.model.User saveOrCreate(User user);
	
	public org.hock_bot.model.User findByUserId(Integer userId) ;
	
	public org.hock_bot.model.User createNew(org.telegram.telegrambots.api.objects.User user);

}
