package org.hock_bot.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hock_bot.core.ConfigurationI;

import com.fasterxml.jackson.annotation.JsonProperty;


@Entity
@Table(name = "bot_user", catalog = ConfigurationI.CATALOG)
public class User extends  AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -160848165083739L;
	
	@JsonProperty(ConfigurationI.ID_FIELD)
	@Column(name="user_id", unique=true)
	private Integer userId; /// < Unique identifier for this user or bot
	
	
	@JsonProperty(ConfigurationI.FIRSTNAME_FIELD)
	@Column(name="first_name")
	private String firstName; /// < User‘s or bot’s first name
	
	@JsonProperty(ConfigurationI.ISBOT_FIELD)
	@Column(name="is_bot")
	private Boolean isBot; /// < True, if this user is a bot
	
	@JsonProperty(ConfigurationI.LASTNAME_FIELD)
	@Column(name="last_name")
	private String lastName; /// < Optional. User‘s or bot’s last name
	
	
	@JsonProperty(ConfigurationI.USERNAME_FIELD)
	@Column(name="user_name")
	private String userName; /// < Optional. User‘s or bot’s username
	
	
	@JsonProperty(ConfigurationI.LANGUAGECODE_FIELD)
	@Column(name="languageCode")
	private String languageCode; /// < Optional. IETF language tag of the user's
									/// language


	public Integer getUserId() {
		return userId;
	}


	public void setUserId(Integer userId) {
		this.userId = userId;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public Boolean getIsBot() {
		return isBot;
	}


	public void setIsBot(Boolean isBot) {
		this.isBot = isBot;
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


	public String getLanguageCode() {
		return languageCode;
	}


	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}
	
	
	

}
