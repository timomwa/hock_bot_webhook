package org.hock_bot.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import org.hock_bot.core.ConfigurationI;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "bot_chat", catalog = ConfigurationI.CATALOG)
public class Chat extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 209573792721L;

	@JsonProperty(ConfigurationI.ID_FIELD)
	@Column(name="chat_id", unique=true)
	private Long chatId; /// < Unique identifier for this chat, not exciding 1e13 by
						/// absolute value
	@JsonProperty(ConfigurationI.TYPE_FIELD)
	@Column(name="type")
	private String type; /// < Type of the chat, one of “private”, “group” or
							/// “channel”
	@JsonProperty(ConfigurationI.TITLE_FIELD)
	@Column(name="title")
	private String title; /// < Optional. Title of the chat, only for channels
							/// and group chat
	@JsonProperty(ConfigurationI.FIRSTNAME_FIELD)
	@Column(name="first_name")
	private String firstName; /// < Optional. Username of the chat, only for
								/// private chats and channels if available
	@JsonProperty(ConfigurationI.LASTNAME_FIELD)
	@Column(name="last_name")
	private String lastName; /// < Optional. Interlocutor's first name for
								/// private chats
	@JsonProperty(ConfigurationI.USERNAME_FIELD)
	@Column(name="user_name")
	private String userName;
	
	@JsonProperty(ConfigurationI.ALL_MEMBERS_ARE_ADMINISTRATORS_FIELD)
	@Column(name="all_members_are_admins")
	private Boolean allMembersAreAdministrators;
	
	
	@JsonProperty(ConfigurationI.DESCRIPTION_FIELD)
	@Column(name="description")
	private String description; ///< Optional. Description, for supergroups and channel chats. Returned only in getChat.
   
	@JsonProperty(ConfigurationI.INVITELINK_FIELD)
	@Column(name="invite_link")
	private String inviteLink; ///< Optional. Chat invite link, for supergroups and channel chats. Returned only in getChat.
    
	@JsonProperty(ConfigurationI.PINNEDMESSAGE_FIELD)
	@JoinColumn(name="pinned_msg_id")
	private Message pinnedMessage; ///< Optional. Pinned message, for supergroups. Returned only in getChat.
    
	
	@JsonProperty(ConfigurationI.STICKERSETNAME_FIELD)
	@Column(name="sticker_set_name")
	private String stickerSetName; ///< Optional. For supergroups, name of Group sticker set. Returned only in getChat.


	public Long getChatId() {
		return chatId;
	}


	public void setChatId(Long chatId) {
		this.chatId = chatId;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public Boolean getAllMembersAreAdministrators() {
		return allMembersAreAdministrators;
	}


	public void setAllMembersAreAdministrators(Boolean allMembersAreAdministrators) {
		this.allMembersAreAdministrators = allMembersAreAdministrators;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getInviteLink() {
		return inviteLink;
	}


	public void setInviteLink(String inviteLink) {
		this.inviteLink = inviteLink;
	}


	public Message getPinnedMessage() {
		return pinnedMessage;
	}


	public void setPinnedMessage(Message pinnedMessage) {
		this.pinnedMessage = pinnedMessage;
	}


	public String getStickerSetName() {
		return stickerSetName;
	}


	public void setStickerSetName(String stickerSetName) {
		this.stickerSetName = stickerSetName;
	}
	
	
	
	
    

}
