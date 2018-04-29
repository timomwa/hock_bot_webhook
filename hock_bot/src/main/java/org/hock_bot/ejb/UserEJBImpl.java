package org.hock_bot.ejb;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.hock_bot.dao.UserDAOI;
import org.hock_bot.model.User;

@Stateless
public class UserEJBImpl implements UserEJBI {
	
	private Logger logger = Logger.getLogger(getClass());
	
	@Inject
	private UserDAOI userDAO;

	@Override
	public User saveOrCreate(org.telegram.telegrambots.api.objects.User user) {
		if(user==null || user.getUserName() ==null && user.getId()==null)
			return null;
		
		User user_ = findByUserId(user.getId()); 
		if(user_ == null){
			user_ = createNew(user); 
		}
		return user_;
	}
	

	@Override
	public User createNew(org.telegram.telegrambots.api.objects.User user) {
		
		User user_ = null;
		
		try{
			user_ = new User();
			user_.setFirstName(user.getFirstName() );
			user_.setIsBot(user.getBot());
			user_.setLanguageCode(user.getLanguageCode());
			user_.setLastName(user.getLastName());
			user_.setUserId(user.getId());
			user_.setUserName(user.getUserName());
			user_ = userDAO.save(user_);
		}catch(Exception e){
			logger.error(e.getMessage() , e);
		}
		return user_;
	}

	@Override
	public User findByUserId(Integer userId) {
		return userDAO.findByUserId(userId);
	}

}
