package org.hock_bot.dao;

import org.hock_bot.model.User;

public interface UserDAOI  extends GenericDAOI<User, Long> {

	public User findByUserId(Integer userId);

}
