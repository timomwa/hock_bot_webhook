package org.hock_bot.ejb;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.hock_bot.core.ConfigurationI;
import org.hock_bot.dao.ChatDAOI;
import org.hock_bot.model.Chat;

@Stateless
public class ChatEJBImpl implements ChatEJBI {
	
	private Logger logger = Logger.getLogger(getClass());
	
	@Inject
	private ChatDAOI chatDAO;

	@Override
	public Chat saveOrCreate(org.telegram.telegrambots.api.objects.Chat chat) {
		if(chat==null)
			return null;
		Chat chat_ = findByChatId(chat.getId());
		
		try{
			
			if(chat_==null){
				chat_ = createNew(chat);
			}
			
		}catch(Exception e){
			logger.error(e.getMessage(),e);
		}
		
		return chat_;
	}

	
	@Override
	public Chat createNew(org.telegram.telegrambots.api.objects.Chat chat) {
		
		Chat chat_ = null;
		
		try{
			
			chat_ = new Chat();
			chat_.setAllMembersAreAdministrators(chat.getAllMembersAreAdministrators());
			chat_.setChatId(chat.getId());
			chat_.setDescription(chat.getDescription());
			chat_.setFirstName(chat.getFirstName());
			chat_.setInviteLink(chat.getInviteLink());
			chat_.setLastName(chat.getLastName());
			chat_.setPinnedMessage(chat.getPinnedMessage());
			chat_.setStickerSetName(chat.getStickerSetName());
			chat_.setTitle(chat.getTitle());
			
			String type = "";
			if(chat.isChannelChat())
				type = ConfigurationI.CHANNELCHATTYPE;
			if(chat.isGroupChat())
				type = ConfigurationI.GROUPCHATTYPE;
			if(chat.isUserChat())
				type = ConfigurationI.USERCHATTYPE;
			if(chat.isSuperGroupChat())
				type = ConfigurationI.SUPERGROUPCHATTYPE;
			chat_.setType(type);
			chat_.setUserName(chat.getUserName());
			chat_ = chatDAO.save(chat_);
			
		}catch(Exception e){
			logger.error(e.getMessage(),e);
		}
		
		return chat_;
	}

	@Override
	public Chat findByChatId(Long chatId) {
		return chatDAO.findByChatId(chatId);
	}

}
