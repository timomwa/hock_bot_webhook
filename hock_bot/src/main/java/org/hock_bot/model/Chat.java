package org.hock_bot.model;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

import org.hock_bot.core.ConfigurationI;

@Entity
@Table(name = "bot_chat", catalog = ConfigurationI.CATALOG,
indexes = { 
		@Index(columnList = "cfg_name,cfg_val", name = "cfgidx", unique = true)})
public class Chat extends AbstractEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 209573792721L;

}
