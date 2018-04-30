package org.hock_bot.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hock_bot.core.ConfigurationI;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "bot_message", catalog = ConfigurationI.CATALOG)
public class Message extends AbstractEntity {

	@JsonProperty(ConfigurationI.MESSAGEID_FIELD)
	@Column(name = "message_id")
	private Integer messageId;

	@JsonProperty(ConfigurationI.FROM_FIELD)
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User fromUser;

	@JsonProperty(ConfigurationI.DATE_FIELD)
	@Column(name = "date")
	private Integer date;

	@JsonProperty(ConfigurationI.CHAT_FIELD)
	@ManyToOne
	@JoinColumn(name = "chat_id")
	private Chat chat;

	@JsonProperty(ConfigurationI.TEXT_FIELD)
	@Column(name = "text", length = 10240)
	private String text;

	@JsonProperty(ConfigurationI.REPLYTOMESSAGE_FIELD)
	@OneToOne
	@JoinColumn(name = "reply_to_message")
	private Message replyToMessage;

	public Integer getMessageId() {
		return messageId;
	}

	public void setMessageId(Integer messageId) {
		this.messageId = messageId;
	}

	public User getFromUser() {
		return fromUser;
	}

	public void setFromUser(User from) {
		this.fromUser = from;
	}

	public Integer getDate() {
		return date;
	}

	public void setDate(Integer date) {
		this.date = date;
	}

	public Chat getChat() {
		return chat;
	}

	public void setChat(Chat chat) {
		this.chat = chat;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Message getReplyToMessage() {
		return replyToMessage;
	}

	public void setReplyToMessage(Message replyToMessage) {
		this.replyToMessage = replyToMessage;
	}
	
	

}
