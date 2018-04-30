package org.hock_bot.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hock_bot.core.ConfigurationI;

@Entity
@Table(name = "vehicle_model", 
indexes={@Index(columnList="name", unique=true)}, 
catalog = ConfigurationI.CATALOG)
public class VehicleModel extends AbstractEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1098873932220811L;
	@Column(name="name", unique=true, nullable=false)
	private String name;
	
	@ManyToOne
	@JoinColumn(name="make_id", nullable=false)
	private VehicleMake make;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public VehicleMake getMake() {
		return make;
	}

	public void setMake(VehicleMake make) {
		this.make = make;
	}
	
	
	
	

}
