package org.hock_bot.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hock_bot.core.ConfigurationI;

@Entity
@Table(name = "bot_chat", catalog = ConfigurationI.CATALOG)
public class Chat extends AbstractEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 209573792721L;

}
