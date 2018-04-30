package org.hock_bot.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hock_bot.core.ConfigurationI;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "bot_callback_query", indexes={
		@Index(
				columnList="callback_query_id", 
				unique=true)
		}, 
catalog = ConfigurationI.CATALOG)
public class CallbackQuery extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 109263926459110431L;
	
	
	@Column(name="callback_query_id", unique=true)
	private Long callBackQueryId;
	
	@JsonProperty(ConfigurationI.FROM_FIELD)
	@ManyToOne
	@JoinColumn(name="user_id")
	private User fromUser; ///< Sender
	
	
	@JsonProperty(ConfigurationI.MESSAGE_FIELD)
	@OneToOne
	@JoinColumn(name="message_id")
	private Message message;
	
	@JsonProperty(ConfigurationI.INLINE_MESSAGE_ID_FIELD)
	@Column(name="inline_message_id")
	private String inlineMessageId; ///< Optional. Identifier of the message sent via the bot in inline mode, that originated the query
    /**
     *
     * Optional. Data associated with the callback button.
     * @note Be aware that a bad client can send arbitrary data in this field
     */
    @JsonProperty(ConfigurationI.DATA_FIELD)
    @Column(name="data", length=1024)
	private String data;
    /**
     * Optional. Short name of a Game to be returned, serves as the unique identifier for the game
     */
    @JsonProperty(ConfigurationI.GAMESHORTNAME_FIELD)
    @Column(name="game_short_name", length=1024)
	private String gameShortName;
    /**
     * Identifier, uniquely corresponding to the chat to which the message with the
     * callback button was sent. Useful for high scores in games.
     */
    @JsonProperty(ConfigurationI.CHAT_INSTANCE_FIELD)
    @Column(name="chat_instance", length=1024)
	private String chatInstance;
	public Long getCallBackQueryId() {
		return callBackQueryId;
	}
	public void setCallBackQueryId(Long callBackQueryId) {
		this.callBackQueryId = callBackQueryId;
	}
	public User getFromUser() {
		return fromUser;
	}
	public void setFromUser(User fromUser) {
		this.fromUser = fromUser;
	}
	public Message getMessage() {
		return message;
	}
	public void setMessage(Message message) {
		this.message = message;
	}
	public String getInlineMessageId() {
		return inlineMessageId;
	}
	public void setInlineMessageId(String inlineMessageId) {
		this.inlineMessageId = inlineMessageId;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getGameShortName() {
		return gameShortName;
	}
	public void setGameShortName(String gameShortName) {
		this.gameShortName = gameShortName;
	}
	public String getChatInstance() {
		return chatInstance;
	}
	public void setChatInstance(String chatInstance) {
		this.chatInstance = chatInstance;
	}
    
    
    

}
