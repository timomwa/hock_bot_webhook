package org.hock_bot.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

import org.hock_bot.core.ConfigurationI;

@Entity
@Table(name = "bot_flow_position", catalog = ConfigurationI.CATALOG,
indexes={@Index(name="chatUserMarkIdx", columnList="chatId, userId, positionMarker", unique=true),
		@Index(name="chatUserIdx", columnList="chatId, userId")})
public class FlowPosition extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -20484210011L;

	
	@Column(name="chatId")
	private Long chatId;
	
	@Column(name="userId")
	private Integer userId;
	
	@Column(name="positionMarker")
	private String positionMarker;

	public Long getChatId() {
		return chatId;
	}

	public void setChatId(Long chatId) {
		this.chatId = chatId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getPositionMarker() {
		return positionMarker;
	}

	public void setPositionMarker(String positionMarker) {
		this.positionMarker = positionMarker;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("\nFlowPosition [\n\tchatId=");
		builder.append(chatId);
		builder.append(", \n\tuserId=");
		builder.append(userId);
		builder.append(", \n\tpositionMarker=");
		builder.append(positionMarker);
		builder.append("\n]\n");
		return builder.toString();
	}
	
	
	
	

}
