package org.hock_bot.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hock_bot.core.ConfigurationI;

@Entity
@Table(name = "vehicle_model_year", 
indexes={@Index(columnList="model_id,year", unique=true)}, 
catalog = ConfigurationI.CATALOG)
public class VehicleModelYear extends AbstractEntity  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 300965218702610874L;
	
	
	@ManyToOne
	@JoinColumn(name="model_id")
	private VehicleModel model;
	
	@Column(name="year")
	private Integer year;

	public VehicleModel getModel() {
		return model;
	}

	public void setModel(VehicleModel model) {
		this.model = model;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}
	
	

}
