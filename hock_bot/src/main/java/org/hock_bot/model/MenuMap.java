package org.hock_bot.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

import org.hock_bot.core.ConfigurationI;

@Entity
@Table(name = "bot_menu_map", indexes={@Index(columnList="menu_key,language", name="menulangkeyidx")}, catalog = ConfigurationI.CATALOG)
public class MenuMap extends AbstractEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 301896729483921L;


	@Column(name="menu_key", nullable=false)
	private String menuKey;
	
	
	@Column(name="language", nullable=false)
	private String language;
	
	@Column(name="response", nullable=false, length=10240)
	private String response;

	public String getMenuKey() {
		return menuKey;
	}

	public void setMenuKey(String menuKey) {
		this.menuKey = menuKey;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}
	
}
