package org.hock_bot.model;

import javax.persistence.Column;

public class SendMessage extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 16820353560741L;
	
	
	@Column(name = "chat_id", length = 45, nullable = false)
	private String chatId;
	
	@Column(name = "text", length = 50120, nullable = false)
	private String text;
	
	@Column(name = "parse_mode", length = 45, nullable = false)
	private String parseMode;
	
	@Column(name = "disable_web_page_privew", length = 45, nullable = false)
	private Boolean disableWebPagePreview;
	
	@Column(name = "disable_notification", length = 45, nullable = false)
	private Boolean disableNotification;
	
	@Column(name = "replply_to_message_id", length = 45, nullable = false)
	private Integer replyToMessageId;

	public String getChatId() {
		return chatId;
	}

	public void setChatId(String chatId) {
		this.chatId = chatId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getParseMode() {
		return parseMode;
	}

	public void setParseMode(String parseMode) {
		this.parseMode = parseMode;
	}

	public Boolean getDisableWebPagePreview() {
		return disableWebPagePreview;
	}

	public void setDisableWebPagePreview(Boolean disableWebPagePreview) {
		this.disableWebPagePreview = disableWebPagePreview;
	}

	public Boolean getDisableNotification() {
		return disableNotification;
	}

	public void setDisableNotification(Boolean disableNotification) {
		this.disableNotification = disableNotification;
	}

	public Integer getReplyToMessageId() {
		return replyToMessageId;
	}

	public void setReplyToMessageId(Integer replyToMessageId) {
		this.replyToMessageId = replyToMessageId;
	}
	
	
	
	
}
