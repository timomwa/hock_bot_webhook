package org.hock_bot.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

import org.hock_bot.core.ConfigurationI;


@Entity
@Table(name = "configuration", catalog = ConfigurationI.CATALOG,
indexes = { 
		@Index(columnList = "cfg_name,cfg_val", name = "cfgidx", unique = true)})
public class Configuration extends AbstractEntity  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 77700324331L;

	@Column(name = "cfg_name", length = 45, nullable = false)
	private String cfgName;
	
	@Column(name = "cfg_val", length = 145, nullable = false)
	private String cfgVal;

	public String getCfgName() {
		return cfgName;
	}

	public void setCfgName(String cfgName) {
		this.cfgName = cfgName;
	}

	public String getCfgVal() {
		return cfgVal;
	}

	public void setCfgVal(String cfgVal) {
		this.cfgVal = cfgVal;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("\nConfiguration \n[\n\tcfgName=");
		builder.append(cfgName);
		builder.append(", \n\tcfgVal=");
		builder.append(cfgVal);
		builder.append("\n]\n\n");
		return builder.toString();
	}


}
