package org.hock_bot.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

import org.hock_bot.core.ConfigurationI;

@Entity
@Table(name = "vehicle_make", 
indexes={@Index(columnList="name", unique=true)}, 
catalog = ConfigurationI.CATALOG)
public class VehicleMake extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 10827838284331L;
	
	@Column(name="name", unique=true)
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	
	

}
