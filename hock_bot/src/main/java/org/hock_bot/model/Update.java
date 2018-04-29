package org.hock_bot.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import org.hock_bot.core.ConfigurationI;

import com.fasterxml.jackson.annotation.JsonProperty;


@Entity
@Table(name = "bot_update", catalog = ConfigurationI.CATALOG)
public class Update extends AbstractEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 157557235345021L;


	@JsonProperty(ConfigurationI.UPDATEID_FIELD)
	@Column(name="update_id")
	private Integer updateId;
    
	
	@JsonProperty(ConfigurationI.MESSAGE_FIELD)
    @JoinColumn(name="message_id")
	private Message message;
	
	
	@Column(name="timeStamp", nullable=false)
	private Date timeStamp;
	
	
	@Column(name="status", nullable=false)
	@Enumerated(EnumType.STRING)
	private Status status;
	
	@PreUpdate
	@PrePersist
	public void update(){
		
		if(timeStamp==null)
			timeStamp = new Date();
		
		if(status==null)
			status = Status.JUST_IN;
		
	}


	public Integer getUpdateId() {
		return updateId;
	}


	public void setUpdateId(Integer updateId) {
		this.updateId = updateId;
	}


	public Message getMessage() {
		return message;
	}


	public void setMessage(Message message) {
		this.message = message;
	}


	public Date getTimeStamp() {
		return timeStamp;
	}


	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}


	public Status getStatus() {
		return status;
	}


	public void setStatus(Status status) {
		this.status = status;
	}
	
	
	

}
